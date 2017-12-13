import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewAcct extends JPanel {

	private Account account;
	private JTable transactions;
	private String[] columnNames = { "Date", "Amount", "Code", "Description" };
	private String[][] transData;
	private TableModel model;
	private Transaction transSelected;
	private JButton btnDelTrans, btnAddTrans, btnYesDelete, btnNoDelete,
			btnBack;
	private MainPanel panel;
	private JLabel lblDelete, lblUniFee, lblCreditCardFee;
	private DecimalFormat fmt = new DecimalFormat("$#0.00");
	NumberFormat $fmt = NumberFormat.getCurrencyInstance();
	private JLabel lblTotalBalance, lblUsername;
	private String strConfirmDelete = "<html>Are you sure you want<br>"
			+ "to delete the<br> " + "transaction?";

	public ViewAcct(MainPanel panel) {

		this.panel = panel;
		JPanel pnlDelete = new JPanel();
		JPanel pnlDisplay = new JPanel();
		BorderLayout border = new BorderLayout();
		setLayout(border);
		pnlDelete.setLayout(new BoxLayout(pnlDelete, BoxLayout.Y_AXIS));
		btnDelTrans = new JButton("Delete transaction");
		lblDelete = new JLabel(strConfirmDelete);
		btnYesDelete = new JButton("Yes, delete transaction");
		btnNoDelete = new JButton("No, don't delete");
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		lblUniFee = new JLabel("");
		lblCreditCardFee = new JLabel("");
		lblUsername = new JLabel();
		lblTotalBalance = new JLabel("");
		pnlDisplay.add(lblTotalBalance);
		transactions = new JTable(model);
		updateTable();
		pnlDisplay.add(btnDelTrans);
		pnlDisplay.add(btnAddTrans);
		pnlDisplay.add(btnBack);
		pnlDelete.add(lblUsername);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(lblTotalBalance);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(lblUniFee);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(lblCreditCardFee);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(lblDelete);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(btnYesDelete);
		pnlDelete.add(Box.createVerticalStrut(20));
		pnlDelete.add(btnNoDelete);
		add(transactions, BorderLayout.EAST);
		add(pnlDelete, BorderLayout.WEST);
		add(pnlDisplay, BorderLayout.NORTH);
		lblDelete.setVisible(false);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		add(new JScrollPane(transactions));
		transactions.addMouseListener(new TableListener());
		btnDelTrans.addActionListener(new ButtonListener());
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		btnNoDelete.addActionListener(new ButtonListener());
	}

	private double getFees (double percent) {

		double fees = 0.0;
		Scanner s = null;
		
		try {
			account.getName();
		} catch (NullPointerException e) {
			return 0.0;
		}
		try {
			s = new Scanner(new FileReader("transactions/" + account.getName() + ".txt"));
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
				if (code.charAt(0) == '5' && percent == .08) {
					fees += amount / .92 * .08;
				} else if (percent == .04 && code.equals("50287")) {
					fees += amount / .92 / .96 * .04;
				}
			}
		}
		return fees;
	}

	private double getTotalBalance() {

		double total = 0;
		for (int i = 0; i < transactions.getRowCount(); i++) {

			total += panel.getDoubleFrom$((String) transactions
					.getValueAt(i, 1));
		}
		return total;
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnBack) {
				setAcct(null);
				panel.switchPanel("InitialView");
			} else if (e.getSource() == btnDelTrans) {

				lblDelete.setVisible(true);
				btnYesDelete.setVisible(true);
				btnNoDelete.setVisible(true);
			} else if (e.getSource() == btnYesDelete) {
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
				panel.deleteLine("transactions/" + account.getName() + ".txt",
						transSelected.toString());
				panel.updateTrans();
				updateTable();
			} else if (e.getSource() == btnNoDelete) {
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
			} else if (e.getSource() == btnAddTrans) {
				panel.switchPanel("AddTrans");
			}
		}
	}

	private class TableListener implements MouseListener {

		public void mouseClicked(java.awt.event.MouseEvent evt) {

			int row = transactions.rowAtPoint(evt.getPoint());
			String date = (String) transactions.getValueAt(row, 0);
			double dblAmount = panel.getDoubleFrom$((String) transactions
					.getValueAt(row, 1));
			int code = Integer.parseInt((String) transactions
					.getValueAt(row, 2));
			String description = (String) transactions.getValueAt(row, 3);
			transSelected = new Transaction(account.getName(), date, dblAmount,
					code, description);
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

	private String[][] getTransactions() {

		ArrayList<String[]> temp = new ArrayList<String[]>();
		Scanner file = null;
		if (account == null) {
			return new String[0][0];
		}
		try {
			file = new Scanner(new FileReader("transactions/"
					+ account.getName() + ".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		while (file.hasNext()) {
			Scanner nextLine = new Scanner(file.nextLine());
			nextLine.useDelimiter(",");
			while (nextLine.hasNext()) {
				String account = nextLine.next();
				String date = nextLine.next();
				String amount = fmt.format(Double.parseDouble(nextLine.next()));
				String description = nextLine.next();
				String code = nextLine.next();
				String[] row = { date, amount, description, code };
				temp.add(row);
			}

		}
		String[][] transactions = new String[temp.size()][2];
		for (int i = 0; i < transactions.length; i++) {
			transactions[i] = temp.get(i);
		}
		lblUsername.setText(account.getName());
		return transactions;
	}

	public void updateTable() {

		transData = getTransactions();
		model = new DefaultTableModel(transData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		transactions.getTableHeader().setReorderingAllowed(false);
		transactions.setModel(model);
		if (account != null) {
			panel.deleteLine("accounts.txt", account.toString());
			account.setBalance(getTotalBalance());
			lblTotalBalance.setText("Total Balance: "
					+ fmt.format(account.getBalance()));
			panel.addLine("accounts.txt", account.toString());
			panel.updateTable();
		}
		lblUniFee.setText("University fee: " + fmt.format(getFees(.08)));
		lblCreditCardFee.setText("Credit card fee: " + fmt.format(getFees(.04)));
	}

	public void setAcct(Account account) {

		this.account = account;
		updateTable();
	}
}
