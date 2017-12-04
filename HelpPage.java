import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpPage extends JFrame {

	private JPanel pnlTxt;
	private StringBuilder sb;
	private JLabel lblTxt;
	private final int x = 650;
	private final int y = 600;

	public HelpPage(String name) {

		super(name);
		setPreferredSize(new Dimension(x, y));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pnlTxt = new JPanel(new FlowLayout());
		sb = new StringBuilder("<html>");
		Scanner s = null;
		try {
			s = new Scanner(new File("help.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNext()) {

			String nextLine = s.nextLine();
			sb.append(nextLine + "<br>");
		}
		sb.append("</html>");
		lblTxt = new JLabel(sb.toString());
		lblTxt.setFont(new Font(lblTxt.getFont().getName(), Font.PLAIN, 12));
		pnlTxt.add(lblTxt);
		add(pnlTxt);
		pack();
	}
}
