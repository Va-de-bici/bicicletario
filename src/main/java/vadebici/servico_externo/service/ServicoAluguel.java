package vadebici.servico_externo.service;

import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import vadebici.servico_externo.dom.Ciclista;
import vadebici.exception.GenericApiException;

public class ServicoAluguel {
    public static final String BASE_URL = "https://va-de-bici.herokuapp.com";

    public Ciclista getCiclista(String id) {
        HttpResponse<String> response = Unirest.get(BASE_URL + "/ciclista/" + id)
                .asString();
        validarResposta(response);
        return JavalinJson.fromJson(response.getBody(), Ciclista.class);
    }

    private void validarResposta(HttpResponse<String> response) {
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            throw JavalinJson.fromJson(response.getBody(), GenericApiException.class);
        }
    }
}
