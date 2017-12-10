import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class InitialView extends JPanel {


	private MainPanel panel;

	private JTable tblAccts;
	private String[] columnNames = { "Username", "Description", "Email",
			"Phone", "Balance" };
	private String[][] accounts;
	private TableModel model;
	private Account acctSelected;
	private JButton btnNoDelete, btnYesDelete;
	private JLabel lblDelete;
	private JLabel lblTotalBalance;
	private JLabel lblEmptyAccount;
	private JLabel lblUniFee;
	private JLabel lblCreditCardFee;
	private JMenuBar mb;
	private JMenu Logout, Benefits, Transactions, Accounts, Help;
	private JMenuItem help, logout, makeAct, deleteAct, viewAct,
			viewtransactions, benefits,modAct;
	private double uniFee;
	private double creditCardFee;
	private final String strEmptyAccount = "<html>This account can't be " +
			"<br>deleted because it<br>" +
			"has transactions</html>";
	private DecimalFormat fmt = new DecimalFormat("$0.00");
	private String strConfirmDelete = "<html>Are you sure you want<br>" +
			"to delete the account?";
	public InitialView(MainPanel panel) {

		this.panel = panel;
		BorderLayout border = new BorderLayout();
		setLayout(border);

		mb = new JMenuBar();
		Help = new JMenu("Help");
		Accounts = new JMenu("Accounts");
		Benefits = new JMenu("Benefits");
		Transactions = new JMenu("Transactions");
		help = new JMenuItem("Help");
		logout = new JMenuItem("Logout");
		makeAct = new JMenuItem("Make Account");
		benefits = new JMenuItem("Benefits Calculator");
		deleteAct = new JMenuItem("Delete Account");
		viewAct = new JMenuItem("View Account");
		viewtransactions = new JMenuItem("View Transactions");
		modAct = new JMenuItem("Modify Account");

		Help.add(logout);
		Help.add(help);
		Transactions.add(viewtransactions);
		Accounts.add(makeAct);
		Accounts.add(deleteAct);
		Accounts.add(viewAct);
		Accounts.add(modAct);
		Benefits.add(benefits);
		mb.add(Accounts);
		mb.add(Transactions);
		mb.add(Benefits);
		mb.add(Help);


		benefits.addActionListener(new ButtonListener());
		viewAct.addActionListener(new ButtonListener());
		modAct.addActionListener(new ButtonListener());
		deleteAct.addActionListener(new ButtonListener());
		makeAct.addActionListener(new ButtonListener());
		logout.addActionListener(new ButtonListener());
		viewtransactions.addActionListener(new ButtonListener());
		help.addActionListener(new ButtonListener());
		modAct.addActionListener(new ButtonListener());

		lblTotalBalance = new JLabel("");
		lblUniFee = new JLabel("");
		lblCreditCardFee = new JLabel("");
		uniFee = getFees(.08);
		creditCardFee = getFees(.04);
		lblUniFee.setText("University fee: " + fmt.format(uniFee));
		lblCreditCardFee.setText("Credit Card Fee: "
				+ fmt.format(creditCardFee));

		btnNoDelete = new JButton("No");
		lblDelete = new JLabel(strConfirmDelete);
		btnYesDelete = new JButton("Yes");
		lblEmptyAccount = new JLabel("");
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());



		JPanel butpan = new JPanel();
		butpan.setLayout(new BoxLayout(butpan, BoxLayout.Y_AXIS));


		butpan.add(lblTotalBalance);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblUniFee);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblCreditCardFee);

		JPanel cdelete = new JPanel();
		cdelete.setLayout(new BoxLayout(cdelete, BoxLayout.Y_AXIS));
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblDelete);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnYesDelete);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnNoDelete);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblEmptyAccount);
		add(butpan, BorderLayout.WEST);
		add(cdelete, BorderLayout.SOUTH);
		JPanel MenuBar = new JPanel();
		add(MenuBar, BorderLayout.NORTH);
		MenuBar.add(mb);
		tblAccts = new JTable(model);
		updateTable();
		add(new JScrollPane(tblAccts), BorderLayout.CENTER);
		lblDelete.setVisible(false);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		tblAccts.addMouseListener(new TableListener());

	}

	private double getFees(double percent) {

		double fees = 0.0;
		ArrayList<String[]> temp = new ArrayList<String[]>();
		File[] folder = null;
		folder = new File("transactions/").listFiles();
		for (File file : folder) {
			Scanner s = null;
			try {
				s = new Scanner(new FileReader(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (s.hasNext()) {
				Scanner nextLine = new Scanner(s.nextLine());
				nextLine.useDelimiter(",");
				while (nextLine.hasNext()) {
					String account = nextLine.next();
					String date = nextLine.next();
					double amount = Double.parseDouble(nextLine.next());
					String code = nextLine.next();
					String description = nextLine.next();
					if (code.equals("50109") && percent == .08) {
						fees += amount / .92 * .08;
					} else if (percent == .04 && code.equals("50287")) {
						fees += amount / .92 / .96 * .04;
					}
				}
			}
		}
		return fees;
	}

	public String[][] getAccounts() {

		return accounts;
	}

	public void updateTable() {

		accounts = getAccountsFromText();
		model = new DefaultTableModel(accounts, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		tblAccts.getTableHeader().setReorderingAllowed(false);
		tblAccts.setModel(model);
		double total = 0;
		for (int i = 0; i < tblAccts.getRowCount(); i++) {
			total += panel.getDoubleFrom$((String) tblAccts.getValueAt(i, 4));
		}
		lblTotalBalance.setText("Total Balance: " + fmt.format(total));
		uniFee = getFees(.08);
		creditCardFee = getFees(.04);
		lblUniFee.setText("University fee: " + fmt.format(uniFee));
		lblCreditCardFee.setText("Credit Card Fee: "
				+ fmt.format(creditCardFee));
	}

	private String[][] getAccountsFromText() {

		ArrayList<String[]> temp = new ArrayList<String[]>();
		Scanner file = null;
		try {
			file = new Scanner(new FileReader("accounts.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (file.hasNext()) {
			String nextLine = file.nextLine();
			String[] account = nextLine.split(",");
			String strBalance = account[4];
			account[4] = fmt.format(Double.parseDouble(strBalance));
			temp.add(account);
		}
		String[][] accounts = new String[temp.size()][5];
		for (int i = 0; i < temp.size(); i++) {
			accounts[i] = temp.get(i);
		}
		return accounts;
	}

	private class TableListener implements MouseListener {

		public void mouseClicked(java.awt.event.MouseEvent evt) {

			int row = tblAccts.rowAtPoint(evt.getPoint());
			String user = (String) tblAccts.getValueAt(row, 0);
			String description = (String) tblAccts.getValueAt(row, 1);
			String email = (String) tblAccts.getValueAt(row, 2);
			String phone = (String) tblAccts.getValueAt(row, 3);
			double balance = panel.getDoubleFrom$((String) tblAccts.getValueAt(
					row, 4));
			acctSelected = new Account(user, description, email, phone, balance);
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == makeAct) {
				panel.setAccount(acctSelected);
				panel.switchPanel("CreateAcct");
			} else if (evt.getSource() == viewAct) {

				panel.setAccount(acctSelected);
				panel.switchPanel("ViewAcct");
			} else if (evt.getSource() == modAct) {

				panel.updateStuff();
				panel.switchPanel("ModifyAcct");
			} else if (evt.getSource() == logout) {
				panel.switchPanel("Login");
			} else if (evt.getSource() == benefits) {
				panel.switchPanel("Benefits");
			} else if (evt.getSource() == viewtransactions) {
				panel.updateTrans();
				panel.switchPanel("TransactionView");
			} else if (evt.getSource() == deleteAct) {
				if (acctSelected != null) {
					lblDelete.setVisible(true);
					btnYesDelete.setVisible(true);
					btnNoDelete.setVisible(true);
				}
			} else if (evt.getSource() == btnNoDelete) {
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
			} else if (evt.getSource() == btnYesDelete) {
				if (acctSelected.getBalance() != 0) {

					lblEmptyAccount.setText(strEmptyAccount);
					panel.updateTable();
					panel.updateStuff();
					return;
				}
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
				String filename = ("transactions/"+ acctSelected.getName() + ".txt");
				String[][] transactions = panel.getTransFromText(filename);
				for (int i = 0; i < transactions.length; i++) {
					String tname = transactions[i][0];
					String tdate = transactions[i][1];
					String bala = (transactions[i][2]);
					bala = bala.replace("$","");
					double tbal = Double.parseDouble(bala);

					Integer tcode = Integer.parseInt(transactions[i][3]);
					String tdes = transactions[i][4];

					Transaction t = new Transaction(acctSelected.getName(), tdate, tbal, tcode, tdes);
					panel.deleteLine("transactions/" + acctSelected.getName() + ".txt",t.toString());
				}
				File f = new File("transactions/" + acctSelected.getName() + ".txt");
				f.delete();
				panel.deleteLine("accounts.txt", acctSelected.toString());
				updateTable();
			} else if (evt.getSource() == help) {

				HelpPage frameHelp = new HelpPage("Help");
				frameHelp.setVisible(true);
			}
			lblEmptyAccount.setText("");
		}
	}
	public Account getAccount(){
		return acctSelected;
	}
}
