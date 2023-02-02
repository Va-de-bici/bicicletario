package vadebici.servico_aluguel.service;

import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.PostTotemJsonApi;
import vadebici.servico_aluguel.dom.dto.PostTrancaJsonApi;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.dom.dto.BiciclistaNaRedeJsonApi;
import vadebici.servico_aluguel.dom.dto.TrancaNaRedeJsonApi;

public class ServicoEquipamento {
    private final String EQUIPAMENTO_BASE_URL = "https://va-de-bici.herokuapp.com";
    private final String BICICLETA_BASE_URL = "/bicicleta/";
    private final String TRANCA_BASE_URL = "/tranca/";


    public HttpResponse<String> mudarStatusBicicleta(String id, String status){
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + BICICLETA_BASE_URL + id + "/status/" + status)
                .body("")
                .asString();
        validarResposta(response);

        return response;
    }

    public HttpResponse<String> mudarStatusTranca(String id, String status){
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + TRANCA_BASE_URL + id + "/status/" + status)
                .body("")
                .asString();
        validarResposta(response);

        return response;
    }

    public HttpResponse<String> integrarTrancaNaRede(String idTranca, String idTotem){
        TrancaNaRedeJsonApi requisicaoTrancaNaRede = new TrancaNaRedeJsonApi(idTranca, idTotem);
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + "/tranca/integrarNaRede")
                .body(requisicaoTrancaNaRede)
                .asString();
        validarResposta(response);

        return response;
    }

    public HttpResponse<String> removerTrancaDaRede(String idTranca, String idTotem){
        TrancaNaRedeJsonApi requisicaoTrancaNaRede = new TrancaNaRedeJsonApi(idTranca, idTotem);
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + "/tranca/removerDaRede")
                .body(requisicaoTrancaNaRede)
                .asString();
        validarResposta(response);

        return response;
    }

    public HttpResponse<String> integrarBicicletaNaRede(String idBicicleta, String idTranca){
        BiciclistaNaRedeJsonApi requisicaoBicicletaNaRede = new BiciclistaNaRedeJsonApi(idBicicleta, idTranca);
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + BICICLETA_BASE_URL + "integrarNaRede")
                .body(requisicaoBicicletaNaRede)
                .asString();
        validarResposta(response);

        return response;
    }

    public HttpResponse<String> removerBicicletaDaRede(String idBicicleta, String idTranca){
        BiciclistaNaRedeJsonApi requisicaoBicicletaNaRede = new BiciclistaNaRedeJsonApi(idBicicleta, idTranca);
        HttpResponse<String> response = Unirest.post(EQUIPAMENTO_BASE_URL + BICICLETA_BASE_URL + "removerDaRede")
                .body(requisicaoBicicletaNaRede)
                .asString();
        validarResposta(response);

        return response;
    }

    public Tranca buscarTranca(String id) {
        HttpResponse<String> response = Unirest.get(EQUIPAMENTO_BASE_URL + TRANCA_BASE_URL + id)
                .asString();
        validarResposta(response);

        return JavalinJson.fromJson(response.getBody(), Tranca.class);
    }

    public Bicicleta buscarBicicleta(String id) {
        HttpResponse<String> response = Unirest.get(EQUIPAMENTO_BASE_URL + BICICLETA_BASE_URL + id)
                .asString();
        validarResposta(response);

        return JavalinJson.fromJson(response.getBody(), Bicicleta.class);
    }

    public String salvarBicicleta(PostTrancaJsonApi requisicaoPostBicicleta) {
        return (String) Unirest.post(EQUIPAMENTO_BASE_URL + "/bicicleta")
                .body(requisicaoPostBicicleta)
                .asJson()
                .getBody()
                .getObject()
                .get("id");
    }

    public String salvarTranca(PostTrancaJsonApi requisicaoPostTranca) {
        return (String) Unirest.post(EQUIPAMENTO_BASE_URL + "/tranca")
                .body(requisicaoPostTranca)
                .asJson()
                .getBody()
                .getObject()
                .get("id");
    }

    public String salvarTotem(PostTotemJsonApi requisicaoPostTotem) {
        return (String) Unirest.post(EQUIPAMENTO_BASE_URL + "/totem")
                .body(requisicaoPostTotem)
                .asJson()
                .getBody()
                .getObject()
                .get("id");
    }

    public void deletarBicicleta(String id) {
        Unirest.delete(EQUIPAMENTO_BASE_URL + BICICLETA_BASE_URL +id);
    }

    public void deletarTranca(String id) {
        Unirest.delete(EQUIPAMENTO_BASE_URL + TRANCA_BASE_URL + id);
    }

    public void deletarTotem(String id) {
        Unirest.delete(EQUIPAMENTO_BASE_URL +"/totem/"+id);
    }

    private void validarResposta(HttpResponse<String> response) {
        if (response.getStatus() < 200 || response.getStatus() > 299) {
            throw JavalinJson.fromJson(response.getBody(), GenericApiException.class);
        }
    }

}
