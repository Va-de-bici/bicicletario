package vadebici.servico_externo.dom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CobrancaRequisicao {
    @NotNull(message = "O campo 'valor' é obrigatório")
    @Positive(message = "O campo 'valor' deve ser positivo")
    private Double valor;
    @NotBlank(message = "O campo 'ciclista' é obrigatório")
    private String ciclista;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCiclista() {
        return ciclista;
    }

    public void setCiclista(String ciclista) {
        this.ciclista = ciclista;
    }
}
