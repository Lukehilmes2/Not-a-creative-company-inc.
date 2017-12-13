import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Glass extends JPanel {

	private BufferedImage img;
	private boolean isLoggedIn;
	private final String companyName = "Made by NACC inc.";
	public Glass() {
		
		isLoggedIn = false;
		try {
			img = ImageIO.read(new File("smallMT2000.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean isLoggedIn() {
		
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		
		this.isLoggedIn = isLoggedIn;
		repaint();
	}
	public void paint(Graphics g) {
		
		super.paint(g);
		g.drawString(companyName, 5, this.getHeight() - 10);
		if(isLoggedIn) {
			g.drawImage(img, -15, 30, null);
		}
	}
}
