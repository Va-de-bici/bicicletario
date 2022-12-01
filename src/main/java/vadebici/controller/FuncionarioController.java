package vadebici.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.dom.Funcionario;
import vadebici.repo.FuncRepo;

import java.util.List;

public class FuncionarioController {
    public static FuncRepo funcionarioRepositorio = new FuncRepo();

    private FuncionarioController(){}

    public static void guardaFunc(Context ctx) {
        String body = ctx.body();
        Funcionario postFunc = JavalinJson.getFromJsonMapper().map(body, Funcionario.class);
        Funcionario func = funcionarioRepositorio.guardaFuncionario(postFunc);
        String response = JavalinJson.toJson(func);
        ctx.result(response);
        ctx.status(201);
    }

    public static void retornaFunc(Context ctx) {
        List<Funcionario> funcList = funcionarioRepositorio.retornaFuncionarios();
        String response = JavalinJson.toJson(funcList);
        ctx.result(response);
        ctx.status(200);
    }
}
