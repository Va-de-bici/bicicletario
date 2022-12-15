package vadebici.servico_externo.pagamento;

public class Pagamento {

	private String Type;
	private int Amount;
	private int Installments;

	private CreditCard CreditCard;

	public Pagamento() {
		this.CreditCard = new CreditCard();
	}

	public class CreditCard {

		private String CardNumber;
		private String ExpirationDate;
		private String SecurityCode;
		private String Brand;

		public String getCardNumber() {
			return CardNumber;
		}

		public void setCardNumber(String cardNumber) {
			CardNumber = cardNumber;
		}

		public String getExpirationDate() {
			return ExpirationDate;
		}

		public void setExpirationDate(String expirationDate) {
			ExpirationDate = expirationDate;
		}

		public String getSecurityCode() {
			return SecurityCode;
		}

		public void setSecurityCode(String securityCode) {
			SecurityCode = securityCode;
		}

		public String getBrand() {
			return Brand;
		}

		public void setBrand(String brand) {
			Brand = brand;
		}
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public CreditCard getCreditCard() {
		return CreditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		CreditCard = creditCard;
	}

	public int getInstallments() {
		return Installments;
	}

	public void setInstallments(int installments) {
		Installments = installments;
	}
}

