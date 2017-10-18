import java.util.*;
class Account {

  private String name;
  private int balance;
  private String fName;
  private String lName;
  private String password;
  ArrayList<Transaction> transactions;
  public Account(String name, String password, String fName, String lName) {
    this.name = name;
    this.balance = 0;
    this.fName = fName;
    this.lName = lName;
    this.password = password;
    this.balance = 0;
    transactions = new ArrayList<Transaction>();
  }

  public void addTransaction(Transaction transaction){
    transactions.add(transaction);
    balance += transaction.getAmount();
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
  
  public String toString() {
    return name + "," + password + "," + fName + "," + lName;
  }
}
