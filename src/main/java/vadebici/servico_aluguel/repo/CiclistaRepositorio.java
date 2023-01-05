package vadebici.servico_aluguel.repo;

import vadebici.exception.GenericApiException;
import vadebici.servico_aluguel.db_in_memory.Database;
import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class CiclistaRepositorio {

    public CiclistaRepositorio() {
        // Não é necessário nenhum parâmetro para instanciar o repositório de Ciclista
        // Todas as integrações são feitas por meio do controller.
    }

    public Ciclista retornaCiclista(String id) {
        Ciclista ciclista = Database.TABELA_CICLISTA.get(id);
        if (ciclista == null) throw new GenericApiException(404, "Ciclista não encontrado");
        return ciclista;
    }

    public boolean checaEmails(String email) {
        return buscaPorEmail(email).isPresent();
    }

    public Optional<Ciclista> buscaPorEmail(String email) {
        return Database.TABELA_CICLISTA.keySet()
                .stream()
                .map(Database.TABELA_CICLISTA::get)
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }

    public Ciclista cadastraCiclista(Ciclista ciclista) {
        String id = UUID.randomUUID().toString();
        ciclista.setId(id);
        ciclista.setStatus(false);
        Database.TABELA_CICLISTA.put(id, ciclista);
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
        ciclistaJsonApi.setNascimento(ciclista.getNascimento());
        ciclistaJsonApi.setPassaporte(ciclista.getPassaporte());
        ciclistaJsonApi.setUrlFotoDocumento(ciclista.getUrlFotoDocumento());
        return ciclistaJsonApi;
    }

    public MeioDePagamento retornaCartaoPorCiclista(String idCiclista) {
        Ciclista ciclista = retornaCiclista(idCiclista);
        return ciclista.getMeioDePagamento();
    }

    public void alteraCartaoPorCiclista(String idCiclista, MeioDePagamento novoMeioDePagamento) {
        Ciclista ciclista = retornaCiclista(idCiclista);
        ciclista.setMeioDePagamento(novoMeioDePagamento);
        Database.TABELA_CICLISTA.replace(idCiclista, ciclista);
    }


    public void alteraCiclista(String id, CiclistaJsonApi ciclistaJsonApi) {
        Ciclista ciclista = retornaCiclista(id);
        ciclista.setCpf(ciclistaJsonApi.getCpf());
        ciclista.setNascimento(ciclistaJsonApi.getNascimento());
        ciclista.setNome(ciclistaJsonApi.getNome());
        ciclista.setEmail(ciclistaJsonApi.getEmail());
        ciclista.setNacionalidade(ciclistaJsonApi.getNacionalidade());
        ciclista.setPassaporte(ciclistaJsonApi.getPassaporte());
        ciclista.setUrlFotoDocumento(ciclistaJsonApi.getUrlFotoDocumento());
        Database.TABELA_CICLISTA.put(id, ciclista);
    }

    public void alteraStatusCiclista(String id, Boolean status){
        Ciclista ciclista = retornaCiclista(id);
        ciclista.setStatus(status);
        Database.TABELA_CICLISTA.put(id, ciclista);
    }

    public Aluguel alugar(Ciclista ciclista, Tranca tranca, Bicicleta bicicleta, Cobranca cobranca) {
        Aluguel aluguel = new Aluguel();
        aluguel.setId(UUID.randomUUID().toString());
        aluguel.setCiclista(ciclista.getId());
        aluguel.setBicicleta(bicicleta.getId());
        aluguel.setCobranca(cobranca.getId());
        aluguel.setHoraInicio(new Date());
        aluguel.setTrancaInicio(tranca.getId());
        Database.TABELA_ALUGUEL.put(aluguel.getId(), aluguel);
        return aluguel;
    }

    public Optional<Aluguel> buscaAluguelEmCursoPorCiclista(Ciclista ciclista) {
        return Database.TABELA_ALUGUEL.keySet()
                .stream()
                .map(Database.TABELA_ALUGUEL::get)
                .filter(e -> e.getCiclista().equals(ciclista.getId()) && e.getHoraFim() == null)
                .findFirst();
    }
}
