import javax.swing.*;
import java.awt.Dimension;

class Driver{

  public static void main(String[] args){

    JFrame frame = new JFrame("not a creative company inc.");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(700, 500));
    frame.add(new Login());
    frame.pack();
    frame.setVisible(true);
  }
}
