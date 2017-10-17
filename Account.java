import java.util.*;
class Account {

  String name;
  int balance;
  ArrayList<Transaction> transactions;
  public Account(String name, int balance) {
    this.name = name;
    this.balance = balance;
    transactions = new ArrayList<Transaction>();
  }

  public void addTransaction(Transaction transaction){
    transactions.add(transaction);
    balance += transaction.getAmount();
  }

  public String toString() {
    return "name: " + name + " balance: " + balance;
  }
}
