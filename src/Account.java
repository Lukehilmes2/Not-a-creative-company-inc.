import java.util.*;
class Account {

  String userName;
  String firstName;
  String lastName;
  String password;
  int balance;
  ArrayList<Transaction> transactions;
  public Account(String userName, int balance, String firstName, String lastName, String password) {
    this.userName = userName;
    this.balance = balance;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    transactions = new ArrayList<Transaction>();
  }

  public void addTransaction(Transaction transaction){
    transactions.add(transaction);
    balance += transaction.getAmount();
  }

  public String toString() {
    return userName + "," + balance + "," + firstName + "," + lastName + "," + password;
  }
}
