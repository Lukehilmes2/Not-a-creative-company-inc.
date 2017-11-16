import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HelpPage extends JPanel {
	private MainPanel panel;
	private JTextArea area;
	private File Help = new File("./src/default package/Help");

	public HelpPage(MainPanel panel){
		this.panel = panel;
		BorderLayout border= new BorderLayout();
		setLayout(border);
		area = new JTextArea("This is are Help page" +
	"this has info on everything that are program does" +
	
	"Starting from the begging I will walk you through the proper way to go through all of are program. \n" +
	"-You log in with your user name and password if successful will bring you to the main view" + 
		"witch changes depending on what kind of level of security with the accounts that you can see \n" +
	"-From there you can do so much from accessing any account and viewing the transactions associated with it" +
		"by clicking on whatever account then click the view account button \n" + 
	 "-In the view account page you can delete the account, add transactions, and view all transactions all ready there \n" + 
	 "-When you create a new transaction you have the options between ");
		
		
	}
}
