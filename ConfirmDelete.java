import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ConfirmDelete extends JPanel{

	private JButton btnDelete;
	private JButton btnYesDelete;
	private JButton btnNoDelete;
	private JLabel lblDelete;
	private MainPanel mainPanel;
	
	public ConfirmDelete(MainPanel pnl) {
	
		mainPanel = pnl;
		btnNoDelete = new JButton("No");
		lblDelete = new JLabel("Are you sure you want to delete your account?");
		btnYesDelete = new JButton("Yes, delete account");
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		add(lblDelete);
		add(btnYesDelete);
		add(btnNoDelete);
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent evt) {

			if (evt.getSource() == btnNoDelete) {	
				mainPanel.switchPanel("InitialView");
			}			
			else if(evt.getSource() == btnYesDelete) {
				
				mainPanel.switchPanel("Login");
			}
			
		}		
	}
}
