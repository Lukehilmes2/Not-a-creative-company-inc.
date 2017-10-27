import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.*;
class Login extends JPanel {

  private JTextField username;
  private JPasswordField password;
  private JLabel lblUsername;
  private JLabel lblPassword;
  private JLabel label;
  private JButton login;
  private int txtFieldLength = 20;
  private MainPanel panel;
  private JLabel lblBadLogin;


  public Login (MainPanel panel) {

	  this.panel = panel;
    setLayout(new BorderLayout());
    JPanel panel1 = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();

    cs.fill = GridBagConstraints.HORIZONTAL;

    lblUsername = new JLabel("Username: ");
    cs.gridx = 0;
    cs.gridy = 0;
    cs.gridwidth = 1;
    panel1.add(lblUsername, cs);

    username = new JTextField(20);
    cs.gridx = 1;
    cs.gridy = 0;
    cs.gridwidth = 2;
    panel1.add(username, cs);

    lblPassword = new JLabel("Password: ");
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    panel1.add(lblPassword, cs);

    password = new JPasswordField(20);
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 2;
    panel1.add(password, cs);
    panel1.setBorder(new LineBorder(Color.GRAY));

    login = new JButton("Login");
    lblBadLogin = new JLabel("");
    login.addActionListener(new ButtonListener());
    JPanel bp = new JPanel();
    bp.add(login);
    bp.add(lblBadLogin);
    add(panel1, BorderLayout.CENTER);
    add(bp, BorderLayout.PAGE_END);


  }

  private class ButtonListener implements ActionListener{

    public void actionPerformed(ActionEvent evt){

	if (username.getText().equalsIgnoreCase("CSAdmin") && password.getText().equalsIgnoreCase("CSCI323")) {
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
      lblBadLogin.setText("<html>Bad Details!<br>Try Again</html>");
	}
  }
}
