import java.awt.CardLayout;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JPanel;


public class MainPanel extends JPanel{

	private CardLayout cards;
	private JPanel pnlLogin, pnlCreateAcct, pnlConfirmDelete;
	private InitialView pnlInitialView;
	private Account acct;
	private final String companyName = "NACC inc.";
	
	public MainPanel() {
		
		cards = new CardLayout();
		setLayout(cards);
		pnlLogin = new Login(this);
		pnlInitialView = new InitialView(this);
		pnlCreateAcct = new CreateAcct(this);
		pnlConfirmDelete = new ConfirmDelete(this);
		add(pnlLogin, "Login");
		add(pnlInitialView, "InitialView");
		add(pnlCreateAcct, "CreateAcct");
		add(pnlConfirmDelete, "ConfirmDelete");
		cards.show(this, "Login");
	}
	
	public void paint(Graphics g) {

	    super.paint(g);
	    g.drawString(companyName, 10, 450);
	}
	
	public Account getAcct() {
		return acct;
	}
	
	public void switchPanel(String panel) {
		
		cards.show(this, panel);
	}
	
	
	public void updateTable() {
		
		pnlInitialView.updateTable();
	}
	public void deleteAcct(Account acct) {
		
		File inputFile = new File("accounts.txt");
		File tempFile = new File("myTempFile.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lineToRemove = acct.toString();
        String currentLine;
        try {
            while((currentLine = reader.readLine()) != null)
            {
                if(currentLine.equals(lineToRemove)) {
                    continue;	
                }
                try {
                    writer.write(currentLine + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
    	    Files.move(tempFile.toPath(), inputFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
	}
}
