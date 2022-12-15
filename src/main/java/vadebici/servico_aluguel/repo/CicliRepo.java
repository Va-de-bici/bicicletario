package vadebici.servico_aluguel.repo;

import vadebici.servico_aluguel.db_in_memory.Database;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.servico_aluguel.exception.GenericApiException;
import vadebici.servico_aluguel.service.ServicoExterno;
import vadebici.servico_aluguel.util.Validacao;
import vadebici.servico_aluguel.util.Url;

import java.util.Optional;
import java.util.UUID;

public class CicliRepo {
    private ServicoExterno servicoExterno;

    public CicliRepo(ServicoExterno servicoExterno) {
        this.servicoExterno = servicoExterno;
    }

    private void enviaEmail(String destinatario, String mensagem) {
        Email email = new Email();
        email.setEnderecoEmail(destinatario);
        email.setMensagem(mensagem);
        servicoExterno.enviarEmail(email);
    }

    public Ciclista retornaCiclista(String id) {
        Ciclista ciclista = Database.TABELA_CICLISTA.get(id);
        if (ciclista == null) throw new GenericApiException(404, "Ciclista não encontrado");
        return ciclista;
    }

    public boolean checaEmails(String email) {
        return buscaPorEmail(email).isPresent();
    }

    private Optional<Ciclista> buscaPorEmail(String email) {
        return Database.TABELA_CICLISTA.keySet()
                .stream()
                .map(Database.TABELA_CICLISTA::get)
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }

    public Ciclista cadastraCiclista(Ciclista ciclista) {
        Validacao.validaAtributos(ciclista, 405);
        if (buscaPorEmail(ciclista.getEmail()).isPresent()) throw new GenericApiException(405, "Email já cadastrado");
        Validacao.validaNacionalidade(ciclista);
        servicoExterno.validaCartao(ciclista.getMeioDePagamento());
        String id = UUID.randomUUID().toString();
        ciclista.setId(id);
        ciclista.setStatus(false);
        Database.TABELA_CICLISTA.put(id, ciclista);
        String mensagem = String.format("Cadastro realizado com sucesso. Por favor, confirme seu cadastro " +
                "clicando no link\n\n %s/ciclista/%s/ativar", Url.BASE_URL, ciclista.getId());
        enviaEmail(ciclista.getEmail(), mensagem);
        return ciclista;
    }

    public CiclistaJsonApi confirmaEmail(String id) {
        Ciclista ciclista = retornaCiclista(id);
        if (Boolean.TRUE.equals(ciclista.getStatus())) throw new GenericApiException(405, "Esta conta já foi confirmada.");
        ciclista.setStatus(true);
        Database.TABELA_CICLISTA.replace(id, ciclista);
        CiclistaJsonApi ciclistaJsonApi = new CiclistaJsonApi();
        ciclistaJsonApi.setId(ciclista.getId());
        ciclistaJsonApi.setStatus(ciclista.getStatus());
        ciclistaJsonApi.setEmail(ciclista.getEmail());
        ciclistaJsonApi.setNacionalidade(ciclista.getNacionalidade());
        ciclistaJsonApi.setCpf(ciclista.getCpf());
        ciclistaJsonApi.setNome(ciclista.getNome());
        ciclistaJsonApi.setPassaporte(ciclista.getPassaporte());
        return ciclistaJsonApi;
    }

    public MeioDePagamento retornaCartaoPorCiclista(String idCiclista) {
        Ciclista ciclista = retornaCiclista(idCiclista);
        return ciclista.getMeioDePagamento();
    }

    public void alteraCiclista(String id, CiclistaJsonApi ciclistaJsonApi) {
        Validacao.validaAtributos(ciclistaJsonApi, 405);
        Ciclista ciclista = retornaCiclista(id);
        ciclista.setCpf(ciclistaJsonApi.getCpf());
        ciclista.setNascimento(ciclistaJsonApi.getNascimento());
        ciclista.setNome(ciclistaJsonApi.getNome());
        ciclista.setEmail(ciclistaJsonApi.getEmail());
        ciclista.setNacionalidade(ciclistaJsonApi.getNacionalidade());
        ciclista.setPassaporte(ciclistaJsonApi.getPassaporte());
        ciclista.setStatus(ciclistaJsonApi.getStatus());
        Validacao.validaNacionalidade(ciclista);
        Database.TABELA_CICLISTA.put(id, ciclista);
        ciclistaJsonApi.setId(id);
        enviaEmail(ciclista.getEmail(), "Dados alterados com sucesso.\n\n" + ciclista);
    }
}
