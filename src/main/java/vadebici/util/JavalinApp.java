package vadebici.util;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.controller.FuncionarioController;
import vadebici.servico_aluguel.controller.CiclistaController;
import vadebici.servico_aluguel.exception.GenericApiException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinApp {

    private final Javalin app =
            Javalin.create(config -> config.defaultContentType = "application/json")
                    .routes(() -> {
                        final String FUNCIONARIO_COM_ID = "/funcionario/:idFuncionario";

                        path("/funcionario", () -> get(FuncionarioController::retornaFuncs));
                        path("/funcionario", () -> post(FuncionarioController::guardaFunc));

                        path(FUNCIONARIO_COM_ID, () -> get(FuncionarioController::retornaFunc));
                        path(FUNCIONARIO_COM_ID, () -> delete(FuncionarioController::deletaFunc));
                        path(FUNCIONARIO_COM_ID, () -> put(FuncionarioController::alteraFunc));

                        path("/ciclista", () -> post(CiclistaController::guardaCicli));
                        path("/ciclista/:idCiclista", () -> get(CiclistaController::retornaCicli));
                        path("/ciclista/:idCiclista/ativar", () -> post(CiclistaController::ativaCadastro));
                        path("/ciclista/existeEmail/:email", () -> get(CiclistaController::verificaEmail));

                        path("/cartaoDeCredito/:idCiclista", () -> get(CiclistaController::retornaCartao));
                    }).error(500, ctx -> {
                        GenericApiException ex = new GenericApiException(500, "Ocorreu um erro durante a operação");
                        ctx.result(JavalinJson.toJson(ex));
                    }).error(404, ctx -> {
                        if (ctx.attribute("handle") == null) {
                            GenericApiException ex = new GenericApiException(404, "Página não encontrada");
                            ctx.result(JavalinJson.toJson(ex));
                        }
                    })
                    .exception(GenericApiException.class, (e, ctx) -> {
                        ctx.attribute("handle", true);
                        String response = JavalinJson.getToJsonMapper().map(e);
                        ctx.status(e.getCodigo());
                        ctx.result(response);
                    });


    public void start(int port) {
        this.app.start(port);
    }

    public void stop()  {
        this.app.stop();
    }
}
