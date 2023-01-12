package vadebici.servico_aluguel.service;

import vadebici.servico_aluguel.dom.*;

import java.util.UUID;

public class ServicoEquipamento {
    public Bicicleta alteraStatusBicicleta(Bicicleta bicicleta, String status){
        bicicleta.setStatus(status);

        return bicicleta;
    }

    public Tranca alteraStatusTranca(Tranca tranca, String status){
        tranca.setStatus(status);

        return tranca;
    }

    public Tranca removeBicicletaDaRede(Tranca tranca){
        tranca.setBicicleta(null);

        return tranca;
    }

    public Tranca insereBicicletaNaRede(Tranca tranca, Bicicleta bicicleta){
        tranca.setBicicleta(bicicleta.getId());

        return tranca;
    }

    public Tranca retornaTranca(String id) {
        Tranca tranca = new Tranca();

        tranca.setId(id);
        tranca.setStatus("OCUPADA");
        tranca.setBicicleta(UUID.randomUUID().toString());

        return tranca;
    }

    public Bicicleta retornaBicicleta(String id) {
        Bicicleta bicicleta = new Bicicleta();

        bicicleta.setId(id);
        bicicleta.setStatus("DISPONIVEL");

        return bicicleta;
    }

}
