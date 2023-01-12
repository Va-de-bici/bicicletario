package vadebici.servico_aluguel.repo;

import vadebici.servico_aluguel.dom.*;
import vadebici.servico_aluguel.dom.dto.CiclistaJsonApi;
import vadebici.exception.GenericApiException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import java.util.Date;

public class CiclistaRepositorio {

    private final Map<String, Ciclista> tabelaCiclista;
    private final Map<String, Aluguel> tabelaAluguel;

    public CiclistaRepositorio(Map<String, Ciclista> tabelaCiclista, Map<String, Aluguel> tabelaAluguel) {
        this.tabelaCiclista = tabelaCiclista;
        this.tabelaAluguel = tabelaAluguel;
    }

    public Ciclista retornaCiclista(String id) {
        Ciclista ciclista = tabelaCiclista.get(id);
        if (ciclista == null) throw new GenericApiException(404, "Ciclista não encontrado");
        return ciclista;
    }

    public boolean checaEmails(String email) {
        return buscaPorEmail(email).isPresent();
    }

    public Optional<Ciclista> buscaPorEmail(String email) {
        return this.tabelaCiclista.keySet()
                .stream()
                .map(this.tabelaCiclista::get)
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }

    public Ciclista cadastraCiclista(Ciclista ciclista) {
        String id = UUID.randomUUID().toString();
        Optional<Ciclista>  ciclistaOptional = verificaSeExisteIdCiclista(id);

        if (ciclistaOptional.isPresent()) {
            boolean novoId = false;

            while (!novoId) {
                id = UUID.randomUUID().toString();
                ciclistaOptional = verificaSeExisteIdCiclista(id);

                if (!ciclistaOptional.isPresent())
                    novoId = true;
            }
        }

        ciclista.setId(id);
        ciclista.setStatus(false);
        this.tabelaCiclista.put(id, ciclista);
        return ciclista;
    }

    public CiclistaJsonApi confirmaEmail(String id) {
        Ciclista ciclista = retornaCiclista(id);
        if (Boolean.TRUE.equals(ciclista.getStatus())) throw new GenericApiException(405, "Esta conta já foi confirmada.");
        ciclista.setStatus(true);
        this.tabelaCiclista.replace(id, ciclista);
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
        this.tabelaCiclista.replace(idCiclista, ciclista);
    }


    public Ciclista alteraCiclista(String id, CiclistaJsonApi ciclistaJsonApi) {
        Ciclista ciclista = retornaCiclista(id);
        ciclista.setCpf(ciclistaJsonApi.getCpf());
        ciclista.setNascimento(ciclistaJsonApi.getNascimento());
        ciclista.setNome(ciclistaJsonApi.getNome());
        ciclista.setEmail(ciclistaJsonApi.getEmail());
        ciclista.setNacionalidade(ciclistaJsonApi.getNacionalidade());
        ciclista.setPassaporte(ciclistaJsonApi.getPassaporte());
        ciclista.setUrlFotoDocumento(ciclistaJsonApi.getUrlFotoDocumento());
        this.tabelaCiclista.replace(id, ciclista);
        return ciclista;
    }

    public void alteraStatusCiclista(String id, Boolean status){
        Ciclista ciclista = retornaCiclista(id);
        ciclista.setStatus(status);
        this.tabelaCiclista.put(id, ciclista);
    }

    public Aluguel alugar(Ciclista ciclista, Tranca tranca, Bicicleta bicicleta, Cobranca cobranca) {
        String id = UUID.randomUUID().toString();
        Optional<Aluguel>  aluguelOptional = verificaSeExisteIdAluguel(id);

        if (aluguelOptional.isPresent()) {
            boolean novoId = false;

            while (!novoId) {
                id = UUID.randomUUID().toString();
                aluguelOptional = verificaSeExisteIdAluguel(id);

                if (!aluguelOptional.isPresent())
                    novoId = true;
            }
        }

        Aluguel aluguel = new Aluguel();
        aluguel.setId(id);
        aluguel.setCiclista(ciclista.getId());
        aluguel.setBicicleta(bicicleta.getId());
        String[] cobrancas = new String[2];
        cobrancas[0] = cobranca.getId();
        aluguel.setCobranca(cobrancas);
        aluguel.setHoraInicio(new Date());
        aluguel.setTrancaInicio(tranca.getId());
        this.tabelaAluguel.put(aluguel.getId(), aluguel);
        return aluguel;
    }

    public Aluguel devolver(Aluguel aluguel, Tranca tranca) {
        aluguel.setHoraFim(new Date());
        aluguel.setTrancaFim(tranca.getId());
        this.tabelaAluguel.replace(aluguel.getId(), aluguel);

        return aluguel;
    }

    public Optional<Ciclista> verificaSeExisteIdCiclista(String id) {
        return this.tabelaCiclista.keySet()
                .stream()
                .map(this.tabelaCiclista::get)
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Optional<Aluguel> verificaSeExisteIdAluguel(String id) {
        return this.tabelaAluguel.keySet()
                .stream()
                .map(this.tabelaAluguel::get)
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Optional<Aluguel> buscaAluguelEmCursoPorCiclista(Ciclista ciclista) {
        return this.tabelaAluguel.keySet()
                .stream()
                .map(this.tabelaAluguel::get)
                .filter(e -> e.getCiclista().equals(ciclista.getId()) && e.getHoraFim() == null)
                .findFirst();
    }

    public Aluguel buscaAluguelEmCursoPorBicicleta(Bicicleta bicicleta) {
        return this.tabelaAluguel.keySet()
                .stream()
                .map(this.tabelaAluguel::get)
                .filter(e -> e.getBicicleta().equals(bicicleta.getId()) && e.getHoraFim() == null)
                .findFirst().orElseThrow(() -> new GenericApiException(422, "Bicicleta não está alugada"));
    }

}
