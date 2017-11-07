import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddTrans extends JPanel {

	private JLabel lblAmount, lblDescription, lblAdd;
	private JTextField txtAmount, txtDescription;
	private JButton btnAddTrans, btnBack;
	private int txtFieldLength = 20;
	private MainPanel panel;
	private JRadioButton btnCCard;
	private JRadioButton btnCheck;
	private JRadioButton btnExpense;
	private ButtonGroup radioBtns;
	private double ccRate = 1.00;
	private double uniFee = .92;
	private int expenseRate = 1;
	public AddTrans(MainPanel panel) {

		this.panel = panel;

		JPanel panel1 = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		lblAdd = new JLabel("Add transaction: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAdd, cs);

		lblAmount = new JLabel("amount: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAmount, cs);
		txtAmount = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtAmount, cs);

		lblDescription = new JLabel("description: ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDescription, cs);
		txtDescription = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtDescription, cs);

		btnAddTrans = new JButton("Add transaction");
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(btnAddTrans, cs);

		btnBack = new JButton("Back");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel2.add(btnBack, cs);
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.NORTH);

		btnCCard = new JRadioButton("Credit Card");
		btnCheck = new JRadioButton("Check");
		btnExpense = new JRadioButton("Expense");
		radioBtns = new ButtonGroup();
	
		radioBtns.add(btnCCard);
		radioBtns.add(btnCheck);
		radioBtns.add(btnExpense);
		btnCCard.addActionListener(new ButtonListener());
		btnCheck.addActionListener(new ButtonListener());
		btnExpense.addActionListener(new ButtonListener());
		panel1.add(btnCCard);
		panel1.add(btnCheck);
		panel1.add(btnExpense);
	}

	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnBack) {
				panel.switchPanel("ViewAcct");
			}
			else if (e.getSource() == btnAddTrans) {
				addTrans();
				txtAmount.setText("");
				txtDescription.setText("");
				panel.switchPanel("ViewAcct");
			}
			else if (e.getSource() == btnCCard) {
				ccRate = .96;
				expenseRate = 1;
				uniFee = .92;
			}
			else if (e.getSource() == btnCheck) {
				ccRate = 1.00;
				expenseRate = 1;
				uniFee = .92;
			}
			else if(e.getSource() == btnExpense) {
				expenseRate = -1;
				uniFee = 1.00;
				ccRate = 1.00;
			}
		}
	}
	private void addTrans() {
		
		double amount = Double.parseDouble(txtAmount.getText())*ccRate*expenseRate*uniFee;
		panel.addLine("transactions/" + panel.getAcct().getName() + ".txt", amount + "," + txtDescription.getText());
		panel.updateTrans();
	}
}
