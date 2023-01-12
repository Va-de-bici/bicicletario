/*package vadebici.service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import vadebici.dom.Email;
import vadebici.util.Segredos;

public class ServiceEmail {

    private final MailgunMessagesApi mailgunMessagesApi;

    public ServiceEmail() {
        mailgunMessagesApi = MailgunClient.config(Segredos.getEmailApiKey())
                .createApi(MailgunMessagesApi.class);
    }

    public void enviaMensagem(Email email) {
        String MESSAGE_FROM = "teste@vadebici.com";
        String MESSAGE_SUBJECT = "Va de bici";
        Message message = Message.builder()
                .from(MESSAGE_FROM)
                .to(email.getEmail())
                .subject(MESSAGE_SUBJECT)
                .text(email.getMensagem())
                .build();
        mailgunMessagesApi.sendMessage(Segredos.getEmailDomainName(), message);
    }
}
*/

