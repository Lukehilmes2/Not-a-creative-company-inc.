
public class Transaction {

	private double amount;
	private String description;
	
	public Transaction(double amount, String description) {
		
		this.amount = amount;
		this.description = description;
	}
	
	public double getAmount(){
		
		return amount;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	public String toString() {
		
		return amount + "," + description;
	}
}
