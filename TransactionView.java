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
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TransactionView extends JPanel {

	private String acctName;
	private JTable transactions;
	private String[] columnNames = { "account", "date", "amount", "code",
			"description" };
	private String[][] transData;
	private TableModel model;
	private Transaction transSelected;
	private JButton btnDelTrans, btnYesDelete, btnNoDelete, btnAddTrans,
			btnBack;
	private MainPanel panel;
	private JLabel lblDelete;
	private DecimalFormat fmt = new DecimalFormat("$#0.00");
	NumberFormat $fmt = NumberFormat.getCurrencyInstance();
	private JLabel lblTotalBalance;

	public TransactionView(MainPanel panel) {

		this.panel = panel;

		BorderLayout border = new BorderLayout();
		JPanel pnlDelete = new JPanel();
		btnDelTrans = new JButton("Delete transaction");
		btnYesDelete = new JButton("Yes, delete");
		btnNoDelete = new JButton("No, don't delete");
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		lblDelete = new JLabel("Are you sure you want to delete this transaction?");
		lblTotalBalance = new JLabel("");
		add(lblTotalBalance);
		transactions = new JTable(model);
		add(lblTotalBalance);
		updateTable();
		add(transactions);
		add(btnDelTrans);
		add(btnAddTrans);
		add(btnBack);
		pnlDelete.add(lblDelete);
		pnlDelete.add(btnYesDelete);
		pnlDelete.add(btnNoDelete);
		add(pnlDelete);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		lblDelete.setVisible(false);
		add(new JScrollPane(transactions));
		transactions.addMouseListener(new TableListener());
		btnDelTrans.addActionListener(new ButtonListener());
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		btnNoDelete.addActionListener(new ButtonListener());
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnBack) {

				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
				panel.switchPanel("InitialView");
			}
			if(e.getSource() == btnDelTrans){
				lblDelete.setVisible(true);
				btnYesDelete.setVisible(true);
				btnNoDelete.setVisible(true);
			}
			if (e.getSource() == btnYesDelete) {
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);

				panel.deleteLine("transactions/" + acctName+ ".txt", transSelected.toString());
				panel.updateTrans();
				panel.updateTable();
				updateTable();

			}
			if (e.getSource() == btnNoDelete) {
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
			}
			if (e.getSource() == btnAddTrans) {
				panel.updateStuff();
				panel.switchPanel("AddTransAll");
			}

		}
	}

	private class TableListener implements MouseListener {

		public void mouseClicked(java.awt.event.MouseEvent evt) {

			int row = transactions.rowAtPoint(evt.getPoint());
			acctName = (String) transactions.getValueAt(row, 0);
			String date = (String) transactions.getValueAt(row, 1);
			double dblAmount = getDoubleFrom$((String) transactions
					.getValueAt(row, 2));
			int code = Integer.parseInt((String) transactions
					.getValueAt(row, 3));
			String description = (String) transactions.getValueAt(row, 4);
			transSelected = new Transaction(acctName, date, dblAmount, code,
					description);
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
	public double getDoubleFrom$(String str) {
		int isNegative = 1;
		if (str.substring(0, 1).equals("-")) {
			isNegative = -1;
			String temp = str.substring(2, str.length());
			str = temp;
		} else {
			String temp = str.substring(1, str.length());
			str = temp;
		}

		return Double.parseDouble(str) * isNegative;

	}
	private String[][] getTransactions() {

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
					String amount = fmt.format(Double.parseDouble(nextLine
							.next()));
					String description = nextLine.next();
					String code = nextLine.next();
					String[] row = { account, date, amount, description, code };
					temp.add(row);
				}
			}
		}
		String[][] transactions = new String[temp.size()][5];
		for (int i = 0; i < transactions.length; i++) {
			transactions[i] = temp.get(i);
		}
		return transactions;
	}

	public void updateTable() {

		transData = getTransactions();
		model = new DefaultTableModel(transData, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		transactions.setModel(model);
	}
}
