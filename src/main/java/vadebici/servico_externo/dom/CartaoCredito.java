package vadebici.servico_externo.dom;

public class CartaoCredito {
    private String nomeTitular;
    private String numero;
    private String validade;
    private String cvv;

    public CartaoCredito(String nomeTitular, String numero, String validade, String cvv)    {
		this.nomeTitular = nomeTitular;
		this.numero = numero;
		this.validade = validade;
		this.cvv = cvv;
	}

    public CartaoCredito(){}
        
    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBand() {
		if (this.getNumero().substring(0, 1).equals("4")) {
			return "Visa";
		} else if (this.getNumero().substring(0, 1).equals("5")) {
			return "MasterCard";
		} else if (this.getNumero().substring(0, 2).equals("37")) {
			return "AmericanExpress";
		} else {
			return "Sem bandeira";
		}
    }

}


