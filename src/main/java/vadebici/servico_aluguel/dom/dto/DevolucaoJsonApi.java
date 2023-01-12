package vadebici.servico_aluguel.dom.dto;

import jakarta.validation.constraints.NotBlank;

public class DevolucaoJsonApi {
    @NotBlank(message = "O campo 'idTranca' é obrigatório")
    private String idTranca;
    @NotBlank(message = "O campo 'idBicicleta' é obrigatório")
    private String idBicicleta;

    public String getIdTranca() {
        return idTranca;
    }

    public void setIdTranca(String idTranca) {
        this.idTranca = idTranca;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }
}
