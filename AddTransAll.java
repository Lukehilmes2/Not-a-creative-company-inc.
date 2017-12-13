import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.Color;
public class AddTransAll extends JPanel implements ActionListener {

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
	private JComboBox<String> users;
	private String[] userlist;
	private String[][] accounts,transactions;
	private Account acct;
	private DecimalFormat fmt = new DecimalFormat("0.00");
	public AddTransAll(MainPanel panel) {

		this.panel = panel;

		users = new JComboBox<>();
		//updateStuff();

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
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDate, cs);
		txtDate = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtDate, cs);

		lblAmount = new JLabel("Amount: ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAmount, cs);
		txtAmount = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtAmount, cs);

		lblDescription = new JLabel("Description: ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDescription, cs);
		txtDescription = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtDescription, cs);

		btnAddTrans = new JButton("Add Transaction");
		cs.gridx = 1;
		cs.gridy = 5;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(btnAddTrans, cs);

		btnBack = new JButton("Back");
		cs.gridx = 0;
		cs.gridy = 5;
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
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(Codes, cs);
		Codes.addActionListener(this);


		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(users,cs);
		panel1.add(users,cs);

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
			try {
				curCode = Codes.getSelectedItem().toString();
			} catch (NullPointerException er) {
				curCode = "50109";
			}
			Character g = curCode.charAt(0);
			String username = users.getSelectedItem().toString();
			accounts = panel.getAccounts();
			for (int i = 0; i < accounts.length; i++) {
				if(accounts[i][0].equals(username)){
					double checkbal = panel.getDoubleFrom$(accounts[i][4]);
					acct = new Account(accounts[i][0],accounts[i][1],accounts[i][2],accounts[i][3],checkbal);

				}

			}

			if (e.getSource() == btnBack) {
				txtAmount.setText("");
				txtDescription.setText("");
				txtDate.setBackground(Color.WHITE);
				txtDescription.setBackground(Color.WHITE);
				txtAmount.setBackground(Color.WHITE);
				lblError.setText("");
				panel.updateTrans();
				panel.switchPanel("TransactionView");
			} else if (e.getSource() == btnAddTrans) {
				if (txtAmount.getText() != null
						&& txtAmount.getText().matches("[-+]?\\d*\\.?\\d+") == false) {
					txtAmount.setBackground(Color.RED);
					txtDate.setBackground(Color.WHITE);
					txtDescription.setBackground(Color.WHITE);

					lblError.setText("Please Enter a Valid number for the amount!");
				} else if (txtDate.getText().matches(
						"([0-9]{2})-([0-9]{2})-([0-9]{4})") == false) {
					txtDate.setBackground(Color.RED);

					txtDescription.setBackground(Color.WHITE);
					txtAmount.setBackground(Color.WHITE);
					lblError.setText("Please Enter a Correctly Formatted Date! (dd-mm-yyyy)");
				} else if (txtDate.getText().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})") == false && txtAmount.getText() != null
						&& txtAmount.getText().matches("[-+]?\\d*\\.?\\d+") == false && (txtDescription.getText().replaceAll("\\s+","").equals(""))) {
					txtDate.setBackground(Color.RED);

					txtDescription.setBackground(Color.RED);
					txtAmount.setBackground(Color.RED);
					lblError.setText("Enter all fields");
				}
				 else {
					if (txtDescription.getText().replaceAll("\\s+","").equals("")) {
						txtDescription.setBackground(Color.RED);
						txtDate.setBackground(Color.WHITE);

						txtAmount.setBackground(Color.WHITE);
						lblError.setText("Please give a description to this transaction");
						return;
					}

					addTrans(acct);
					txtAmount.setText("");
					lblError.setText("");
					txtDescription.setText("");
					txtDate.setBackground(Color.WHITE);
					txtDescription.setBackground(Color.WHITE);
					txtAmount.setBackground(Color.WHITE);
					panel.switchPanel("TransactionView");
				}
			}
		}
	}

	private void addTrans(Account acct) {
		String curCode = Codes.getSelectedItem().toString();
		String date = txtDate.getText();
		int code = Integer.parseInt(curCode.substring(0, 5));
		String description = txtDescription.getText();
		String amt = fmt.format(Double.parseDouble(txtAmount.getText())* ccRate * expenseRate * uniFee);
		double amount = Double.parseDouble(amt);

		Transaction t = new Transaction(acct.getName(), date,
				amount, code, description);
		panel.addLine("transactions/" + acct.getName() + ".txt",t.toString());
		panel.deleteLine("accounts.txt", acct.toString());
		acct.setBalance(acct.getBalance() + amount);
		panel.addLine("accounts.txt", acct.toString());
		panel.updateTable();
		panel.updateTrans();
	}
	public void updateStuff() {

		panel.updateTable();
		accounts = panel.getAccounts();
		userlist = new String[accounts.length];
		for (int i = 0; i < accounts.length; i++) {


			userlist[i] = (accounts[i][0]);
		}
		users.removeAllItems();
			for(String s : userlist){
					users.addItem(s);
			}
	}
}
