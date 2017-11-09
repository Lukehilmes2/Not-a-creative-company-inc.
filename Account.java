import java.util.ArrayList;
class Account {

  private String name;
  private double balance = 0;
  private String fName;
  private String lName;
  private String email;
  private String phone;
  
  public Account(String name, String fName, String lName, String email, String phone, double balance) {
    this.name = name;
    this.balance = 0;
    this.fName = fName;
    this.lName = lName;
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
  
  public String getfName() {
	  return fName;
  }
  
  public String getlName() {
	  return lName;
  }
  public double getBalance() {
	  return balance;
  }
  public void setBalance(double balance) {
	  this.balance = balance;
  }
  
  public String toString() {
    return name + "," + fName + "," + lName + "," + email + "," + phone + "," + balance;
  }
}
