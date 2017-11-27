import java.util.ArrayList;
class Account {

  private String name;
  private double balance = 0;
  private String description;
  private String email;
  private String phone;
  
  public Account(String name, String description, String email, String phone, double balance) {
    this.name = name;
    this.balance = 0;
    this.description = description;
    this.email = email;
    this.phone = phone;
    this.balance = balance;
  }
  public String getEmail() {	  
	  return email;
  }
  
  public String getPhone() {	  
	  return phone;
  }
  public String getName() {
	  return name;
  }
  
  public String getDescription() {
	  return description;
  }
  public double getBalance() {
	  return balance;
  }
  public void setBalance(double balance) {
	  this.balance = balance;
  }
  
  public String toString() {
    return name + "," + description + "," + email + "," + phone + "," + balance;
  }
}
