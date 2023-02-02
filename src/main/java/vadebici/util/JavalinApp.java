package vadebici.util;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.controller.CiclistaController;
import vadebici.servico_aluguel.controller.FuncionarioController;
import vadebici.servico_equipamento.controller.BicicletaController;
import vadebici.servico_equipamento.controller.TotemController;
import vadebici.servico_equipamento.controller.TrancaController;
import vadebici.servico_externo.controller.EmailController;
import vadebici.servico_externo.controller.PagamentoController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinApp {

    private static String PATH_TRANCA = "/tranca/:idTranca";
    private static String PATH_BICICLETA = "/bicicleta";
    private static String PATH_BICICLETA_ID = "/bicicleta/:idBicicleta";

    private final Javalin app =
            Javalin.create(config -> config.defaultContentType = "application/json")
                    .routes(() -> {
                        // Serviço Aluguel

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
                        path("/devolucao", () -> post(CiclistaController::devolver));

                        // Serviço Equipamento

                        path("/totem", () -> get(TotemController::buscarTotens));
                        path("/totem", () -> post(TotemController::salvarTotem));
                        path("/totem/:idTotem", () -> delete(TotemController::deletarTotem));
                        path("/totem/:idTotem", () -> put(TotemController::atualizarTotem));
                        path("/totem/:idTotem/trancas", () -> get(TotemController::buscarTrancasPorTotem));
                        path("/totem/:idTotem/bicicletas", () -> get(TotemController::buscarBicicletasPorTotem));

                        path("/tranca", () -> get(TrancaController::buscarTrancas));
                        path("/tranca", () -> post(TrancaController::salvarTranca));
                        path(PATH_TRANCA, () -> get(TrancaController::buscarTranca));
                        path(PATH_TRANCA, () -> delete(TrancaController::deletarTranca));
                        path(PATH_TRANCA, () -> put(TrancaController::atualizarTranca));
                        path(PATH_TRANCA + "/status/:acao", () -> post(TrancaController::alterarStatus));
                        path("/tranca/integrarNaRede", () -> post(TrancaController::integrarNaRede));
                        path("/tranca/removerDaRede", () -> post(TrancaController::removerDaRede));
                        path(PATH_TRANCA + PATH_BICICLETA, () -> get(TrancaController::buscaBicicletaPorTranca));

                        path(PATH_BICICLETA, () -> get(BicicletaController::buscarBicicletas));
                        path(PATH_BICICLETA, () -> post(BicicletaController::salvarBicicleta));
                        path(PATH_BICICLETA_ID, () -> get(BicicletaController::buscarBicicleta));
                        path(PATH_BICICLETA_ID, () -> delete(BicicletaController::deletarBicicleta));
                        path(PATH_BICICLETA_ID, () -> put(BicicletaController::atualizarBicicleta));
                        path(PATH_BICICLETA_ID + "/status/:acao", () -> post(BicicletaController::alterarStatus));
                        path("/bicicleta/integrarNaRede", () -> post(BicicletaController::integrarNaRede));
                        path("/bicicleta/removerDaRede", () -> post(BicicletaController::removerDaRede));

                        // Serviço Externo

                        path("/enviarEmail", () -> post(EmailController::enviarEmail));
                        path("/cobranca", () -> post(PagamentoController::realizarCobranca));
                        path("/filaCobranca", () -> post(PagamentoController::adicionarNaFilaCobranca));
                        path("/processaCobrancasEmFila", () -> post(PagamentoController::processaCobrancasEmFila));
                        path("/cobranca/:idCobranca", () -> get(PagamentoController::buscarCobranca));
                        path("/validaCartaoDeCredito", () -> post(PagamentoController::validarCartao));


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
