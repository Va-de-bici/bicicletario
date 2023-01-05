package vadebici.servico_aluguel.repo;

import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.db_in_memory.Database;
import vadebici.servico_aluguel.dom.Funcionario;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FuncionarioRepositorio {
    public List<Funcionario> retornaFuncionarios() {
        return Database.TABELA_FUNCIONARIO.keySet()
                .stream()
                .map(Database.TABELA_FUNCIONARIO::get)
                .collect(Collectors.toList());
    }

    public Funcionario retornaFuncionario(String id) {
        Funcionario funcionario = Database.TABELA_FUNCIONARIO.get(id);
        if (funcionario == null) throw new GenericApiException(404, "Funcionário não encontrado.");
        return funcionario;
    }

    public Funcionario guardaFuncionario(Funcionario funcionario) {
        funcionario.setId(UUID.randomUUID().toString());
        Database.TABELA_FUNCIONARIO.put(funcionario.getId(), funcionario);
        return funcionario;
    }

    public Funcionario alteraFuncionario(String id, Funcionario novoFuncionario) {
        retornaFuncionario(id);
        novoFuncionario.setId(id);
        Database.TABELA_FUNCIONARIO.put(id, novoFuncionario);
        return novoFuncionario;
    }

    public void deletaFuncionario(String id) {
        retornaFuncionario(id);
        Database.TABELA_FUNCIONARIO.remove(id);
    }

}
