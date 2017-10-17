import java.awt.CardLayout;
import javax.swing.JPanel;


public class MainPanel extends JPanel{

	private CardLayout cards;
	private JPanel pnlLogin;
	private JPanel pnlCreateAcct;
	
	public MainPanel() {
		
		cards = new CardLayout();
		setLayout(cards);
		pnlLogin = new Login(this);
		pnlCreateAcct = new CreateAcct(this);
		add(pnlLogin, "Login");
		add(pnlCreateAcct, "CreateAcct");
		cards.show(this, "Login");
	}
	
	public void switchPanel(String panel) {
		
		cards.show(this, panel);
	}
}
