package vadebici.servico_aluguel.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.AluguelJsonApi;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.servico_aluguel.dom.dto.CobrancaJsonApi;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.repo.CiclistaRepositorio;
import vadebici.servico_aluguel.service.ServicoEquipamento;
import vadebici.servico_aluguel.service.ServicoExterno;
import vadebici.servico_aluguel.util.Validacao;

import java.util.Optional;


public class CiclistaController {
    public static final CiclistaRepositorio CICLISTA_REPOSITORIO = new CiclistaRepositorio();

    public static final Validacao VALIDACAO = new Validacao();

    private static final ServicoExterno SERVICO_EXTERNO =  new ServicoExterno();

    private static final ServicoEquipamento SERVICO_EQUIPAMENTO =  new ServicoEquipamento();

    private static final String CAMPO_ID_CICLISTA = "idCiclista";

    private CiclistaController(){}

    public static void guardaCiclista(Context ctx) {
        String body = ctx.body();
        Ciclista postCiclista = JavalinJson.getFromJsonMapper().map(body, Ciclista.class);
        VALIDACAO.validaAtributos(postCiclista, 405);
        VALIDACAO.validaNacionalidade(postCiclista);
        SERVICO_EXTERNO.validaCartao(postCiclista.getMeioDePagamento());
        if (CICLISTA_REPOSITORIO.buscaPorEmail(postCiclista.getEmail()).isPresent()) throw new GenericApiException(405, "Email já cadastrado");
        Ciclista ciclista = CICLISTA_REPOSITORIO.cadastraCiclista(postCiclista);
        String mensagem = String.format("Cadastro realizado com sucesso. Por favor, confirme seu cadastro " +
                "clicando no link\n\n https://va-de-bici.herokuapp.com/ciclista/%s/ativar", ciclista.getId());
        enviaEmail(ciclista.getEmail(), mensagem);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(201);
    }

    public static void retornaCiclista(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = CICLISTA_REPOSITORIO.retornaCiclista(ciclistaId);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(200);
    }

