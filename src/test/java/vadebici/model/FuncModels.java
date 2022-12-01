package vadebici.model;

import vadebici.dom.Funcionario;

public class FuncModels {
    public static Funcionario getFuncionario() {
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
