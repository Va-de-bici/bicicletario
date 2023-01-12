package vadebici.servico_aluguel.dom;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties({"id"})
public class Aluguel {
    private String id;
    private String bicicleta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date horaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")

    private Date horaFim;
    private String trancaInicio;
    private String trancaFim;
    private String[] cobranca;
    private String ciclista;

    @Override
    public String toString() {
        String stringCobrancas;

        if (this.cobranca != null) {
            StringBuilder stringCobrancasBuilder = new StringBuilder("[" + "\n");
            for (String c : this.cobranca) {
                if (c != null) {
                    stringCobrancasBuilder.append(c);

                    if (c.equals(this.cobranca[0]))
                        stringCobrancasBuilder.append(",");

                    stringCobrancasBuilder.append("\n");
                }
            }
            stringCobrancas = stringCobrancasBuilder.toString();

            stringCobrancas += "]" + "\n";
        } else
            stringCobrancas = null;

        return "Aluguel {" + "\n" +
                "bicicleta='" + bicicleta + "'\n" +
                "horaInicio=" + horaInicio + "\n" +
                "horaFim=" + horaFim + "\n" +
                "trancaInicio='" + trancaInicio + "'\n" +
                "trancaFim='" + trancaFim + "'\n" +
                "cobranca=" + stringCobrancas + "\n" +
                "ciclista='" + ciclista + "'\n" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluguel that = (Aluguel) o;
        return Objects.equals(bicicleta, that.bicicleta) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(trancaInicio, that.trancaInicio) && Objects.equals(trancaFim, that.trancaFim) && Arrays.equals(cobranca, that.cobranca) && Objects.equals(ciclista, that.ciclista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bicicleta, horaInicio, horaFim, trancaInicio, trancaFim, Arrays.hashCode(cobranca), ciclista);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(String bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public String getTrancaInicio() {
        return trancaInicio;
    }

    public void setTrancaInicio(String trancaInicio) {
        this.trancaInicio = trancaInicio;
    }

    public String getTrancaFim() {
        return trancaFim;
    }

    public void setTrancaFim(String trancaFim) {
        this.trancaFim = trancaFim;
    }

    public String[] getCobranca() {
        return cobranca;
    }

    public void setCobranca(String[] cobranca) {
        this.cobranca = cobranca;
    }

    public String getCiclista() {
        return ciclista;
    }

    public void setCiclista(String ciclista) {
        this.ciclista = ciclista;
    }
}
