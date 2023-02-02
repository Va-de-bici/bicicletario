package vadebici.servico_aluguel.dom.dto;

public class BiciclistaNaRedeJsonApi {
    private String idBicicleta;
    private String idTranca;

    public BiciclistaNaRedeJsonApi(String idBicicleta, String idTranca) {
        this.idBicicleta = idBicicleta;
        this.idTranca = idTranca;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getIdTranca() {
        return idTranca;
    }

    public void setIdTranca(String idTranca) {
        this.idTranca = idTranca;
    }
}
