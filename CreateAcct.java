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
	private JTextField fName, lName, email, phone;
	private JLabel lblName, lblFirstName, lblLastName, lblEmail, lblPhone;
    private JLabel acct;
    private JButton create;
    private String file = "accounts.txt";
    private int txtFieldLength = 10;
    private MainPanel panel;
    public CreateAcct(MainPanel panel) 
    {
    	this.panel = panel;
    	name = new JTextField(txtFieldLength);
    	fName = new JTextField(txtFieldLength);
    	lName = new JTextField(txtFieldLength);
    	email = new JTextField(txtFieldLength);
    	phone = new JTextField(txtFieldLength);
    	create  = new JButton("Create Account");
    	acct = new JLabel();
    	acct.setText("Create a new Account!");
    	lblName = new JLabel("Username: ");
    	lblFirstName = new JLabel("First name: ");
    	lblLastName = new JLabel("Last name: ");
    	lblEmail = new JLabel("Email: ");
    	lblPhone = new JLabel("Phone: ");
    	add(acct);
    	add(lblName);
    	add(name);
    	add(lblFirstName);
    	add(fName);
    	add(lblLastName);
    	add(lName);
    	add(lblEmail);
    	add(email);
    	add(lblPhone);
    	add(phone);
    	add(create);
    	create.addActionListener(new ButtonListener());
    }
    
    private class ButtonListener implements ActionListener{ 
    	public void actionPerformed(ActionEvent arg0) {
    		if (!name.getText().matches(".*\\w.*") || !fName.getText().matches(".*\\w.*") || 
    		!lName.getText().matches(".*\\w.*") || !email.getText().matches(".*\\w.*") ||
    		!email.getText().matches(".*\\w.*")){
    			return;
    		}
    		Account acct = new Account(name.getText(), fName.getText(), lName.getText(),
    				email.getText(), phone.getText());
    		BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.append(acct.toString() + "\n");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			name.setText("");
			fName.setText("");
			lName.setText("");
			email.setText("");
			phone.setText("");
			panel.updateTable();
			panel.switchPanel("InitialView");
    	}    	
    }
}

	


