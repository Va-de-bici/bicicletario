package vadebici.servico_aluguel.dom;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

public class Ciclista {
    private String id;
    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' deve ser válido")
    private String email;
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;
    @NotNull(message = "O campo 'nascimento' é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date nascimento;
    @NotBlank(message = "O campo 'nacionalidade' é obrigatório")
    private String nacionalidade;
    private String cpf;

    private Passaporte passaporte;
    @NotBlank(message = "O campo 'senha' é obrigatório")
    private String senha;
    @NotNull(message = "O campo 'meioDePagamento' é obrigatório")
    private MeioDePagamento meioDePagamento;
    private Boolean status;

    @NotBlank(message = "O campo 'urlFotoDocumento' é obrigatório")
    private String urlFotoDocumento;

    @Override
    public String toString() {
        return "Ciclista{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", cpf='" + cpf + '\'' +
                ", passaporte=" + passaporte +
                ", meioDePagamento=" + meioDePagamento +
                ", status=" + status +
                ", urlFotoDocumento=" + urlFotoDocumento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciclista ciclista = (Ciclista) o;
        return Objects.equals(id, ciclista.id) && Objects.equals(email, ciclista.email) && Objects.equals(nome, ciclista.nome) && Objects.equals(nascimento, ciclista.nascimento) && Objects.equals(nacionalidade, ciclista.nacionalidade) && Objects.equals(cpf, ciclista.cpf) && Objects.equals(passaporte, ciclista.passaporte) && Objects.equals(senha, ciclista.senha) && Objects.equals(meioDePagamento, ciclista.meioDePagamento) && Objects.equals(status, ciclista.status) && Objects.equals(urlFotoDocumento, ciclista.urlFotoDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nome, nascimento, nacionalidade, cpf, passaporte, senha, meioDePagamento, status, urlFotoDocumento);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
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

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public MeioDePagamento getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(MeioDePagamento meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUrlFotoDocumento() {
        return urlFotoDocumento;
    }

    public void setUrlFotoDocumento(String urlFotoDocumento) {
        this.urlFotoDocumento = urlFotoDocumento;
    }
}
