package vadebici.controller;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;
import vadebici.db_in_memory.Database;
import vadebici.dom.Funcionario;
import vadebici.util.JavalinApp;

import static org.junit.jupiter.api.Assertions.*;
import static vadebici.model.FuncModels.getFuncionario;

class FuncionarioControllerTest {

    private final static JavalinApp app = new JavalinApp();
    private static final String BASE_URL = "http://localhost:7010";

    @BeforeAll
    static void init() {
        app.start(7010);
    }
    @BeforeEach
    void setUp() {
        Database.carregarDados();
    }

    @AfterAll
    static void tearDown() {
        Database.limpar();
        app.stop();
    }

    @Test
    void salvarFuncionario() {
        Funcionario funcionario = getFuncionario();
        funcionario.setId(null);
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/funcionario")
                .body(funcionario)
                .asJson();

        JSONObject jsonResponse = response.getBody().getObject();

        assertEquals(201, response.getStatus());
        assertEquals(funcionario.getEmail(), jsonResponse.get("email"));
        assertEquals(funcionario.getCpf(), jsonResponse.get("cpf"));
        assertEquals(funcionario.getSenha(), jsonResponse.get("senha"));
        assertEquals(funcionario.getNome(), jsonResponse.get("nome"));
        assertEquals(funcionario.getFuncao(), jsonResponse.get("funcao"));
        assertEquals(funcionario.getIdade(), jsonResponse.get("idade"));
        assertNotNull(jsonResponse.get("id"));
    }

    @Test
    void buscarFuncionarios() {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + "/funcionario")
                .asJson();

        JSONArray jsonResponse = response.getBody().getArray();

        assertEquals(200, response.getStatus());
        assertEquals(1, jsonResponse.length());
    }

}