import javax.swing.*;

public class CreateAcct extends JPanel {

	
	    JTextField Name;
		JTextField StartingBal;
		JTextField FName, LName, Password;
	    JLabel Acct;
	    JButton Create;
	    


	    public CreateAcct() 
	    {
	    	Name = new JTextField("Name");
	    	StartingBal = new JTextField("Starting Balance");
	    	FName = new JTextField("First Name");
	    	LName = new JTextField("Last Name");
	    	Password = new JTextField("Password");
	    	Create  = new JButton("Create Account");
	    	
	    	Acct = new JLabel();
	    	Acct.setText("Create a new Account!");
	    	
	    	add(Acct);
	    	add(Name);
	    	add(StartingBal);
	    	add(FName);
	    	add(LName);
	    	add(Password);
	    	add(Create);





	    	}

	}


