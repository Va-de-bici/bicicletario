package vadebici.servico_equipamento.service;

import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import vadebici.exception.GenericApiException;
import vadebici.servico_equipamento.dom.Email;
import vadebici.servico_equipamento.dom.dto.EmailJsonApi;

public class ServicoExterno {
    private final String EXTERNO_BASE_URL = "https://va-de-bici.herokuapp.com";

    public HttpResponse<String> enviaEmail(Email email) {
        EmailJsonApi emailEnviado = new EmailJsonApi(email.getEnderecoEmail(), email.getMensagem());

        HttpResponse<String> response = Unirest.post(EXTERNO_BASE_URL + "/enviarEmail")
                .body(emailEnviado)
                .asString();
        validarResposta(response);

        return response;
    }

    private void validarResposta(HttpResponse<String> response) {
        if (response.getStatus() < 200 || response.getStatus() > 299) {
            throw JavalinJson.fromJson(response.getBody(), GenericApiException.class);
        }
    }

}
