import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class InitialView extends JPanel{

	private JButton btnDelete;
	private MainPanel panel;
	private JButton btnMakeAcct;
	private JButton btnLogOut;
	private JTable tblAccts;
	private String[] columnNames = {"Username", "First name", "Last name", "Email", "Phone"};
	private String[][] accounts;
	private TableModel model;

	public InitialView(MainPanel panel) {

		this.panel = panel;
		BorderLayout border= new BorderLayout();
		setLayout(border);
		btnDelete = new JButton("Delete Account");
		btnMakeAcct = new JButton("Make new Account");
		btnLogOut = new JButton("Logout");
		btnDelete.addActionListener(new ButtonListener());
		btnMakeAcct.addActionListener(new ButtonListener());
		btnLogOut.addActionListener(new ButtonListener());


		JPanel butpan = new JPanel();
		butpan.setLayout(new BoxLayout(butpan,BoxLayout.Y_AXIS));
		butpan.add(Box.createVerticalStrut(50));
		butpan.add(btnDelete);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnMakeAcct);
		butpan.add(Box.createVerticalStrut(20));
		butpan.add(btnLogOut);

		add(butpan,BorderLayout.WEST);
		accounts = getAccounts();
		model = new DefaultTableModel(accounts, columnNames)
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		tblAccts = new JTable(model);
		add(new JScrollPane(tblAccts),BorderLayout.EAST);
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
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent evt) {

			if (evt.getSource() == btnMakeAcct) {
				panel.switchPanel("CreateAcct");
			}

			else if (evt.getSource() == btnLogOut) {
				panel.switchPanel("Login");
			}
			else if (evt.getSource() == btnDelete) {
				panel.switchPanel("ConfirmDelete");
			}
		}
	}
}
