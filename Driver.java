import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
class Driver{

  public static void main(String[] args){

    JFrame frame = new JFrame("Money Tracker 2000");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(700, 500));
    MainPanel panel = new MainPanel();
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
