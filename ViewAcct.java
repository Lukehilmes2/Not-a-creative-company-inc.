import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;

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
	private String[] columnNames = {"amount", "description"};
	private String[][] transData;
	private TableModel model;
	private Transaction transSelected;
	private JButton btnDelTrans, btnAddTrans, btnBack;
	private MainPanel panel;
	private DecimalFormat fmt = new DecimalFormat("$#0.00");
	NumberFormat $fmt = NumberFormat.getCurrencyInstance();
	private JLabel lblTotalBalance;
	
	public ViewAcct(MainPanel panel) {
			
		this.panel = panel;
		btnDelTrans = new JButton("Delete transaction");
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		lblTotalBalance = new JLabel("");
		add(lblTotalBalance);
		transactions = new JTable(model);
		add(lblTotalBalance);
		updateTable();
		add(transactions);
		add(btnDelTrans);
		add(btnAddTrans);
		add(btnBack);
		add(new JScrollPane(transactions));
		transactions.addMouseListener(new TableListener());
		btnDelTrans.addActionListener(new ButtonListener());
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
	}
	
	private double getTotalBalance() {
		
		double total = 0;
		for (int i = 0; i < transactions.getRowCount(); i++) {
			
			total += panel.getDoubleFrom$((String)transactions.getValueAt(i, 0));

		}
		return total;
	}
	
	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnBack) {
				setAcct(null);
				panel.switchPanel("InitialView");
			}
			else if (e.getSource() == btnDelTrans) {
				
				panel.deleteLine("transactions/" + account.getName() + ".txt", transSelected.toString());
				panel.updateTrans();
			}
			else if (e.getSource() == btnAddTrans) {
				panel.switchPanel("AddTrans");
			}
		}
	}
	
	private class TableListener implements MouseListener {

		public void mouseClicked(java.awt.event.MouseEvent evt) {

			int row = transactions.rowAtPoint(evt.getPoint());			
			double dblAmount = panel.getDoubleFrom$((String)transactions.getValueAt(row, 0));
			String description = (String)transactions.getValueAt(row,  1);
			transSelected = new Transaction(dblAmount, description, 0);
		}
		public void mouseEntered(MouseEvent arg0 ){}
		public void mouseExited(MouseEvent arg0) {}
 		public void mousePressed(MouseEvent arg0) {}
 		public void mouseReleased(MouseEvent arg0) {}
  	}
	
	private String[][] getTransactions() {

		
		ArrayList<String[]> temp = new ArrayList<String[]>();
		Scanner file = null;
		if(account == null) {
			return new String[0][0];
		}
	      try {
	    	  file = new Scanner(new FileReader("transactions/" + account.getName() + ".txt"));
	      }
	      catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	      }
	      while (file.hasNext()) {
	        String nextLine = file.nextLine();
	        String[] transaction = nextLine.split(",");
	        transaction[0] = fmt.format(Double.parseDouble(transaction[0]));
	        temp.add(transaction);
	      }
	      String[][] transactions = new String[temp.size()][2];
	      for (int i = 0; i < transactions.length; i++) {
	    	  transactions[i] = temp.get(i);
	      }
	      return transactions;
	}

	public void updateTable() {
		
		transData = getTransactions();
		model = new DefaultTableModel(transData, columnNames){
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		transactions.setModel(model);
		if (account != null) {
			panel.deleteLine("accounts.txt", account.toString());
			account.setBalance(getTotalBalance());
			lblTotalBalance.setText("total balance: " + fmt.format(account.getBalance()));
			panel.addLine("accounts.txt", account.toString());
			panel.updateTable();
		}
	}
	
	public void setAcct(Account account) {
		
		this.account = account;
		updateTable();
	}
}
