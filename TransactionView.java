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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	private String[] columnNames = { "Account", "Date", "Amount", "Code",
			"Description" };
	private String[][] transData;
	private TableModel model;
	private Transaction transSelected;
	private JButton btnDelTrans, btnYesDelete, btnNoDelete, btnAddTrans,
			btnBack;
	private MainPanel panel;
	private JLabel lblDelete, lblTotal, lblUniFee, lblCreditCardFee;
	private DecimalFormat fmt = new DecimalFormat("$#0.00");
	NumberFormat $fmt = NumberFormat.getCurrencyInstance();
	private JLabel lblTotalBalance;

	public TransactionView(MainPanel panel) {

		this.panel = panel;

		BorderLayout border = new BorderLayout();
		setLayout(border);
		JPanel pnlDelete = new JPanel();
		JPanel pnlDisplay = new JPanel();
		btnDelTrans = new JButton("Delete transaction");
		btnYesDelete = new JButton("Yes, delete");
		btnNoDelete = new JButton("No, don't delete");
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		lblDelete = new JLabel("<html>Are you sure you<br>" +
				"want to delete this<br>" +
				"transaction?");
		lblTotalBalance = new JLabel("");
		add(lblTotalBalance);
		transactions = new JTable(model);
		add(lblTotalBalance);
		lblTotal = new JLabel("");
		lblUniFee = new JLabel("");
		lblCreditCardFee = new JLabel("");
		updateTable();
		pnlDisplay.add(transactions);
		pnlDisplay.add(btnDelTrans);
		pnlDisplay.add(btnAddTrans);
		pnlDisplay.add(btnBack);		

		pnlDelete.setLayout(new BoxLayout(pnlDelete, BoxLayout.Y_AXIS));
		pnlDelete.add(lblTotal);
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
		
		add(pnlDelete, BorderLayout.WEST);
		add(transactions, BorderLayout.EAST);
		add(pnlDisplay, BorderLayout.NORTH);
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

			if(e.getSource() != btnDelTrans) {
				
				lblDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				btnNoDelete.setVisible(false);
			}
			if (e.getSource() == btnBack) {
				
				panel.switchPanel("InitialView");
			} else if(e.getSource() == btnDelTrans){
				lblDelete.setVisible(true);
				btnYesDelete.setVisible(true);
				btnNoDelete.setVisible(true);
			} else if (e.getSource() == btnYesDelete) {

				panel.deleteLine("transactions/" + acctName+ ".txt", transSelected.toString());
				panel.updateTrans();
				panel.updateTable();
				updateTable();

			} else if (e.getSource() == btnAddTrans) {
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
		transactions.getTableHeader().setReorderingAllowed(false);
		lblTotal.setText("Total balance: " + fmt.format(panel.getTotal()));
		lblUniFee.setText("University fee: " + fmt.format(panel.getUniFee()));
		lblCreditCardFee.setText("Credit card fee: " + fmt.format(panel.getCreditCardFee()));
		transactions.setModel(model);
	}
}
