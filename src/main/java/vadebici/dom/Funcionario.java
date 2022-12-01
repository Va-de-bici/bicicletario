package vadebici.dom;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;

import java.util.Objects;

public class Funcionario {
    private String id;
    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' deve ser válido")
    private String email;
    @NotBlank(message = "O campo 'senha' é obrigatório")
    private String senha;
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;
    @NotNull(message = "O campo 'idade' é obrigatório")
    private int idade;
    @NotBlank(message = "O campo 'funcao' é obrigatório")
    private String funcao;
    @NotBlank(message = "O campo 'cpf' é obrigatório")
    @Size(min = 8)
    private String cpf;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return idade == that.idade && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(senha, that.senha) && Objects.equals(nome, that.nome) && Objects.equals(funcao, that.funcao) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, nome, idade, funcao, cpf);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", funcao='" + funcao + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}