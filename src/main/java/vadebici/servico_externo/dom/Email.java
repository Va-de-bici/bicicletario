package vadebici.servico_externo.dom;

public class Email {
    private String emailD;
    private String mensagem;

    public Email(String emailD, String mensagem){
        this.emailD = emailD;
        this.mensagem = mensagem;
    }

    public Email(){}

    public String getEmail() {
        return emailD;
    }

    public void setEmail(String emailD) {
        this.emailD = emailD;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}


