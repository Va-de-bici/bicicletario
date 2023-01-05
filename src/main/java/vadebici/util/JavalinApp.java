package vadebici.util;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.controller.CiclistaController;
import vadebici.servico_aluguel.controller.FuncionarioController;
import vadebici.servico_equipamento.controller.TotemController;
import vadebici.servico_equipamento.controller.TrancaController;
import vadebici.servico_externo.controller.CartaoCreditoController;
import vadebici.servico_externo.controller.EmailController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinApp {

    private static String PATH_TRANCA = "/tranca/:idTranca";

    private final Javalin app =
            Javalin.create(config -> config.defaultContentType = "application/json")
                    .routes(() -> {
                        final String FUNCIONARIO_COM_ID = "/funcionario/:idFuncionario";

                        path("/funcionario", () -> get(FuncionarioController::retornaFuncs));
                        path("/funcionario", () -> post(FuncionarioController::guardaFunc));

                        path(FUNCIONARIO_COM_ID, () -> get(FuncionarioController::retornaFunc));
                        path(FUNCIONARIO_COM_ID, () -> delete(FuncionarioController::deletaFunc));
                        path(FUNCIONARIO_COM_ID, () -> put(FuncionarioController::alteraFunc));

                        path("/ciclista", () -> post(CiclistaController::guardaCiclista));
                        path("/ciclista/:idCiclista", () -> get(CiclistaController::retornaCiclista));
                        path("/ciclista/:idCiclista", () -> put(CiclistaController::alteraCiclista));
                        path("/ciclista/:idCiclista/ativar", () -> post(CiclistaController::ativaCadastro));
                        path("/ciclista/existeEmail/", () -> get(CiclistaController::emailNaoEnviado));
                        path("/ciclista/existeEmail/:email", () -> get(CiclistaController::verificaEmail));
                        path("/ciclista/:idCiclista/permiteAluguel", () -> get(CiclistaController::permiteAluguel));
                        path("/ciclista/:idCiclista/notificaAluguelEmCurso", () -> post(CiclistaController::notificaAluguelEmCurso));

                        path("/cartaoDeCredito/:idCiclista", () -> get(CiclistaController::retornaCartao));
                        path("/cartaoDeCredito/:idCiclista", () -> put(CiclistaController::alteraCartao));

                        path("/aluguel", () -> post(CiclistaController::alugar));

                        path("/totem", () -> post(TotemController::salvarTotem));
                        path("/totem", () -> get(TotemController::buscarTotens));
                        path("/totem/:idTotem", () -> delete(TotemController::deletarTotem));
                        path("/totem/:idTotem", () -> put(TotemController::atualizarTotem));
                        path("/totem/:idTotem/trancas", () -> get(TotemController::buscarTrancasPorTotem));

                        path("/tranca", () -> get(TrancaController::buscarTrancas));
                        path("/tranca", () -> post(TrancaController::salvarTranca));
                        path(PATH_TRANCA, () -> get(TrancaController::buscarTranca));
                        path(PATH_TRANCA, () -> delete(TrancaController::deletarTranca));
                        path(PATH_TRANCA, () -> put(TrancaController::atualizarTranca));
                        path(PATH_TRANCA + "/status/:acao", () -> post(TrancaController::alterarStatus));

                        path("/enviarEmail", () -> post(EmailController::enviaEmail));
                        path("/validaCartaoDeCredito", () -> post(CartaoCreditoController::validaCartaoCredito));

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
