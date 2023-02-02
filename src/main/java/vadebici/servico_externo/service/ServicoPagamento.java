package vadebici.servico_externo.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;
import vadebici.servico_externo.db_in_memory.Database;
import vadebici.servico_externo.dom.Cartao;
import vadebici.servico_externo.dom.Ciclista;
import vadebici.servico_externo.dom.CobrancaRequisicao;
import vadebici.servico_externo.dom.Cobranca;
import vadebici.exception.GenericApiException;
import vadebici.servico_externo.service.mapping.*;
import vadebici.servico_externo.util.Segredos;
import vadebici.servico_externo.util.Validacao;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.singletonList;

public class ServicoPagamento {

    private static final String BASE_URL = "https://api-sandbox.getnet.com.br";
    private String token;
    private LocalDateTime expiracaoToken;
    private final ServicoAluguel servicoAluguel;

    public ServicoPagamento(ServicoAluguel servicoAluguel) {
        this.servicoAluguel = servicoAluguel;
    }

    private void refrescarTokenSeExpirado() {
       if (token == null || LocalDateTime.now().isAfter(expiracaoToken)) {
           String url = BASE_URL + "/auth/oauth/v2/token";
           Map<String, String> headers = new HashMap<>();
           Map<String, Object> fields = new HashMap<>();
           String auth = Segredos.getClientId() + ":" + Segredos.getClientSecret();
           String authEnc = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
           headers.put("content-type", "application/x-www-form-urlencoded");
           headers.put("authorization", "Basic " + authEnc);
           fields.put("scope", "oob");
           fields.put("grant_type", "client_credentials");

           HttpResponse<String> response = Unirest.post(url)
                   .headers(headers)
                   .fields(fields)
                   .asString();

           GetnetRespostaTokenAcesso getnetRespostaTokenAcesso = JavalinJson.getFromJsonMapper()
                   .map(response.getBody(), GetnetRespostaTokenAcesso.class);
           token = getnetRespostaTokenAcesso.getAccessToken();
           expiracaoToken = LocalDateTime.now().plusSeconds(getnetRespostaTokenAcesso.getExpiresIn());
       }
    }

    private String getTokenCartao(String numeroCartao) {
        String url = BASE_URL + "/v1/tokens/card";
        refrescarTokenSeExpirado();
        Map<String, String> headers = getHeaders();

        HttpResponse<String> response = Unirest.post(url)
                .headers(headers)
                .body("{\"card_number\":\"" + numeroCartao + "\"}")
                .asString();

        if (response.getStatus() == 201) {
            JsonNode node = JavalinJson.getFromJsonMapper()
                    .map(response.getBody(), JsonNode.class);
            return node.get("number_token").asText();

        } else {
            throw construirExcecao(response);
        }
    }
    private GenericApiException construirExcecao(HttpResponse<String> response) {
        GetnetErrorResponse getnetErrorResponse = JavalinJson.getFromJsonMapper()
                .map(response.getBody(), GetnetErrorResponse.class);
        String descricaoErro = getnetErrorResponse.getDetails().get(0).getDescription();
        String detalhesErro = getnetErrorResponse.getDetails().get(0).getDescriptionDetail();
        String mensagem = String.format("%s : %s", descricaoErro, detalhesErro);
        return new GenericApiException(422, mensagem);
    }

    public String validarCartao(Cartao cartao) {
        Validacao.validarCampos(cartao);
        String url = BASE_URL + "/v1/cards/verification";
        refrescarTokenSeExpirado();
        String tokenCartao = getTokenCartao(cartao.getNumero());

        Map<String, String> headers = getHeaders();

        GetnetRequisicaoCard getnetRequisicaoCard = getGetnetRequisicaoCard(tokenCartao, cartao);

        HttpResponse<String> response = Unirest.post(url)
                .headers(headers)
                .body(getnetRequisicaoCard)
                .asString();

        if (response.getStatus() == 200) {
            JsonNode node = JavalinJson.getFromJsonMapper()
                    .map(response.getBody(), JsonNode.class);
            return node.get("status").asText();
        } else {
            throw construirExcecao(response);
        }
    }

    public Cobranca realizarCobranca(CobrancaRequisicao cobrancaRequisicao) {
        Validacao.validarCampos(cobrancaRequisicao);
        Ciclista ciclista = servicoAluguel.getCiclista(cobrancaRequisicao.getCiclista());
        String url = BASE_URL + "/v1/payments/credit";
        refrescarTokenSeExpirado();
        String tokenCartao = getTokenCartao(ciclista.getCartao().getNumero());

        Map<String, String> headers = getHeaders();

        GetnetRequisicaoPagamento getnetRequisicaoPagamento = construirGetnetRequisicaoPagamento(tokenCartao, cobrancaRequisicao, ciclista);
        Cobranca cobranca = new Cobranca();
        cobranca.setHoraSolicitacao(new Date());
        String json = JavalinJson.toJson(getnetRequisicaoPagamento);
        HttpResponse<String> response = Unirest.post(url)
                .headers(headers)
                .body(json)
                .asString();

        if (response.getStatus() == 200) {
            JsonNode node = JavalinJson.getFromJsonMapper()
                    .map(response.getBody(), JsonNode.class);
            cobranca.setHoraFinalizacao(new Date());
            cobranca.setValor(cobrancaRequisicao.getValor());
            cobranca.setCiclista(cobrancaRequisicao.getCiclista());
            cobranca.setId(UUID.randomUUID().toString());
            cobranca.setStatus(node.get("status").asText());
            Database.TABELA_COBRANCA.put(cobranca.getId(), cobranca);
            return cobranca;
        } else {
            throw construirExcecao(response);
        }
    }

