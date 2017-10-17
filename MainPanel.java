import java.awt.CardLayout;
import javax.swing.JPanel;


public class MainPanel extends JPanel{

	private CardLayout cards;
	private JPanel pnlLogin, pnlCreateAcct;
	private InitialView pnlInitialView;
	private Account acct;
	
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
	
	public void setAcct(Account acct) {
		this.acct = acct;
	}
	
	public Account getAcct() {
		return acct;
	}
	
	public void switchPanel(String panel) {
		
		cards.show(this, panel);
	}
}
