package vadebici.servico_externo.controller;

import io.javalin.http.Context;
import vadebici.servico_externo.dom.Email;
import vadebici.servico_externo.form.EmailForm;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailController {

	private EmailController() {

	}

	public static void enviaEmail(Context ctx) throws MessagingException, IOException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.ssl.checkserveridentity", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("brunopmunirio@gmail.com", "pmexterno");
			}
		});

		Message mensagem = prepareMessage(session, ctx);

		Transport.send(mensagem);
		ctx.status(200);
	}

	private static Message prepareMessage(Session session, Context ctx) throws IOException {

		Message message = new MimeMessage(session);
		Email emailForm = ctx.bodyAsClass(EmailForm.class).deserialize();

		try {
			message.setFrom(new InternetAddress("vadebici@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailForm.getEmail()));
			message.setSubject("Va de Bici - Teste Externo");
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(emailForm.getMensagem(), "text/html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			return message;

		} catch (AddressException e) {
			ctx.status(404);
		} catch (MessagingException e) {
			ctx.status(422);
		}
		return null;
	}
}

