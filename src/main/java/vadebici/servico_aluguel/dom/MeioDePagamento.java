package vadebici.servico_aluguel.dom;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Objects;

public class MeioDePagamento {
    @NotBlank(message = "O campo 'senha' é obrigatório")
    private String nomeTitular;
    @NotBlank(message = "O campo 'numero' é obrigatório")
    @Size(min = 13, max = 16, message = "O campo 'numero' deve ter entre 13 e 16 dígitos")
    private String numero;
    @NotNull(message = "O campo 'senha' é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "America/Sao_Paulo")
    private Date validade;
    @NotBlank(message = "O campo 'cvv' é obrigatório")
    @Size(min = 3, max = 3, message = "O campo 'cvv' deve conter somente 3 digitos")
    private String cvv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeioDePagamento that = (MeioDePagamento) o;
        return Objects.equals(nomeTitular, that.nomeTitular) && Objects.equals(numero, that.numero) && Objects.equals(validade, that.validade) && Objects.equals(cvv, that.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeTitular, numero, validade, cvv);
    }

    @Override
    public String toString() {
        return "MeioDePagamento{" +
                "nomeTitular='" + nomeTitular + '\'' +
                ", numero='" + numero + '\'' +
                ", validade=" + validade +
                ", cvv='" + cvv + '\'' +
                '}';
    }

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

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
