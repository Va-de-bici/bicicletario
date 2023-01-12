package vadebici.servico_aluguel.repo;

import vadebici.servico_aluguel.dom.Funcionario;
import vadebici.exception.GenericApiException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class FuncionarioRepositorio {
    private final Map<String, Funcionario> tabelaFuncionario;

    public FuncionarioRepositorio(Map<String, Funcionario> tabelaFuncionario) {
        this.tabelaFuncionario = tabelaFuncionario;
    }

    public List<Funcionario> retornaFuncionarios() {
        return this.tabelaFuncionario.keySet()
                .stream()
                .map(this.tabelaFuncionario::get)
                .collect(Collectors.toList());
    }

    public Funcionario retornaFuncionario(String id) {
        Funcionario funcionario = this.tabelaFuncionario.get(id);
        if (funcionario == null) throw new GenericApiException(404, "Funcionário não encontrado.");
        return funcionario;
    }

    public Funcionario guardaFuncionario(Funcionario funcionario) {
        String id = UUID.randomUUID().toString();
        Optional<Funcionario>  funcionarioOptional = verificaSeExisteIdFuncionario(id);

        if (funcionarioOptional.isPresent()) {
            boolean novoId = false;

            while (!novoId) {
                id = UUID.randomUUID().toString();
                funcionarioOptional = verificaSeExisteIdFuncionario(id);

                if (!funcionarioOptional.isPresent())
                    novoId = true;
            }
        }

        funcionario.setId(id);
        this.tabelaFuncionario.put(funcionario.getId(), funcionario);
        return funcionario;
    }

    public Funcionario alteraFuncionario(String id, Funcionario novoFuncionario) {
        retornaFuncionario(id);
        novoFuncionario.setId(id);
        this.tabelaFuncionario.put(id, novoFuncionario);
        return novoFuncionario;
    }

    public void deletaFuncionario(String id) {
        retornaFuncionario(id);
        this.tabelaFuncionario.remove(id);
    }

    public Optional<Funcionario> verificaSeExisteIdFuncionario(String id) {
        return this.tabelaFuncionario.keySet()
                .stream()
                .map(this.tabelaFuncionario::get)
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

}
