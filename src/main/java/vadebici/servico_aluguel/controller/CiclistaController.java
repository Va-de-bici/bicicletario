package vadebici.servico_aluguel.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.servico_aluguel.repo.CicliRepo;
import vadebici.servico_aluguel.service.ServicoExterno;
import vadebici.servico_aluguel.dom.*;


public class CiclistaController {
    public static final  CicliRepo CICLI_REPO = new CicliRepo(new ServicoExterno());

    private CiclistaController(){}

    private static final String CAMPO_ID_CICLISTA = "idCiclista";

    public static void guardaCicli(Context ctx) {
        String body = ctx.body();
        Ciclista postCiclista = JavalinJson.getFromJsonMapper().map(body, Ciclista.class);
        Ciclista ciclista = CICLI_REPO.cadastraCiclista(postCiclista);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(201);
    }

    public static void retornaCicli(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = CICLI_REPO.retornaCiclista(ciclistaId);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(200);
    }

    public static void verificaEmail(Context ctx) {
        String email = ctx.pathParam("email");
        boolean existe = CICLI_REPO.checaEmails(email);
        ctx.status(200);
        ctx.result(String.valueOf(existe));
    }

    public static void ativaCadastro(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        CiclistaJsonApi resultado = CICLI_REPO.confirmaEmail(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));

    }

    public static void retornaCartao(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        MeioDePagamento resultado = CICLI_REPO.retornaCartaoPorCiclista(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));
    }

}
