package vadebici.servico_externo.form;

import vadebici.servico_externo.dom.CartaoCredito;

public class CartaoCreditoForm {
	
	private String nomeTitular;
	private String numero;
	private String validade;
	public String cvv;

	public CartaoCreditoForm() 
	{
		
	}

	
	public CartaoCreditoForm(String nomeTitular, String numero, String validade, String cvv){
		this.nomeTitular = nomeTitular;
		this.numero = numero;
		this.validade = validade;
		this.cvv = cvv;
	}

	public CartaoCredito deserialize(){
		return new CartaoCredito(this.nomeTitular, this.numero, this.validade, this.cvv);
	}
}

