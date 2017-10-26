import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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


public class InitialView extends JPanel{

	private JButton btnDelete;
	private MainPanel panel;
	private JButton btnMakeAcct;
	private JButton btnLogOut;
	private JTable tblAccts;
	private String[] columnNames = {"Username", "First name", "Last name", "Email", "Phone"};
	private String[][] accounts;
	private TableModel model;
	private Account acctSelected;
	private JButton btnNoDelete, btnYesDelete;
	private JLabel lblDelete;
	
	public InitialView(MainPanel panel) {
		
		
		this.panel = panel;
		btnDelete = new JButton("Delete Account");
		btnMakeAcct = new JButton("Make new Account");
		btnLogOut = new JButton("Logout");
		btnNoDelete = new JButton("No");
		lblDelete = new JLabel("Are you sure you want to delete your account?");
		btnYesDelete = new JButton("Yes, delete account");
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		add(lblDelete);
		add(btnYesDelete);
		add(btnNoDelete);
		btnDelete.addActionListener(new ButtonListener());	
		btnMakeAcct.addActionListener(new ButtonListener());
		btnLogOut.addActionListener(new ButtonListener());
		add(btnDelete);
		add(btnMakeAcct);
		add(btnLogOut);	
		lblDelete.setVisible(false);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		accounts = getAccounts();
		model = new DefaultTableModel(accounts, columnNames)
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		tblAccts = new JTable(model);
		add(new JScrollPane(tblAccts));
		tblAccts.addMouseListener(new TableListener());
	}
	
	public void updateTable() {
		
		accounts = getAccounts();
		model = new DefaultTableModel(accounts, columnNames)
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		tblAccts.setModel(model);
	}
	
	private String[][] getAccounts() {
		
		ArrayList<String[]> temp = new ArrayList<String[]>();
		Scanner file = null;
	      try {
	        file = new Scanner(new FileReader("accounts.txt"));
	      }
	      catch (FileNotFoundException e) {
	        e.printStackTrace();
	      }
	      while (file.hasNext()) {
	        String nextLine = file.nextLine();
	        String[] account = nextLine.split(",");
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
			String user = (String)tblAccts.getValueAt(row, 0);
			String fName = (String)tblAccts.getValueAt(row, 1);
			String lName = (String)tblAccts.getValueAt(row, 2);
			String email = (String)tblAccts.getValueAt(row, 3);
			String phone = (String)tblAccts.getValueAt(row, 4);
			acctSelected = new Account(user, fName, lName, email, phone);
		}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		public void mousePressed(MouseEvent arg0) {}

		public void mouseReleased(MouseEvent arg0) {}	
	}
		private class ButtonListener implements ActionListener{
	
			public void actionPerformed(ActionEvent evt) {
	
				if (evt.getSource() == btnMakeAcct) {
					panel.switchPanel("CreateAcct");
				}
				
				else if (evt.getSource() == btnLogOut) {
					panel.switchPanel("Login");
				}
				else if (evt.getSource() == btnDelete) {
					if(acctSelected != null) {
						lblDelete.setVisible(true);
						btnYesDelete.setVisible(true);
						btnNoDelete.setVisible(true);	
					}
				}
				else if (evt.getSource() == btnNoDelete) {
					lblDelete.setVisible(false);
					btnYesDelete.setVisible(false);
					btnNoDelete.setVisible(false);
				}
				
				else if (evt.getSource() == btnYesDelete) {
					lblDelete.setVisible(false);
					btnYesDelete.setVisible(false);
					btnNoDelete.setVisible(false);
					panel.deleteAcct(acctSelected);
					updateTable();
				}
			}	
		}
}
