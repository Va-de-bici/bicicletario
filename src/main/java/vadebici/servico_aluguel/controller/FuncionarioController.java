package vadebici.servico_aluguel.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.dom.Funcionario;
import vadebici.servico_aluguel.repo.FuncionarioRepositorio;
import vadebici.servico_aluguel.util.Validacao;

import java.util.List;

public class FuncionarioController {
    public static final FuncionarioRepositorio FUNC_REPO = new FuncionarioRepositorio();
    public static final Validacao VALIDACAO = new Validacao();

    private static final String CAMPO_ID_FUNCIONARIO = "idFuncionario";

    private FuncionarioController(){}

    public static void guardaFunc(Context ctx) {
        String body = ctx.body();
        Funcionario postFunc = JavalinJson.getFromJsonMapper().map(body, Funcionario.class);
        VALIDACAO.validaAtributos(postFunc, 422);
        Funcionario func = FUNC_REPO.guardaFuncionario(postFunc);
        String response = JavalinJson.toJson(func);
        ctx.result(response);
        ctx.status(201);
    }

    public static void retornaFuncs(Context ctx) {
        List<Funcionario> funcList = FUNC_REPO.retornaFuncionarios();
        String response = JavalinJson.toJson(funcList);
        ctx.result(response);
        ctx.status(200);
    }

    public static void retornaFunc(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_FUNCIONARIO);
        Funcionario resposta = FUNC_REPO.retornaFuncionario(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resposta));
    }

    public static void alteraFunc(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(CAMPO_ID_FUNCIONARIO);
        Funcionario funcionario = JavalinJson.getFromJsonMapper().map(body, Funcionario.class);
        VALIDACAO.validaAtributos(funcionario, 422);
        Funcionario response = FUNC_REPO.alteraFuncionario(id, funcionario);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(response));
    }

    public static void deletaFunc(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_FUNCIONARIO);
        FUNC_REPO.deletaFuncionario(id);
        ctx.status(200);
    }
}