    public static void alteraCiclista(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        CiclistaJsonApi ciclistaJsonApi = JavalinJson.getFromJsonMapper().map(body, CiclistaJsonApi.class);
        VALIDACAO.validaAtributos(ciclistaJsonApi, 405);
        VALIDACAO.validaNacionalidadeJson(ciclistaJsonApi);
        if (CICLISTA_REPOSITORIO.buscaPorEmail(ciclistaJsonApi.getEmail()).isPresent()) throw new GenericApiException(405, "Email já cadastrado");
        CICLISTA_REPOSITORIO.alteraCiclista(id, ciclistaJsonApi);
        ciclistaJsonApi.setId(id);
        enviaEmail(ciclistaJsonApi.getEmail(), "Dados alterados com sucesso.\n\n" + ciclistaJsonApi);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(ciclistaJsonApi));

    }

    public static void verificaEmail(Context ctx) {
        String email = ctx.pathParam("email");

        boolean existe = CICLISTA_REPOSITORIO.checaEmails(email);

        String response = JavalinJson.toJson(existe);
        ctx.result(response);
        ctx.status(200);
    }

    public static void emailNaoEnviado(Context ctx) {
        throw new GenericApiException(400, "Email não enviado como parâmetro");
    }

    public static void ativaCadastro(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        CiclistaJsonApi resultado = CICLISTA_REPOSITORIO.confirmaEmail(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));

    }

    public static void retornaCartao(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        MeioDePagamento resultado = CICLISTA_REPOSITORIO.retornaCartaoPorCiclista(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));
    }

    public static void alteraCartao(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        MeioDePagamento meioDePagamento = JavalinJson.getFromJsonMapper().map(body, MeioDePagamento.class);
        VALIDACAO.validaAtributos(meioDePagamento, 422);
        SERVICO_EXTERNO.validaCartao(meioDePagamento);
        CICLISTA_REPOSITORIO.alteraCartaoPorCiclista(id, meioDePagamento);
        ctx.status(200);
        ctx.result(JavalinJson.toJson("Dados atualizados"));
    }

    public static void enviaEmail(String destinatario, String mensagem) {
        Email email = new Email();
        email.setEnderecoEmail(destinatario);
        email.setMensagem(mensagem);
        SERVICO_EXTERNO.enviaEmail(email);
    }

    public static void alugar(Context ctx) {
        String body = ctx.body();
        AluguelJsonApi aluguelJsonApi = JavalinJson.getFromJsonMapper().map(body, AluguelJsonApi.class);
        VALIDACAO.validaAtributos(aluguelJsonApi, 422);
        Ciclista ciclista = CICLISTA_REPOSITORIO.retornaCiclista(aluguelJsonApi.getCiclista());
        Optional<Aluguel> aluguelOptional = CICLISTA_REPOSITORIO.buscaAluguelEmCursoPorCiclista(ciclista);

        if (aluguelOptional.isPresent()) {
            enviaEmail(ciclista.getEmail(), "O aluguel foi bloqueado, pois já existe outro em andamento. \n\n" + aluguelOptional.get());
            throw new GenericApiException(422, "Ciclista não pode alugar a bicicleta, pois já possui outro aluguel em andamento");
        }

        Tranca tranca = SERVICO_EQUIPAMENTO.retornaTranca(aluguelJsonApi.getTrancaInicio());

        if (tranca.getBicicleta() == null) {
            throw new GenericApiException(422, "Tranca vazia");
        }

        CobrancaJsonApi cobrancaJsonApi = new CobrancaJsonApi();
        cobrancaJsonApi.setCiclista(ciclista.getId());
        cobrancaJsonApi.setValor(10.0);
        Cobranca cobranca = SERVICO_EXTERNO.realizaCobranca(cobrancaJsonApi);
        Bicicleta bicicleta = SERVICO_EQUIPAMENTO.retornaBicicleta(tranca.getBicicleta());

        if (bicicleta.getStatus().equals("EM REPARO")) {
            throw new GenericApiException(422, "Há um reparo requisitado para a bicicleta e, portanto, esta bicicleta não pode ser alugada.");
        }

        tranca = SERVICO_EQUIPAMENTO.removeBicicletaDaRede(tranca);
        bicicleta = SERVICO_EQUIPAMENTO.alteraStatusBicicleta(bicicleta, "EM USO");
        tranca = SERVICO_EQUIPAMENTO.alteraStatusTranca(tranca, "LIVRE");
        final Aluguel aluguel = CICLISTA_REPOSITORIO.alugar(ciclista, tranca, bicicleta, cobranca);
        enviaEmail(ciclista.getEmail(), "Aluguel efetuado com sucesso \n\n" + aluguel);
        String response = JavalinJson.toJson(aluguel);
        ctx.result(response);
        ctx.status(200);
    }

    public static void permiteAluguel(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = CICLISTA_REPOSITORIO.retornaCiclista(ciclistaId);
        Optional<Aluguel> aluguelOptional = CICLISTA_REPOSITORIO.buscaAluguelEmCursoPorCiclista(ciclista);
        String response;

        if (aluguelOptional.isPresent())
            response = JavalinJson.toJson(false);
        else
            response = JavalinJson.toJson(true);

        ctx.result(response);
        ctx.status(200);
    }

    public static void notificaAluguelEmCurso(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = CICLISTA_REPOSITORIO.retornaCiclista(ciclistaId);
        Optional<Aluguel> aluguelOptional = CICLISTA_REPOSITORIO.buscaAluguelEmCursoPorCiclista(ciclista);
        if (aluguelOptional.isPresent()) {
            enviaEmail(ciclista.getEmail(), "O aluguel foi bloqueado, pois já existe outro em andamento. \n\n" + aluguelOptional.get());
            String response = JavalinJson.toJson(ciclista);
            ctx.result(response);
            ctx.status(200);
        } else {
            throw new GenericApiException(405, "Dados Inválidos - ciclista não possui aluguel em curso");
        }
    }

}
