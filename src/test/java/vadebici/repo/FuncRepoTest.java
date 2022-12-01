package vadebici.repo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vadebici.db_in_memory.Database;
import vadebici.dom.Funcionario;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static vadebici.model.FuncModels.getFuncionario;

class FuncRepoTest {

    private final FuncRepo funcionarioRepositorio = new FuncRepo();

    @BeforeEach
    void setUp() {
        Database.carregarDados();
    }

    @AfterAll
    static void setDown() {
        Database.limpar();
    }

    @Test
    void cadastrarFuncionario_RetornaFuncionario_QuandoBemSucedido() {
        Funcionario funcionario = getFuncionario();
        Funcionario respostaFuncionario = funcionarioRepositorio.guardaFuncionario(funcionario);

        assertEquals(funcionario, respostaFuncionario);
    }

    @Test
    void buscarFuncionarios_RetornaListaFuncionario_QuandoBemSucedido() {
        List<Funcionario> resultado = funcionarioRepositorio.retornaFuncionarios();
        assertEquals(1, resultado.size());
    }

}