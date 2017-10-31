import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

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
	public ViewAcct(MainPanel panel) {
			
		this.panel = panel;
		btnDelTrans = new JButton("Delete transaction");
		btnAddTrans = new JButton("Add transaction");
		btnBack = new JButton("Back");
		transactions = new JTable(model);
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
			double amount = Double.parseDouble((String)transactions.getValueAt(row, 0));
			String description = (String)transactions.getValueAt(row, 1);
			transSelected = new Transaction(amount, description);
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
	    	  File folder = new File("transactions");
	    	  file = new Scanner(new FileReader("transactions/" + account.getName() + ".txt"));
	      }
	      catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	      }
	      while (file.hasNext()) {
	        String nextLine = file.nextLine();
	        String[] transaction = nextLine.split(",");
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
	}
	
	public void setAcct(Account account) {
		
		this.account = account;
		updateTable();
	}
}