    public Cobranca buscarCobranca(String idCobranca) {
        return Optional.of(Database.TABELA_COBRANCA.get(idCobranca))
                .orElseThrow(() -> new GenericApiException(404, "Cobranca não encontrada"));
    }

    public Cobranca adicionarCobrancaNaFila(CobrancaRequisicao cobrancaRequisicao) {
        Cobranca cobranca = new Cobranca();
        cobranca.setCiclista(cobrancaRequisicao.getCiclista());
        cobranca.setValor(cobrancaRequisicao.getValor());
        cobranca.setHoraSolicitacao(new Date());
        cobranca.setStatus("DENIED");
        cobranca.setId(UUID.randomUUID().toString());

        Database.TABELA_FILA_COBRANCA.put(cobranca.getId(), cobranca);

        return cobranca;
    }

    public void processaCobrancas() {
        for (Map.Entry<String, Cobranca> itemFila :
                Database.TABELA_FILA_COBRANCA.entrySet()) {

            Cobranca cobranca = itemFila.getValue();

            CobrancaRequisicao cobrancaRequisicao = new CobrancaRequisicao();

            cobrancaRequisicao.setCiclista(cobranca.getCiclista());
            cobrancaRequisicao.setValor(cobranca.getValor());

            Cobranca novaCobranca = this.realizarCobranca(cobrancaRequisicao);

            if (novaCobranca != null)
                Database.TABELA_FILA_COBRANCA.remove(itemFila.getKey());
        }
    }

    @NotNull
    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("authorization", "Bearer " + token);
        return headers;
    }

    @NotNull
    private GetnetRequisicaoPagamento construirGetnetRequisicaoPagamento(String tokenCartao, CobrancaRequisicao cobrancaRequisicao, Ciclista ciclista) {
        GetnetRequisicaoPagamento getnetRequisicaoPagamento = new GetnetRequisicaoPagamento();
        getnetRequisicaoPagamento.setSellerId(Segredos.getSellerId());
        getnetRequisicaoPagamento.setAmount(String.valueOf(cobrancaRequisicao.getValor()));
        getnetRequisicaoPagamento.setOrder(getGetnetRequisicaoOrder());
        getnetRequisicaoPagamento.setCustomer(getGetnetRequisicaoCustomer());
        getnetRequisicaoPagamento.setShippings(getGetnetRequisicaoShippings());
        getnetRequisicaoPagamento.setCredit(getGetnetRequisicaoCredit(tokenCartao, ciclista.getCartao()));
        return getnetRequisicaoPagamento;
    }

    @NotNull
    private GetnetRequisicaoCredit getGetnetRequisicaoCredit(String tokenCartao, Cartao cartao) {
        GetnetRequisicaoCredit getnetRequisicaoCredit = new GetnetRequisicaoCredit();
        getnetRequisicaoCredit.setDelayed(false);
        getnetRequisicaoCredit.setSaveCardData(false);
        getnetRequisicaoCredit.setNumberInstallments(1);
        getnetRequisicaoCredit.setTransactionType("FULL");
        getnetRequisicaoCredit.setCard(getGetnetRequisicaoCard(tokenCartao, cartao));
        return getnetRequisicaoCredit;
    }
    @NotNull
    private GetnetRequisicaoCard getGetnetRequisicaoCard(String tokenCartao, Cartao cartao) {
        GetnetRequisicaoCard getnetRequisicaoCard = new GetnetRequisicaoCard();
        getnetRequisicaoCard.setNumber_token(tokenCartao);
        getnetRequisicaoCard.setCardholder_name(cartao.getNomeTitular());
        getnetRequisicaoCard.setExpiration_month(cartao.getValidade().substring(5, 7));
        getnetRequisicaoCard.setExpiration_year(cartao.getValidade().substring(2, 4));
        getnetRequisicaoCard.setSecurity_code(cartao.getCvv());
        return getnetRequisicaoCard;
    }

    @NotNull
    private GetnetRequisicaoCustomer getGetnetRequisicaoCustomer() {
        GetnetRequisicaoCustomer getnetRequisicaoCustomer = new GetnetRequisicaoCustomer();
        getnetRequisicaoCustomer.setCustomerId(UUID.randomUUID().toString());
        getnetRequisicaoCustomer.setBillingAddress(new HashMap<>());
        return getnetRequisicaoCustomer;
    }

    @NotNull
    private GetnetRequisicaoOrder getGetnetRequisicaoOrder() {
        GetnetRequisicaoOrder getnetRequisicaoOrder = new GetnetRequisicaoOrder();
        getnetRequisicaoOrder.setOrderId(UUID.randomUUID().toString());
        return getnetRequisicaoOrder;
    }

    @NotNull
    private List<GetnetRequisicaoShippingInfo> getGetnetRequisicaoShippings() {
        GetnetRequisicaoShippingInfo getnetRequisicaoShippingInfo = new GetnetRequisicaoShippingInfo();
        getnetRequisicaoShippingInfo.setAddress(new HashMap<>());
        return singletonList(getnetRequisicaoShippingInfo);
    }
}
