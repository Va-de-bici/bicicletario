package vadebici.servico_externo.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_externo.dom.Cartao;
import vadebici.servico_externo.dom.Cobranca;
import vadebici.servico_externo.dom.CobrancaRequisicao;
import vadebici.servico_externo.service.ServicoAluguel;
import vadebici.servico_externo.service.ServicoPagamento;


public class PagamentoController {
    private static final ServicoPagamento servicoPagamento = new ServicoPagamento(new ServicoAluguel());

    private PagamentoController(){}

    public static void realizarCobranca(Context ctx) {
        String body = ctx.body();
        CobrancaRequisicao cobrancaRequisicao = JavalinJson.getFromJsonMapper().map(body, CobrancaRequisicao.class);
        Cobranca cobranca = servicoPagamento.realizarCobranca(cobrancaRequisicao);
        String response = JavalinJson.toJson(cobranca);
        ctx.result(response);
        ctx.status(200);
    }

    public static void buscarCobranca(Context ctx) {
        String idCobranca = ctx.pathParam("idCobranca");
        Cobranca cobranca = servicoPagamento.buscarCobranca(idCobranca);
        String response = JavalinJson.getToJsonMapper().map(cobranca);
        ctx.result(response);
        ctx.status(200);
    }

    public static void validarCartao(Context ctx) {
        String body = ctx.body();
        Cartao cartao = JavalinJson.getFromJsonMapper().map(body, Cartao.class);
        servicoPagamento.validarCartao(cartao);
        ctx.status(200);
        ctx.result(JavalinJson.toJson("Cartão validado com sucesso!"));

    }

    public static void adicionarNaFilaCobranca(Context ctx) {
        String body = ctx.body();
        CobrancaRequisicao cobrancaRequisicao = JavalinJson.getFromJsonMapper().map(body, CobrancaRequisicao.class);
        Cobranca cobranca = servicoPagamento.adicionarCobrancaNaFila(cobrancaRequisicao);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(cobranca));
    }

    public static void processaCobrancasEmFila(Context ctx) {
        servicoPagamento.processaCobrancas();

        ctx.status(200);
        ctx.result(JavalinJson.toJson("Cobranças retiradas da fila com sucesso!"));
    }
}
