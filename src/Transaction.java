class Transaction {

  String description;
  int amount;
  public Transaction(String description, int amount) {
    this.description = description;
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  public String toString() {
    return ("amount: " + amount + " description: " + description);
  }
}
