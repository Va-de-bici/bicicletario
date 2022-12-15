package vadebici.servico_aluguel.db_in_memory;

import vadebici.servico_aluguel.dom.*;

import java.util.*;

public class Database {
    private Database() {

    }

    public static final Map<String, Funcionario> TABELA_FUNCIONARIO = new HashMap<>();
    public static final Map<String, Ciclista> TABELA_CICLISTA = new HashMap<>();

    public static void limpar() {
        TABELA_CICLISTA.clear();
        TABELA_FUNCIONARIO.clear();
    }

    public static void carregarDados() {
        limpar();
        Ciclista ciclista = getCiclista();
        Funcionario funcionario = getFuncionario();
        TABELA_CICLISTA.put(ciclista.getId(), ciclista);
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

    private static Ciclista getCiclista() {
        MeioDePagamento meioDePagamento = new MeioDePagamento();
        meioDePagamento.setNomeTitular("LEANDRO ABREU");
        meioDePagamento.setValidade(new GregorianCalendar(2024, Calendar.JANUARY,1, 12, 0, 0).getTime());
        meioDePagamento.setNumero("12345678910123");
        meioDePagamento.setCvv("123");

        Ciclista ciclista = new Ciclista();
        ciclista.setId("1");
        ciclista.setNome("Leandro");
        ciclista.setCpf("99999999999");
        ciclista.setEmail("leandroabreusa@edu.unirio.br");
        ciclista.setNacionalidade("BRA");
        ciclista.setSenha("12345678");
        ciclista.setStatus(true);
        ciclista.setNascimento(new GregorianCalendar(2000, Calendar.APRIL, 19, 12, 0, 0).getTime());
        ciclista.setMeioDePagamento(meioDePagamento);
        ciclista.setUrlFotoDocumento("https://imagens.ebc.com.br/JR7Fb8ISNmbrQZq0A-I6sSAbDDs=/1170x700/smart/https://agenciabrasil.ebc.com.br/sites/default/files/thumbnails/image/cpf_receita_federal0107200467.jpg?itok=iX_mVn8n");
        return ciclista;
    }
}
