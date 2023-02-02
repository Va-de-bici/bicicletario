package vadebici.servico_aluguel.service;

import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.CobrancaJsonApi;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import vadebici.servico_aluguel.dom.dto.EmailJsonApi;
import vadebici.exception.GenericApiException;

public class ServicoExterno {
    private final String EXTERNO_BASE_URL = "https://va-de-bici.herokuapp.com";

    public HttpResponse<String> validaCartao(MeioDePagamento meioDePagamento) {
        HttpResponse<String> response = Unirest.post(EXTERNO_BASE_URL + "/validaCartaoDeCredito")
                .body(meioDePagamento)
                .asString();

        validarResposta(response);

        return response;
    }

    public HttpResponse<String> enviaEmail(Email email) {
        EmailJsonApi emailEnviado = new EmailJsonApi(email.getEnderecoEmail(), email.getMensagem());

        HttpResponse<String> response = Unirest.post(EXTERNO_BASE_URL + "/enviarEmail")
                .body(emailEnviado)
                .asString();
        validarResposta(response);

        return response;
    }

    public Cobranca realizaCobranca(CobrancaJsonApi cobrancaJsonApi) {
        HttpResponse<String> response = Unirest.post(EXTERNO_BASE_URL + "/cobranca")
                .body(cobrancaJsonApi)
                .asString();

        if (response.getStatus() != 200) {
            response = Unirest.post(EXTERNO_BASE_URL + "/filaCobranca")
                    .body(cobrancaJsonApi)
                    .asString();
        }

        validarResposta(response);

        return JavalinJson.fromJson(response.getBody(), Cobranca.class);
    }

    private void validarResposta(HttpResponse<String> response) {
        if (response.getStatus() < 200 || response.getStatus() > 299) {
            throw JavalinJson.fromJson(response.getBody(), GenericApiException.class);
        }
    }

}
