package vadebici.servico_aluguel.dom.dto;

import jakarta.validation.constraints.NotBlank;

public class AluguelJsonApi {
    @NotBlank(message = "O campo 'ciclista' é obrigatório")
    private String ciclista;
    @NotBlank(message = "O campo 'trancaInicio' é obrigatório")
    private String trancaInicio;

    public String getCiclista() {
        return ciclista;
    }

    public void setCiclista(String ciclista) {
        this.ciclista = ciclista;
    }

    public String getTrancaInicio() {
        return trancaInicio;
    }

    public void setTrancaInicio(String trancaInicio) {
        this.trancaInicio = trancaInicio;
    }
}
