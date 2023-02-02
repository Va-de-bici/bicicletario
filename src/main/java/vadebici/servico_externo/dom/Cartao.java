package vadebici.servico_externo.dom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Cartao {
    private String nomeTitular;
    @NotBlank(message = "O campo 'numero' é obrigatório")
    @Size(min = 13, max = 16, message = "O campo 'numero' deve ter entre 13 e 16 dígitos")
    private String numero;
    @NotBlank(message = "O campo 'validade' é obrigatório")
    private String validade;
    @NotBlank(message = "O campo 'cvv' é obrigatório")
    @Size(min = 3, max = 3, message = "O campo 'cvv' deve conter somente 3 digitos")
    private String cvv;

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
}
