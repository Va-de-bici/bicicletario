package vadebici.util;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import vadebici.controller.FuncionarioController;
import vadebici.exception.GenericApiException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinApp {
    private final Javalin app =
            Javalin.create(config -> config.defaultContentType = "application/json")
                    .routes(() -> {
                        path("/funcionario", () -> get(FuncionarioController::retornaFunc));
                        path("/funcionario", () -> post(FuncionarioController::guardaFunc));
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

    public void stop() {
        this.app.stop();
    }
}
