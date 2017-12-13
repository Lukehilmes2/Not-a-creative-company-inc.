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
		super("Money Tracker 2000");
		this.addComponentListener(new FrameListen(this));
	}
	
	public static void main(String[] args) {
	
		Driver driver = new Driver();
		BorderLayout border = new BorderLayout();
		driver.setLayout(border);
		driver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driver.setPreferredSize(new Dimension(width, height));
		panel = new MainPanel(driver);
		panel.setSize(width, height);
		pnlGlass = new Glass();
		pnlGlass.setSize(width, height);
		pnlGlass.setOpaque(false);
		JLayeredPane jp = driver.getLayeredPane();
		jp.add(panel, Integer.valueOf(1));
		jp.add(pnlGlass, Integer.valueOf(2));
		driver.pack();
		jp.setVisible(true);
		driver.reSize();
		driver.setVisible(true);
	}
	
	public void switchLogin() {
		
		pnlGlass.setLoggedIn(!pnlGlass.isLoggedIn());
	}
	
	public boolean isLoggedIn() {
		return pnlGlass.isLoggedIn();
	}
	public void reSize() {
		
	     
    	Rectangle r = getBounds();
    	width = r.width;
    	height = r.height;
		pnlGlass.setSize(width, height);
		panel.setSize(width, height);
	}
	private class FrameListen implements ComponentListener{
     
		private Driver driver;
		public FrameListen(Driver driver) {
			this.driver = driver;
		}
		public void componentHidden(ComponentEvent arg0) {
        }
        public void componentMoved(ComponentEvent arg0) {   
        }
        public void componentResized(ComponentEvent arg0) {
        	driver.reSize();
        }
        public void componentShown(ComponentEvent arg0) {
        	driver.reSize();
        }
    }
}
