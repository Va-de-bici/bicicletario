package vadebici.servico_aluguel.controller;

import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import vadebici.servico_aluguel.db_in_memory.Database;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.AluguelJsonApi;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.servico_aluguel.dom.dto.CobrancaJsonApi;
import vadebici.servico_aluguel.dom.dto.DevolucaoJsonApi;
import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.repo.CiclistaRepositorio;
import vadebici.servico_aluguel.service.ServicoEquipamento;
import vadebici.servico_aluguel.service.ServicoExterno;
import vadebici.servico_aluguel.util.Validacao;

import java.util.Date;
import java.util.Optional;


public class CiclistaController {
    private CiclistaController() {
        throw new IllegalStateException("Utility class");
    }

    public static CiclistaRepositorio ciclistaRepositorio = new CiclistaRepositorio(Database.TABELA_CICLISTA, Database.TABELA_ALUGUEL);

    public static Validacao validacao = new Validacao();

    public static ServicoExterno servicoExterno =  new ServicoExterno();

    public static ServicoEquipamento servicoEquipamento =  new ServicoEquipamento();

    private static final String CAMPO_ID_CICLISTA = "idCiclista";
    private static final String BICICLETA_ALUGADA = "EM_USO";
    private static final String TRANCA_SEM_BICICLETA = "LIVRE";

    private static final String URL_API = "https://va-de-bici.herokuapp.com";

    public static void guardaCiclista(Context ctx) {
        String body = ctx.body();
        Ciclista postCiclista = JavalinJson.getFromJsonMapper().map(body, Ciclista.class);
        validacao.validaAtributos(postCiclista, 405);
        validacao.validaNacionalidade(postCiclista);
        servicoExterno.validaCartao(postCiclista.getMeioDePagamento());
        if (ciclistaRepositorio.buscaPorEmail(postCiclista.getEmail()).isPresent()) throw new GenericApiException(405, "Email já cadastrado");
        Ciclista ciclista = ciclistaRepositorio.cadastraCiclista(postCiclista);
        String mensagem = String.format("Cadastro realizado com sucesso. Por favor, confirme seu cadastro " +
                "clicando no link\n\n" + URL_API + "/ciclista/%s/ativar", ciclista.getId());
        enviaEmail(ciclista.getEmail(), mensagem);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(201);
    }

    public static void retornaCiclista(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = ciclistaRepositorio.retornaCiclista(ciclistaId);
        String response = JavalinJson.toJson(ciclista);
        ctx.result(response);
        ctx.status(200);
    }

