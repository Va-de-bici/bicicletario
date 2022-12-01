package vadebici.repo;

import vadebici.db_in_memory.Database;
import vadebici.dom.Funcionario;
import vadebici.util.Validacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FuncRepo {
    public List<Funcionario> retornaFuncionarios() {
        return Database.TABELA_FUNCIONARIO.keySet()
                .stream()
                .map(Database.TABELA_FUNCIONARIO::get)
                .collect(Collectors.toList());
    }

    public Funcionario guardaFuncionario(Funcionario funcionario) {
        Validacao.validarCampos(funcionario);
        funcionario.setId(UUID.randomUUID().toString());
        Database.TABELA_FUNCIONARIO.put(funcionario.getId(), funcionario);
        return funcionario;
    }
}
