package vadebici.servico_equipamento.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_equipamento.service.ServicoExterno;
import vadebici.servico_equipamento.dom.Bicicleta;
import vadebici.servico_equipamento.dom.Email;
import vadebici.servico_equipamento.dom.dto.PostBicicleta;
import vadebici.servico_equipamento.dom.dto.BicicletaNaRede;
import vadebici.servico_equipamento.repo.BicicletaRepositorio;
import vadebici.servico_equipamento.util.Validacao;

import java.util.List;


public class BicicletaController {
    private static final String PATH_ID = "idBicicleta";

    public static final BicicletaRepositorio bicicletaRepositorio = new BicicletaRepositorio();

    public static ServicoExterno servicoExterno =  new ServicoExterno();

    private BicicletaController(){}

    public static void salvarBicicleta(Context ctx) {
        String body = ctx.body();
        PostBicicleta postBicicleta = JavalinJson.getFromJsonMapper().map(body, PostBicicleta.class);
        Validacao.validarCampos(postBicicleta);
        Bicicleta bicicleta = bicicletaRepositorio.salvarBicicleta(postBicicleta);
        String response = JavalinJson.toJson(bicicleta);
        ctx.result(response);
        ctx.status(201);
    }

    public static void buscarBicicletas(Context ctx) {
        List<Bicicleta> bicicletaList = bicicletaRepositorio.buscarBicicletas();
        String response = JavalinJson.toJson(bicicletaList);
        ctx.result(response);
        ctx.status(200);
    }

    public static void buscarBicicleta(Context ctx) {
        String bicicletaId = ctx.pathParam(PATH_ID);
        Bicicleta bicicleta = bicicletaRepositorio.buscarBicicleta(bicicletaId);
        String response = JavalinJson.toJson(bicicleta);
        ctx.result(response);
        ctx.status(200);
    }

    public static void atualizarBicicleta(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(PATH_ID);
        Bicicleta bicicleta = JavalinJson.getFromJsonMapper().map(body, Bicicleta.class);
        Validacao.validarCampos(bicicleta);
        Bicicleta resultado = bicicletaRepositorio.atualizarBicicleta(id, bicicleta);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));

    }

    public static void deletarBicicleta(Context ctx) {
        String id = ctx.pathParam(PATH_ID);
        bicicletaRepositorio.deletarBicicleta(id);
        ctx.status(200);
    }

    public static void alterarStatus(Context ctx) {
        String id = ctx.pathParam(PATH_ID);
        String status = ctx.pathParam("acao");
        Bicicleta bicicleta = bicicletaRepositorio.alterarStatus(id, status);
        String response = JavalinJson.getToJsonMapper().map(bicicleta);
        ctx.status(200);
        ctx.result(response);
    }

    public static void integrarNaRede(Context ctx) {
        String body = ctx.body();
        BicicletaNaRede bicicletaNaRede = JavalinJson.getFromJsonMapper().map(body, BicicletaNaRede.class);
        bicicletaRepositorio.integrarNaRede(bicicletaNaRede);
        enviaEmail("erick.m.val@edu.unirio.br", "Bicicleta incluída na rede");
        ctx.status(200);
    }

    public static void removerDaRede(Context ctx) {
        String body = ctx.body();
        BicicletaNaRede bicicletaNaRede = JavalinJson.getFromJsonMapper().map(body, BicicletaNaRede.class);
        bicicletaRepositorio.removerDaRede(bicicletaNaRede);
        enviaEmail("erick.m.val@edu.unirio.br", "Bicicleta removida da rede");
        ctx.status(200);
    }

    public static void enviaEmail(String destinatario, String mensagem) {
        Email email = new Email();
        email.setEnderecoEmail(destinatario);
        email.setMensagem(mensagem);
        servicoExterno.enviaEmail(email);
    }
}
