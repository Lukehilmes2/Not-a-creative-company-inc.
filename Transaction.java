import java.util.Date;

public class Transaction {

	private double amount;
	private String description;
	private Date date;
	private int code;
	private double fines = 0;
	
	public Transaction(double amount, String description, int code) {
		
		date = new Date();
		this.amount = amount;
		this.description = description;
		this.code = code;
	}
	
	public Transaction(Date date, double amount, int code, String description) {
		
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.code = code;
	}
	
	public void setFines (double fines) {
		this.fines = fines;
	}
	
	public double getFines() {
		return fines;
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
