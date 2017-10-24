import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
class Login extends JPanel {

  private JTextField username;
  private JTextField password;
  private JLabel lblUsername;
  private JLabel lblPassword;
  private JButton login;
  private int txtFieldLength = 10;
  private MainPanel panel;
  private JLabel lblBadLogin;
  public Login (MainPanel panel) {

	this.panel = panel;
    username = new JTextField(txtFieldLength);
    password = new JTextField(txtFieldLength);
    lblUsername = new JLabel("Username: ");
    lblPassword = new JLabel("Password: ");
    lblBadLogin = new JLabel("");
    login = new JButton("login");
    add(lblUsername);
    add(username);
    add(lblPassword);
    add(password);
    add(login);
    add(lblBadLogin);
    login.addActionListener(new ButtonListener());

  }

  private class ButtonListener implements ActionListener{

    public void actionPerformed(ActionEvent evt){
	
	if (username.getText().equals("CSAdmin") && password.getText().equals("CSCI323")) {
		panel.switchPanel("InitialView");
    	lblBadLogin.setText("");
    	username.setText("");
    	password.setText("");
		return;
	}
     /* Scanner file = null;
      try {
        file = new Scanner(new FileReader("login.txt"));
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      while (file.hasNext()) {
        String nextLine = file.nextLine();
        String[] login = nextLine.split(",");
        if (login[0].equals(username.getText().trim()) && login[1].equals(password.getText().trim())) {
        	Account acct = new Account(login[0], login[1], login[2], login[3]);
        	username.setText("");
        	password.setText("");
        	panel.switchPanel("InitialView");
        	panel.setAcct(acct);
        	lblBadLogin.setText("");
        	return;
        }
      }*/
      lblBadLogin.setText("bad password");
	}
  }
}
