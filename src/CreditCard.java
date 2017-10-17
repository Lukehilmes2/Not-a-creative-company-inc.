class CreditCard extends Transaction{

  int last4Digits;
  public CreditCard(String description, int amount, int last4Digits) {
    super(description, amount);
    this.last4Digits = last4Digits;
  }

  public String toString(){
    return super.toString() + " " + last4Digits;
  }
}
