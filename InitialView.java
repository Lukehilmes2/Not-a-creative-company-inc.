import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;


public class InitialView extends JPanel{

	private JButton btnDelete;
	private MainPanel panel;
	private JButton btnMakeAcct;
	private JButton btnLogOut;
	private JLabel lblName;
	private JLabel lblFName;
	private JLabel lblLName;
	private JTable tblAccts;
	private String[] columnNames = {"First name", "Last name", "Email", "Phone"};
	private String[][] Accounts;
	
	public InitialView(MainPanel panel) {
		
		this.panel = panel;
		btnDelete = new JButton("Delete Account");
		btnMakeAcct = new JButton("Make new Account");
		btnLogOut = new JButton("Logout");
		lblName = new JLabel("");
		lblFName = new JLabel("");
		lblLName = new JLabel("");
		btnDelete.addActionListener(new ButtonListener());	
		btnMakeAcct.addActionListener(new ButtonListener());
		btnLogOut.addActionListener(new ButtonListener());
		
		add(lblName);
		add(lblFName);
		add(lblLName);
		add(btnDelete);
		add(btnMakeAcct);
		add(btnLogOut);
	}

	public void setAccount(){
		
		lblName.setText("User Name: " + panel.getAcct().getName());	
		lblFName.setText("First Name: " + panel.getAcct().getfName());	
		lblLName.setText("Last Name: " + panel.getAcct().getlName());	
	}
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent evt) {

			if (evt.getSource() == btnMakeAcct) {
				panel.switchPanel("CreateAcct");
			}
			
			else if (evt.getSource() == btnLogOut) {
				panel.switchPanel("Login");
				panel.setAcct(null);
			}
			else if (evt.getSource() == btnDelete) {
				panel.switchPanel("ConfirmDelete");
			}
		}	
	}
}
