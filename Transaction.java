import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private double amount;
	private String description;
	private String date;
	private int code;
	private double fines = 0;
	private String account;

	public Transaction(String account, double amount, String description,
			int code) {

		this.account = account;
		date = new SimpleDateFormat("MM-dd-yyyy").format(new Date()).toString();
		this.amount = amount;
		this.description = description;
		this.code = code;
	}

	public Transaction(String account, String date, double amount, int code,
			String description) {

		this.account = account;
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.code = code;
	}

	public void setFines(double fines) {
		this.fines = fines;
	}

	public double getFines() {
		return fines;
	}

	public double getAmount() {

		return amount;
	}

	public String getDescription() {

		return description;
	}

	public String toString() {

		return account + "," + date + "," + amount + "," + code + ","
				+ description;
	}
}
