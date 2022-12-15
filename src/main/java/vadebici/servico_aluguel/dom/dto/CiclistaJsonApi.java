package vadebici.servico_aluguel.dom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import vadebici.servico_aluguel.dom.Passaporte;

import java.util.Date;

public class CiclistaJsonApi {
    private String id;

    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @NotNull(message = "O campo 'nascimento' é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date nascimento;
    @NotBlank(message = "O campo 'nacionalidade' é obrigatório")
    private String nacionalidade;

    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' deve ser válido")
    private String email;


    private String cpf;

    private Boolean status;

    private Passaporte passaporte;




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }


    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }


}
