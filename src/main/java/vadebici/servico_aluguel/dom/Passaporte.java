package vadebici.servico_aluguel.dom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Passaporte {
    @NotBlank(message = "O campo 'numero' de passaporte é obrigatório")
    private String numero;
    @NotNull(message = "O campo 'validade' de passaporte é obrigatório")
    private Date validade;
    @NotBlank(message = "O campo 'pais' de passaporte é obrigatório")
    private String pais;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
