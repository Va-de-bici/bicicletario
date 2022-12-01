package vadebici.db_in_memory;

import vadebici.dom.Funcionario;

import java.util.*;

public class Database {
    public static final Map<String, Funcionario> TABELA_FUNCIONARIO = new HashMap<>();

    public static void limpar() {
        TABELA_FUNCIONARIO.clear();
    }

    public static void carregarDados() {
        limpar();
        Funcionario funcionario = getFuncionario();
        TABELA_FUNCIONARIO.put(funcionario.getId(), funcionario);

    }

    private static Funcionario getFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Leandro");
        funcionario.setId("1");
        funcionario.setIdade(22);
        funcionario.setCpf("99999999999");
        funcionario.setEmail("leandroabreusa@edu.unirio.br");
        funcionario.setSenha("senha");
        funcionario.setFuncao("Reparador");
        return funcionario;
    }
}
