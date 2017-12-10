import java.awt.Color;

import javax.swing.JButton;


public class RedButton extends JButton {

	public RedButton(String text) {
		
		super(text);
		setBorderPainted(false);
		setBackground(Color.RED);
	}
}
