import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateAcct extends JPanel {


	    JTextField Name;
		JTextField StartingBal;
		JTextField FName, LName, Password;
	    JLabel Acct;
	    JButton Create;
	    String File = "login.txt";



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
	    	add(FName);
	    	add(LName);
	    	add(Password);
	    	add(Create);
	    	Create.addActionListener(new ButtonListener());

	    	Name.addFocusListener(new FocusListener(){
	    		@Override
	    		public void focusGained(FocusEvent e){
	    			if(e.getSource().equals(Name)){
	    			Name.setText("Name");
	    			}

	    		}

				@Override
				public void focusLost(FocusEvent arg0) {


				}
	    		});
	    	FName.addFocusListener(new FocusListener(){
	    		@Override
	    		public void focusGained(FocusEvent e){
	    			if(e.getSource().equals(FName)){
	    			FName.setText("");
	    			}

	    		}

				@Override
				public void focusLost(FocusEvent arg0) {


				}
	    		});
	    	LName.addFocusListener(new FocusListener(){
	    		@Override
	    		public void focusGained(FocusEvent e){
	    			if(e.getSource().equals(LName)){
	    			LName.setText("");
	    			}

	    		}

				@Override
				public void focusLost(FocusEvent arg0) {


				}
	    		});
	    	Password.addFocusListener(new FocusListener(){
	    		@Override
	    		public void focusGained(FocusEvent e){
	    			if(e.getSource().equals(Password)){
	    			Password.setText("");
	    			}

	    		}

				@Override
				public void focusLost(FocusEvent arg0) {


				}
	    		});
	    	}

	    private class ButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Account newAcct = new Account(Name.getText(), 0, FName.getText(), LName.getText(), Password.getText());
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(File));
					writer.write(newAcct.toString());
					System.out.println("bleh");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    }
  }
