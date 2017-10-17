import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.*;

public class CreateAcct extends JPanel {

	
    JTextField Name;
	JTextField FName, LName, Password;
	JLabel lblName, lblFirstName, lblLastName, lblPassword;
    JLabel Acct;
    JButton Create;
    String File = "login.txt";

    public CreateAcct() 
    {
    	Name = new JTextField("Name");
    	FName = new JTextField("First Name");
    	LName = new JTextField("Last Name");
    	Password = new JTextField("Password");
    	Create  = new JButton("Create Account");
    	Acct = new JLabel();
    	Acct.setText("Create a new Account!");
    	lblName = new JLabel("Username: ");
    	lblFirstName = new JLabel("First name: ");
    	lblLastName = new JLabel("Last name: ");
    	lblPassword = new JLabel("Password: ");
    	
    	add(Acct);
    	add(lblName);
    	add(Name);
    	add(lblFirstName);
    	add(FName);
    	add(lblLastName);
    	add(LName);
    	add(lblPassword);
    	add(Password);
    	add(Create);
    	Create.addActionListener(new ButtonListener());
    }
    private class ButtonListener implements ActionListener{

    	public void actionPerformed(ActionEvent arg0) {
			String newAcct = (Name.getText()+","+Password.getText());
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(File, true));
				PrintWriter out = new PrintWriter(writer);
				out.println(newAcct.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}    	
    }
}

	


