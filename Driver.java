import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

class Driver extends JFrame {

	
	private static Glass pnlGlass;
	private static MainPanel panel;
	static int width = 700;
	static int height = 500;
	public Driver() {
		super("Money Tracker 2000 - Made by NACC inc.");
	}
	
	public static void main(String[] args) {
	
		Driver driver = new Driver();
		BorderLayout border = new BorderLayout();
		driver.setLayout(border);
		driver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driver.setPreferredSize(new Dimension(width, height));
		panel = new MainPanel(driver);
		driver.add(panel);
		driver.pack();
		driver.setVisible(true);
	}
}
