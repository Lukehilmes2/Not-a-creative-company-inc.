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
  private final String companyName = "NACC inc.";
  private final String productName = "Money Tracker 2000";
  private int txtFieldLength = 10;
  private MainPanel panel;
  
  public Login (MainPanel panel) {

	this.panel = panel;
    username = new JTextField(txtFieldLength);
    password = new JTextField(txtFieldLength);
    lblUsername = new JLabel("Username: ");
    lblPassword = new JLabel("Password: ");
    login = new JButton("login");
    add(lblUsername);
    add(username);
    add(lblPassword);
    add(password);
    add(login);
    login.addActionListener(new ButtonListener());

  }

  public void paint(Graphics g) {

    super.paint(g);
    g.drawString(companyName, 10, 450);
    g.drawString(productName, 10, 20);
  }

  private class ButtonListener implements ActionListener{

    public void actionPerformed(ActionEvent evt){

      Scanner file = null;
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
        	System.out.println("Logged in");
        	panel.switchPanel("CreateAcct");
        	return;
        }
      }
      System.out.println("bad password");
	}
  }
}
