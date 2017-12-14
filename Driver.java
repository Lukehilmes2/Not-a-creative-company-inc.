import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

class Driver {

	
	public static void main(String[] args) {
	
		JFrame frame = new JFrame("Money Tracker 2000 - Made by NACC inc.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(700, 500));
		MainPanel panel = new MainPanel();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