    public static void alteraCiclista(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        CiclistaJsonApi ciclistaJsonApi = JavalinJson.getFromJsonMapper().map(body, CiclistaJsonApi.class);
        validacao.validaAtributos(ciclistaJsonApi, 405);
        validacao.validaNacionalidadeJson(ciclistaJsonApi);
        if (ciclistaRepositorio.buscaPorEmail(ciclistaJsonApi.getEmail()).isPresent()) throw new GenericApiException(405, "Email já cadastrado");
        ciclistaRepositorio.alteraCiclista(id, ciclistaJsonApi);
        ciclistaJsonApi.setId(id);
        enviaEmail(ciclistaJsonApi.getEmail(), "Dados alterados com sucesso.\n\n" + ciclistaJsonApi);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(ciclistaJsonApi));
    }

    public static void verificaEmail(Context ctx) {
        String email = ctx.pathParam("email");

        boolean existe = ciclistaRepositorio.checaEmails(email);

        String response = JavalinJson.toJson(existe);
        ctx.result(response);
        ctx.status(200);
    }

    public static void emailNaoEnviado(Context ctx) {
        throw new GenericApiException(400, "Email não enviado como parâmetro");
    }

    public static void ativaCadastro(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        CiclistaJsonApi resultado = ciclistaRepositorio.confirmaEmail(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));

    }

    public static void retornaCartao(Context ctx) {
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        MeioDePagamento resultado = ciclistaRepositorio.retornaCartaoPorCiclista(id);
        ctx.status(200);
        ctx.result(JavalinJson.toJson(resultado));
    }

    public static void alteraCartao(Context ctx) {
        String body = ctx.body();
        String id = ctx.pathParam(CAMPO_ID_CICLISTA);
        MeioDePagamento meioDePagamento = JavalinJson.getFromJsonMapper().map(body, MeioDePagamento.class);
        validacao.validaAtributos(meioDePagamento, 422);
        servicoExterno.validaCartao(meioDePagamento);
        ciclistaRepositorio.alteraCartaoPorCiclista(id, meioDePagamento);
        ctx.status(200);
        ctx.result(JavalinJson.toJson("Dados atualizados"));
    }

    public static void enviaEmail(String destinatario, String mensagem) {
        Email email = new Email();
        email.setEnderecoEmail(destinatario);
        email.setMensagem(mensagem);
        servicoExterno.enviaEmail(email);
    }

    public static void alugar(Context ctx) {
        String body = ctx.body();
        AluguelJsonApi aluguelJsonApi = JavalinJson.getFromJsonMapper().map(body, AluguelJsonApi.class);
        validacao.validaAtributos(aluguelJsonApi, 422);
        Ciclista ciclista = ciclistaRepositorio.retornaCiclista(aluguelJsonApi.getCiclista());
        Optional<Aluguel> aluguelOptional = ciclistaRepositorio.buscaAluguelEmCursoPorCiclista(ciclista);

        if (aluguelOptional.isPresent()) {
            enviaEmail(ciclista.getEmail(), "O aluguel foi bloqueado, pois já existe outro em andamento. \n\n" + aluguelOptional.get());
            throw new GenericApiException(422, "Ciclista não pode alugar a bicicleta, pois já possui outro aluguel em andamento");
        }

        Tranca tranca = servicoEquipamento.retornaTranca(aluguelJsonApi.getTrancaInicio());

        if (tranca.getBicicleta() == null)
            throw new GenericApiException(422, "Tranca vazia");

        CobrancaJsonApi cobrancaJsonApi = new CobrancaJsonApi();
        cobrancaJsonApi.setCiclista(ciclista.getId());
        cobrancaJsonApi.setValor(10.0);
        Cobranca cobranca = servicoExterno.realizaCobranca(cobrancaJsonApi);
        Bicicleta bicicleta = servicoEquipamento.retornaBicicleta(tranca.getBicicleta());

        if (bicicleta.getStatus().equals("EM REPARO"))
            throw new GenericApiException(422, "Há um reparo requisitado para a bicicleta e, portanto, esta bicicleta não pode ser alugada.");

        tranca = servicoEquipamento.removeBicicletaDaRede(tranca);
        bicicleta = servicoEquipamento.alteraStatusBicicleta(bicicleta, BICICLETA_ALUGADA);
        tranca = servicoEquipamento.alteraStatusTranca(tranca, TRANCA_SEM_BICICLETA);
        final Aluguel aluguel = ciclistaRepositorio.alugar(ciclista, tranca, bicicleta, cobranca);
        enviaEmail(ciclista.getEmail(), "Aluguel efetuado com sucesso \n\n" + aluguel);
        String response = JavalinJson.toJson(aluguel);
        ctx.result(response);
        ctx.status(200);
    }

    public static void permiteAluguel(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = ciclistaRepositorio.retornaCiclista(ciclistaId);
        Optional<Aluguel> aluguelOptional = ciclistaRepositorio.buscaAluguelEmCursoPorCiclista(ciclista);
        String response;

        if (aluguelOptional.isPresent())
            response = JavalinJson.toJson(false);
        else
            response = JavalinJson.toJson(true);

        ctx.result(response);
        ctx.status(200);
    }

    public static void notificaAluguelEmCurso(Context ctx) {
        String ciclistaId = ctx.pathParam(CAMPO_ID_CICLISTA);
        Ciclista ciclista = ciclistaRepositorio.retornaCiclista(ciclistaId);
        Optional<Aluguel> aluguelOptional = ciclistaRepositorio.buscaAluguelEmCursoPorCiclista(ciclista);
        if (aluguelOptional.isPresent()) {
            enviaEmail(ciclista.getEmail(), "O aluguel foi bloqueado, pois já existe outro em andamento. \n\n" + aluguelOptional.get());
            String response = JavalinJson.toJson(ciclista);
            ctx.result(response);
            ctx.status(200);
        } else {
            throw new GenericApiException(405, "Dados Inválidos - ciclista não possui aluguel em curso");
        }
    }

    public static void devolver(Context ctx) {
        String body = ctx.body();
        DevolucaoJsonApi devolucaoJsonApi = JavalinJson.getFromJsonMapper().map(body, DevolucaoJsonApi.class);

        validacao.validaAtributos(devolucaoJsonApi, 422);
        Bicicleta bicicleta = servicoEquipamento.retornaBicicleta(devolucaoJsonApi.getIdBicicleta());
        bicicleta.setStatus(BICICLETA_ALUGADA);
        Tranca tranca = servicoEquipamento.retornaTranca(devolucaoJsonApi.getIdTranca());
        tranca.setStatus(TRANCA_SEM_BICICLETA);

        if (!tranca.getStatus().equals(TRANCA_SEM_BICICLETA))
            throw new GenericApiException(422, "Tranca não está disponível");

        if (!bicicleta.getStatus().equals(BICICLETA_ALUGADA))
            throw new GenericApiException(422, "Bicicleta não está em uso");

        Aluguel aluguel = ciclistaRepositorio.buscaAluguelEmCursoPorBicicleta(bicicleta);
        Ciclista ciclista = ciclistaRepositorio.retornaCiclista(aluguel.getCiclista());
        Cobranca cobrancaExtra = realizarCobrancaExtra(aluguel);

        if (cobrancaExtra != null) {
            String[] cobranca = aluguel.getCobranca();
            cobranca[1] = cobrancaExtra.getId();
            aluguel.setCobranca(cobranca);
        }

        servicoEquipamento.insereBicicletaNaRede(tranca, bicicleta);
        servicoEquipamento.alteraStatusBicicleta(bicicleta, "DISPONIVEL");
        servicoEquipamento.alteraStatusTranca(tranca, "OCUPADA");
        aluguel = ciclistaRepositorio.devolver(aluguel, tranca);
        enviaEmail(ciclista.getEmail(), "Devolução realizada com sucesso \n\n" + aluguel);

        String response = JavalinJson.toJson(aluguel);
        ctx.result(response);
        ctx.status(200);
    }

    private static Cobranca realizarCobrancaExtra(Aluguel aluguel) {
        double tempo = (new Date().getTime() - aluguel.getHoraInicio().getTime()) / 1000.0 / 60.0; // converte o tempo em minutos
        if (tempo > 150.0) {
            double excesso = tempo - 120.0;
            CobrancaJsonApi cobrancaJsonApi = new CobrancaJsonApi();
            cobrancaJsonApi.setCiclista(aluguel.getCiclista());
            cobrancaJsonApi.setValor(5.0 * Math.floor(excesso / 30.0));    // A cada 30 min excedidos, são adicionados 5 reais na cobrança extra
            return servicoExterno.realizaCobranca(cobrancaJsonApi);
        }

        return null;
    }


}
