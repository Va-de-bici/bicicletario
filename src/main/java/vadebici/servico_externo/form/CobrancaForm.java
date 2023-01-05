package vadebici.servico_externo.form;

import vadebici.servico_externo.dom.CobrancaD;

import java.util.UUID;

public class CobrancaForm {
	
	private String id;
	private String ciclistaId;
	private int valor;
	private String horaSolicitacao;
	private String horaFinalizacao;	
	private String status;

	public CobrancaForm() {
		
	}
	
	public CobrancaForm(String id, String ciclistaId, String horaSolicitacao, String horaFinalizacao, int valor, String status){
		this.id = id;
		this.ciclistaId = ciclistaId;
		this.horaSolicitacao = horaSolicitacao;
		this.horaFinalizacao = horaFinalizacao;
		this.valor = valor;
		this.status = status;
	}
	
	public CobrancaForm(String ciclistaId,  int valor){
		this.id =  UUID.randomUUID().toString();
		this.ciclistaId = ciclistaId;
		this.valor = valor;
	}
	
	public CobrancaD unserialize(){
		return new CobrancaD(this.id, this.ciclistaId, this.horaSolicitacao, this.horaFinalizacao, this.valor, this.status);
	}
	
	public CobrancaD deserializePostCobranca(){
		return new CobrancaD(this.ciclistaId, this.valor);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCiclistaId() {
		return ciclistaId;
	}

	public void setCiclistaId(String ciclistaId) {
		this.ciclistaId = ciclistaId;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
