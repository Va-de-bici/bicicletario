package vadebici.servico_externo.form;

import vadebici.servico_externo.dom.Email;

public class EmailForm {

	public String message;
	public String email;
	
	public EmailForm() {

	}

	public EmailForm(String message, String email) {
		this.message = message;
		this.email = email;
	}

	public Email deserialize() {
		return new Email(this.message, this.email);
	}
}
