package vadebici.servico_equipamento.dom.dto;

import jakarta.validation.constraints.NotEmpty;

public class EmailJsonApi {

    @NotEmpty(message = "O campo 'mensagem' é obrigatório")
    private String mensagem;

    @NotEmpty(message = "O campo 'email' é obrigatório")
    @jakarta.validation.constraints.Email(message = "O campo 'email' deve ser válido")
    private String email;

    public EmailJsonApi(String email, String mensagem) {
        this.email = email;
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
