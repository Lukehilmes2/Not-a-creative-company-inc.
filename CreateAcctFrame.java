import java.awt.Dimension;

import javax.swing.JFrame;

public class CreateAcctFrame {

	public static void main(String[] args) {


				JFrame Frame = new JFrame("Money Tracker 2000");
				Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Frame.setPreferredSize(new Dimension(700, 500));
				Frame.add(new CreateAcct());

				Frame.pack();
				Frame.setVisible(true);
			
		


	}

}
