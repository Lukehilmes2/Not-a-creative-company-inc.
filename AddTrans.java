import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddTrans extends JPanel {

	private JLabel lblAmount, lblDescription, lblAdd;
	private JTextField txtAmount, txtDescription;
	private JButton btnAddTrans, btnBack;
	private int txtFieldLength = 20;
	private MainPanel panel;
	public AddTrans(MainPanel panel) {
		
		this.panel = panel;
		lblAdd = new JLabel("Add transaction: ");
		lblAmount = new JLabel("amount: ");
		lblDescription = new JLabel("description: ");
		txtAmount = new JTextField(txtFieldLength);
		txtDescription = new JTextField(txtFieldLength);
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		add(lblAdd);
		add(lblAmount);
		add(txtAmount);
		add(lblDescription);
		add(txtDescription);
		add(btnAddTrans);
		add(btnBack);
	}
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnBack) {
				panel.switchPanel("ViewAcct");
			}
			else if (e.getSource() == btnAddTrans) {				
				addTrans();
				panel.switchPanel("ViewAcct");
			}
		}
	}
	
	private void addTrans() {
		
		panel.addLine("transactions/" + panel.getAcct().getName() + ".txt", Double.parseDouble(txtAmount.getText()) + "," + txtDescription.getText());
		panel.updateTrans();
	}
}
