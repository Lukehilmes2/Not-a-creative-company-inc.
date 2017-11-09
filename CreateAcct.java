import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateAcct extends JPanel {


    private JTextField name;
	private JTextField fName, lName, email, phone,balance;
	private JLabel lblName, lblFirstName, lblLastName, lblEmail, lblPhone,lblBalance;
    private JLabel acct,badacct;
    private JButton create,back;
    private String file = "accounts.txt";
    private int txtFieldLength = 10;
    private MainPanel panel;
    public CreateAcct(MainPanel panel)
    {
    	this.panel = panel;
		setLayout(new BorderLayout());
		JPanel panel1 = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		acct = new JLabel();
    	acct.setText("Create a new Account!");
      cs.gridx = 1;
      cs.gridy = 0;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(acct, cs);

      lblName = new JLabel("Username: ");
      cs.gridx = 0;
      cs.gridy = 2;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblName, cs);
    	name = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 2;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(name, cs);

      lblFirstName = new JLabel("First name: ");
      cs.gridx = 0;
      cs.gridy = 4;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblFirstName, cs);
    	fName = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 4;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(fName, cs);

      lblLastName = new JLabel("Last name: ");
      cs.gridx = 0;
      cs.gridy = 6;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblLastName, cs);
    	lName = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 6;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(lName, cs);

      lblEmail = new JLabel("Email: ");
      cs.gridx = 0;
      cs.gridy = 8;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblEmail, cs);
    	email = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 8;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(email, cs);

      lblBalance = new JLabel("Starting Balance: ");
      cs.gridx = 0;
      cs.gridy = 9;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblBalance, cs);
      balance = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 9;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(balance, cs);

      lblPhone = new JLabel("Phone: ");
      cs.gridx = 0;
      cs.gridy = 10;
      cs.gridwidth = 1;
      cs.ipady = 20;
      panel1.add(lblPhone, cs);
    	phone = new JTextField(txtFieldLength);
      cs.gridx = 1;
      cs.gridy = 10;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(phone, cs);

    	create  = new JButton("Create Account");
      cs.gridx = 1;
      cs.gridy = 12;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(create,cs);

      badacct = new JLabel("");
      cs.gridx = 1;
      cs.gridy = 14;
      cs.gridwidth = 2;
      cs.ipady = 20;
      panel1.add(badacct,cs);

      back = new JButton("Back");

      add(panel1,BorderLayout.CENTER);
      add(back,BorderLayout.NORTH);
      back.addActionListener(new ButtonListener());
	create.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == create){
    		if (!name.getText().matches(".*\\w.*") || !fName.getText().matches(".*\\w.*") ||
    		!lName.getText().matches(".*\\w.*") || !email.getText().matches(".*\\w.*") ||
    		!phone.getText().matches(".*\\w.*")){
    			badacct.setText("Please fill out all the fields!");
    			return;
    		}
    		Account acct = new Account(name.getText(), fName.getText(), lName.getText(),
    				email.getText(), phone.getText(), Double.parseDouble(balance.getText()));
    		panel.addLine("accounts.txt", acct.toString());
    		if (acct.getBalance() != 0) {
        		panel.addLine("transactions/" + acct.getName() + ".txt", acct.getBalance() + ",starting balance");	
    		}
    		else {
        		panel.addLine("transactions/" + acct.getName() + ".txt", "");	
    		}
    		name.setText("");
    		fName.setText("");
    		lName.setText("");
    		email.setText("");
    		phone.setText("");
    		balance.setText("");
    		panel.updateTable();
    		panel.switchPanel("InitialView");

    	}
      else if(arg0.getSource() == back){
        name.setText("");
        fName.setText("");
        lName.setText("");
        email.setText("");
        phone.setText("");
        panel.switchPanel("InitialView");
      }
    }
    }
}
