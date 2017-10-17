class Check extends Transaction{


  int checkNum;
  public Check(String description, int amount, int checkNum) {
    super(description, amount);
    this.checkNum = checkNum;
  }

  public String toString(){
    return super.toString() + " " + checkNum;
  }
}
