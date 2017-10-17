import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
class Login extends JPanel {

  private JTextField username;
  private JTextField password;
  private JButton login;
  private final String companyName = "NACC inc.";
  private final String productName = "Money Tracker 2000";

  public Login () {

    username = new JTextField("username");
    password = new JTextField("password");
    login = new JButton("login");
    add(username);
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
        for(String s : login){
          System.out.println(s);
        }
        if (login[0].equals(username.getText().trim()) && login[1].equals(password.getText().trim())) {
          System.out.println("Logged in");
        } else {
          System.out.println("Invalid username or password");
        }
      }
    }
  }
}
