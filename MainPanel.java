import java.awt.CardLayout;
import java.awt.Graphics;

import javax.swing.JPanel;


public class MainPanel extends JPanel{

	private CardLayout cards;
	private JPanel pnlLogin, pnlCreateAcct;
	private InitialView pnlInitialView;
	private Account acct;
	private final String companyName = "NACC inc.";
	private final String productName = "Money Tracker 2000";
	
	public MainPanel() {
		
		cards = new CardLayout();
		setLayout(cards);
		pnlLogin = new Login(this);
		pnlInitialView = new InitialView(this);
		pnlCreateAcct = new CreateAcct(this);
		add(pnlLogin, "Login");
		add(pnlInitialView, "InitialView");
		add(pnlCreateAcct, "CreateAcct");
		cards.show(this, "Login");
	}
	
	public void paint(Graphics g) {

	    super.paint(g);
	    g.drawString(companyName, 10, 450);
	    g.drawString(productName, 10, 70);
	}
	public void setAcct(Account acct) {
		this.acct = acct;
		pnlInitialView.setAccount();
	}
	
	public Account getAcct() {
		return acct;
	}
	
	public void switchPanel(String panel) {
		
		cards.show(this, panel);
	}
}
