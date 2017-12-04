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

	private JButton btnDelete;
	private MainPanel panel;
	private JButton btnMakeAcct;
	private JButton btnLogOut;
	private JTable tblAccts;
	private String[] columnNames = { "Username", "Description", "Email",
			"Phone", "Balance" };
	private String[][] accounts;
	private TableModel model;
	private Account acctSelected;
	private JButton btnNoDelete, btnYesDelete, btnViewAcct, btnBenefits,
			btnTransactions, btnHelp, btnModAcct;
	private JLabel lblDelete;
	private JLabel lblTotalBalance;
	private JLabel lblEmptyAccount;
	private JLabel lblUniFee;
	private JLabel lblCreditCardFee;
	private JMenuBar mb;
	private JMenu Logout, Benefits, Transactions, Accounts, Help;
	private JMenuItem help, logout, makeAct, deleteAct, viewAct,
			viewtransactions, benefits;
	private double uniFee;
	private double creditCardFee;
	private final String strEmptyAccount = "This account can't be deleted because it has transactions";
	private DecimalFormat fmt = new DecimalFormat("$0.00");

	public InitialView(MainPanel panel) {

		this.panel = panel;
		BorderLayout border = new BorderLayout();
		setLayout(border);

		mb = new JMenuBar();
		Logout = new JMenu("Logout");
		Help = new JMenu("Help");
		Accounts = new JMenu("Accounts");
		Benefits = new JMenu("Benefits");
		Transactions = new JMenu("Transactions");
		help = new JMenuItem("Help");
		logout = new JMenuItem("Logout");
		makeAct = new JMenuItem("Make Account");
		benefits = new JMenuItem("Benefits");
		deleteAct = new JMenuItem("Delete Account");
		viewAct = new JMenuItem("View Account");
		viewtransactions = new JMenuItem("View Transactions");
		Logout.add(logout);
		Help.add(help);
		Transactions.add(viewtransactions);
		Accounts.add(makeAct);
		Accounts.add(deleteAct);
		Accounts.add(viewAct);
		Benefits.add(benefits);
		mb.add(Accounts);
		mb.add(Transactions);
		mb.add(Logout);
		mb.add(Help);
		mb.add(Benefits);

		btnDelete = new JButton("Delete Account");
		btnMakeAcct = new JButton("Make new Account");
		btnLogOut = new JButton("Logout");
		btnViewAcct = new JButton("View Account");
		btnModAcct = new JButton("Modify Account");
		btnBenefits = new JButton("Benefits");
		btnTransactions = new JButton("Transactions");
		btnBenefits.addActionListener(new ButtonListener());
		btnViewAcct.addActionListener(new ButtonListener());
		btnModAcct.addActionListener(new ButtonListener());
		btnDelete.addActionListener(new ButtonListener());
		btnMakeAcct.addActionListener(new ButtonListener());
		btnLogOut.addActionListener(new ButtonListener());
		btnTransactions.addActionListener(new ButtonListener());

		lblTotalBalance = new JLabel("");
		lblUniFee = new JLabel("");
		lblCreditCardFee = new JLabel("");
		uniFee = getFees(.08);
		creditCardFee = getFees(.04);
		lblUniFee.setText("University fee: " + fmt.format(uniFee));
		lblCreditCardFee.setText("Creedit Card Fee: "
				+ fmt.format(creditCardFee));

		btnNoDelete = new JButton("No");
		lblDelete = new JLabel("Are you sure you want to delete your account?");
		btnYesDelete = new JButton("Yes, delete account");
		lblEmptyAccount = new JLabel("");
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());

		btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ButtonListener());
		btnHelp.setVisible(true);

		JPanel butpan = new JPanel();
		butpan.setLayout(new BoxLayout(butpan, BoxLayout.Y_AXIS));

		butpan.add(btnLogOut);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnMakeAcct);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnDelete);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnViewAcct);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnModAcct);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnBenefits);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnTransactions);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblTotalBalance);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblUniFee);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(lblCreditCardFee);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnHelp);
		JPanel cdelete = new JPanel();
		cdelete.setLayout(new BoxLayout(cdelete, BoxLayout.Y_AXIS));
		cdelete.add(lblDelete);
		cdelete.add(btnYesDelete);
		cdelete.add(btnNoDelete);
		cdelete.add(lblEmptyAccount);
		add(butpan, BorderLayout.WEST);
		add(cdelete, BorderLayout.NORTH);
		JPanel MenuBar = new JPanel();
		add(MenuBar, BorderLayout.NORTH);
		MenuBar.add(mb);
		tblAccts = new JTable(model);
		updateTable();
		add(new JScrollPane(tblAccts), BorderLayout.EAST);
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
						System.out.println(amount + " " + fees);
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
		tblAccts.setModel(model);
		double total = 0;
		for (int i = 0; i < tblAccts.getRowCount(); i++) {
			total += panel.getDoubleFrom$((String) tblAccts.getValueAt(i, 4));
		}
		lblTotalBalance.setText("Total Balance: " + fmt.format(total));
		uniFee = getFees(.08);
		creditCardFee = getFees(.04);
		lblUniFee.setText("University fee: " + fmt.format(uniFee));
		lblCreditCardFee.setText("Creedit Card Fee: "
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
			if (evt.getSource() == btnMakeAcct) {
				panel.setAccount(acctSelected);
				panel.switchPanel("CreateAcct");
			} else if (evt.getSource() == btnViewAcct) {

				panel.setAccount(acctSelected);
				panel.switchPanel("ViewAcct");
			} else if (evt.getSource() == btnModAcct) {

				panel.setAccount(acctSelected);
				panel.switchPanel("ModifyAcct");
			} else if (evt.getSource() == btnLogOut) {
				panel.switchPanel("Login");
			} else if (evt.getSource() == btnBenefits) {
				panel.switchPanel("Benefits");
			} else if (evt.getSource() == btnTransactions) {
				panel.switchPanel("TransactionView");
			} else if (evt.getSource() == btnDelete) {
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
					return;
				}
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
				File transactionFile = new File("transactions/"
						+ acctSelected.getName() + ".txt");
				transactionFile.delete();
				panel.deleteLine("accounts.txt", acctSelected.toString());
				updateTable();
			} else if (evt.getSource() == btnHelp) {

				HelpPage frameHelp = new HelpPage("Help");
				frameHelp.setVisible(true);
			}
			lblEmptyAccount.setText("");
		}
	}
}
