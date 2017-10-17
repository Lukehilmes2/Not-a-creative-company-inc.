import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateAcct extends JPanel {

	
    private JTextField name;
	private JTextField fName, lName, password;
	private JLabel lblName, lblFirstName, lblLastName, lblPassword;
    private JLabel acct;
    private JButton create;
    private String file = "login.txt";
    private int txtFieldLength = 10;
    private MainPanel panel;
    public CreateAcct(MainPanel panel) 
    {
    	this.panel = panel;
    	name = new JTextField(txtFieldLength);
    	fName = new JTextField(txtFieldLength);
    	lName = new JTextField(txtFieldLength);
    	password = new JTextField(txtFieldLength);
    	create  = new JButton("Create Account");
    	acct = new JLabel();
    	acct.setText("Create a new Account!");
    	lblName = new JLabel("Username: ");
    	lblFirstName = new JLabel("First name: ");
    	lblLastName = new JLabel("Last name: ");
    	lblPassword = new JLabel("Password: ");
    	
    	add(acct);
    	add(lblName);
    	add(name);
    	add(lblFirstName);
    	add(fName);
    	add(lblLastName);
    	add(lName);
    	add(lblPassword);
    	add(password);
    	add(create);
    	create.addActionListener(new ButtonListener());
    }
    private class ButtonListener implements ActionListener{ 
    	public void actionPerformed(ActionEvent arg0) {
    		if (!name.getText().matches(".*\\w.*") || !fName.getText().matches(".*\\w.*") || 
    		!lName.getText().matches(".*\\w.*") || !password.getText().matches(".*\\w.*")) {
    			return;
    		}
    		Account acct = new Account(name.getText(), fName.getText(), lName.getText(), password.getText());
    		BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.append(acct.toString() + "\n");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}    	
    }
}

	


