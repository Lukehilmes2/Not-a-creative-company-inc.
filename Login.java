import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
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

	if (username.getText().equalsIgnoreCase("CSAdmin") && String.copyValueOf(password.getPassword()).equalsIgnoreCase("CSCI323")) {
		panel.switchPanel("InitialView");
    	lblBadLogin.setText("");
    	username.setText("");
    	password.setText("");
		return;
	}

      lblBadLogin.setText("<html>Bad Details!<br>Try Again</html>");
	}
  }
}
