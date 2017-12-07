import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddTrans extends JPanel implements ActionListener {

	private JLabel lblDate, lblAmount, lblDescription, lblAdd, lblError;
	private JTextField txtDate, txtAmount, txtDescription;
	private JButton btnAddTrans, btnBack;
	private int txtFieldLength = 20;
	private MainPanel panel;
	private double ccRate = 1.00;
	private double uniFee = .92;
	private int expenseRate = 1;
	private String curCode;
	private String[] codesList = { "50109 Other Income",
			"50287 Credit Card Sales", "61123 Contract Faculty",
			"61225 Student", "62210 Minor Equipment", "62241 Office Supplies",
			"62245 Computer Equipment <$5000",
			"62249 Minor Software < $100,000", "62255 Promotional Aids",
			"62280 Program Expense", "62282 Ink",
			"62315 Advertising-Newspaper Non Re",
			"62817 Meeting & Conference Cost", "62852 Bank Service Charges" };
	private JComboBox Codes;
	SimpleDateFormat dateFormat;

	public AddTrans(MainPanel panel) {

		this.panel = panel;
		dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		JPanel panel1 = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		lblAdd = new JLabel("Add Transaction: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAdd, cs);

		lblDate = new JLabel("Date: (dd-mm-yyyy)");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDate, cs);
		txtDate = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtDate, cs);

		lblAmount = new JLabel("Amount: ");
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

		lblDescription = new JLabel("Description: ");
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

		btnAddTrans = new JButton("Add Transaction");
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

		lblError = new JLabel();
		add(panel1, BorderLayout.CENTER);
		add(panel2, BorderLayout.NORTH);
		add(lblError, BorderLayout.SOUTH);

		Codes = new JComboBox(codesList);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(Codes, cs);
		Codes.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {

		curCode = Codes.getSelectedItem().toString();
		Character g = curCode.charAt(0);

		if (curCode.contains("Credit")) {
			ccRate = .96;
			expenseRate = 1;
			uniFee = .92;

		} else if (curCode.contains("50109")) {

			ccRate = 1.00;
			expenseRate = 1;
			uniFee = .92;
		} else if (g == '6') {

			expenseRate = -1;
			uniFee = 1.00;
			ccRate = 1.00;
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String curCode = Codes.getSelectedItem().toString();
			Character g = curCode.charAt(0);
			if (e.getSource() == btnBack) {
				panel.switchPanel("ViewAcct");
			} else if (e.getSource() == btnAddTrans) {
				try{
					Double.parseDouble(txtAmount.getText());
				} catch (NumberFormatException ev) {
					lblError.setText("Please give a valid number format(no $, e.g. 54.33)");
					return;
				}
				if (txtAmount.getText() != null
						&& txtAmount.getText().matches("[-+]?\\d*\\.?\\d+") == false) {
					lblError.setText("Please Enter a Valid number for the amount!");
				} else if (txtDate.getText().matches(
						"([0-9]{2})-([0-9]{2})-([0-9]{4})") == false) {
					lblError.setText("Please Enter a Correctly Formatted Date! (dd-mm-yyy)");
				} else {
					addTrans();
					txtAmount.setText("");
					txtDescription.setText("");
					panel.switchPanel("ViewAcct");
				}
			}
		}
	}

	private void addTrans() {

		String date = txtDate.getText();
		int code = Integer.parseInt(curCode.substring(0, 5));
		String description = txtDescription.getText();
		double amount = Double.parseDouble(txtAmount.getText()) * ccRate
				* expenseRate * uniFee;
		Transaction t = new Transaction(panel.getAcct().getName(), date,
				amount, code, description);
		panel.addLine("transactions/" + panel.getAcct().getName() + ".txt",
				t.toString());
		panel.updateTrans();
	}
}
