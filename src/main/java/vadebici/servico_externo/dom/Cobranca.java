package vadebici.servico_externo.dom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Cobranca {
    private String id;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss", timezone = "America/Sao_Paulo")
    private Date horaSolicitacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss", timezone = "America/Sao_Paulo")
    private Date horaFinalizacao;
    private Double valor;
    private String ciclista;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getHoraSolicitacao() {
        return horaSolicitacao;
    }

    public void setHoraSolicitacao(Date horaSolicitacao) {
        this.horaSolicitacao = horaSolicitacao;
    }

    public Date getHoraFinalizacao() {
        return horaFinalizacao;
    }

    public void setHoraFinalizacao(Date horaFinalizacao) {
        this.horaFinalizacao = horaFinalizacao;
    }

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
