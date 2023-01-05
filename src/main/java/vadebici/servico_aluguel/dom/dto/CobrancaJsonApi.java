package vadebici.servico_aluguel.dom.dto;

public class CobrancaJsonApi {
    private String ciclista;
    private Double valor;

    public String getCiclista() {
        return ciclista;
    }

    public void setCiclista(String ciclista) {
        this.ciclista = ciclista;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
