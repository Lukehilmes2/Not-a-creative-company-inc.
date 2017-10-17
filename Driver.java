import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
