package vadebici.servico_aluguel.dom.dto;

public class TrancaNaRedeJsonApi {
    private String idTranca;
    private String idTotem;

    public TrancaNaRedeJsonApi(String idTranca, String idTotem) {
        this.idTranca = idTranca;
        this.idTotem = idTotem;
    }

    public String getIdTranca() {
        return idTranca;
    }

    public void setIdTranca(String idTranca) {
        this.idTranca = idTranca;
    }

    public String getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(String idTotem) {
        this.idTotem = idTotem;
    }
}
