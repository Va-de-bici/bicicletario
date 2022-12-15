package vadebici.servico_externo.pagamento;

public class TransacaoCielo {

	private String MerchantOrderId;
	private Customer Customer;
	private Pagamento Payment;

	public class Customer {
		public String Name;

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}
	}

	public TransacaoCielo() {

	}

	public TransacaoCielo(String MerchantOrderId) {
		this.Customer = new Customer();
		this.MerchantOrderId = MerchantOrderId;
		this.Payment = new Pagamento();
	}

	public String getMerchantOrderId() {
		return MerchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		MerchantOrderId = merchantOrderId;
	}

	public Customer getCustomer() {
		return Customer;
	}

	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	public Pagamento getPayment() {
		return Payment;
	}

	public void setPayment(Pagamento payment) {
		Payment = payment;
	}
}

