package vadebici.servico_externo.dom;

import java.util.UUID;

public class CobrancaD {
	
	private String id;
	private String ciclistaId;
	private String horaSolicitacao;
	private String horaFinalizacao;
	private int valor;
    private String status;

	public CobrancaD() {
		
	}

	
	public CobrancaD(String ciclistaId, String horaSolicitacao, String horaFinalizacao, int valor, String status){
		this.id = UUID.randomUUID().toString();
		this.ciclistaId = ciclistaId;
		this.horaSolicitacao = horaSolicitacao;
		this.horaFinalizacao = horaFinalizacao;
		this.valor = valor;
		this.status = status;
	}
	
	public CobrancaD(String id, String ciclistaId, String horaSolicitacao, String horaFinalizacao, int valor, String status){
		this.id = id;
		this.ciclistaId = ciclistaId;
		this.horaSolicitacao = horaSolicitacao;
		this.horaFinalizacao = horaFinalizacao;
		this.valor = valor;
		this.status = status;
	}
	
	public CobrancaD(String ciclistaId, int valor){
		this.id = UUID.randomUUID().toString();
		this.valor = valor;
		this.ciclistaId = ciclistaId;
	}

	public String getId() {
		return id;
	}
	
	public String getCiclistaId() {
		return ciclistaId;
	}

	public void setCiclistaId(String ciclistaId) {
		this.ciclistaId = ciclistaId;
	}

	public String getHoraSolicitacao() {
		return horaSolicitacao;
	}

	public void setHoraSolicitacao(String horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}

	public String getHoraFinalizacao() {
		return horaFinalizacao;
	}

	public void setHoraFinalizacao(String horaFinalizacao) {
		this.horaFinalizacao = horaFinalizacao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
