import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.io.File;
import java.nio.file.Files;
import java.io.PrintWriter;

public class ModifyAcct extends JPanel implements ActionListener {

	private JTextField name;
	private JTextField description, email, phone, balance;
	private JLabel lblName, lblDescription, lblEmail, lblPhone, lblBalance;
	private JLabel acct, badacct;
	private JButton create, back;
	private String file = "accounts.txt";
	private int txtFieldLength = 10;
	private MainPanel panel;
	private InitialView init;
	private JComboBox<String> users;
	private String[] userlist;
	private String[][] accounts, transactions;
	private Account acctSelected, updateAcct;
	private DecimalFormat fmt = new DecimalFormat("$0.00");
	private String nam, des, ema, pho;
	private double bal;
	private JPanel panel1;
	GridBagConstraints cs;

	public ModifyAcct(MainPanel panel) {

		this.panel = panel;
		
		setLayout(new BorderLayout());
		panel1 = new JPanel(new GridBagLayout());
		cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		users = new JComboBox<>();
		updateStuff();

		acct = new JLabel();
		acct.setText("Modify Which Account?");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(acct, cs);

		lblName = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblName, cs);

		name = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(name, cs);

		lblDescription = new JLabel("Description: ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDescription, cs);

		description = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(description, cs);

		lblEmail = new JLabel("Email: ");
		cs.gridx = 0;
		cs.gridy = 8;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblEmail, cs);

		email = new JTextField(50);
		cs.gridx = 1;
		cs.gridy = 8;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(email, cs);

		lblPhone = new JLabel("Phone: ");
		cs.gridx = 0;
		cs.gridy = 10;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblPhone, cs);

		phone = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 10;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(phone, cs);

		create = new JButton("Change Account");
		cs.gridx = 1;
		cs.gridy = 12;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(create, cs);

		badacct = new JLabel("");
		cs.gridx = 1;
		cs.gridy = 14;
		cs.gridwidth = 2;
		cs.ipady = 20;
		panel1.add(badacct, cs);

		back = new JButton("Back");

		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(users, cs);
		add(panel1, BorderLayout.CENTER);
		add(back, BorderLayout.NORTH);
		back.addActionListener(new ButtonListener());
		create.addActionListener(new ButtonListener());
		users.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {

		String curUserName;
		try {
			curUserName = users.getSelectedItem().toString();
		} catch (NullPointerException ev) {
			return;
		}
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i][0].equals(curUserName)) {
				name.setText(accounts[i][0]);
				description.setText(accounts[i][1]);
				email.setText(accounts[i][2]);
				phone.setText(accounts[i][3]);
				nam = accounts[i][0];
				des = accounts[i][1];
				ema = accounts[i][2];
				pho = accounts[i][3];
				String bala = (accounts[i][4]);
				bala = bala.replace("$", "");
				bal = Double.parseDouble(bala);
			}
		}
		panel.updateTrans();
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == create) {
				if (!name.getText().matches(".*\\w.*")
						|| !description.getText().matches(".*\\w.*")
						|| !email.getText().matches(".*\\w.*")
						|| !phone.getText().matches(".*\\w.*")) {
					badacct.setText("Please fill out all the fields!");
					return;
				}
				panel.updateTable();
				acctSelected = new Account(nam, des, ema, pho, bal);
				updateAcct = new Account(name.getText(), description.getText(),
						email.getText(), phone.getText(), bal);
				acctSelected = new Account(nam,des,ema,pho,bal);
				updateAcct = new Account(name.getText(),description.getText(),email.getText(),phone.getText(),bal);
				if(!updateAcct.getName().equals(nam) || !updateAcct.getDescription().equals(des)|| !updateAcct.getEmail().equals(ema) || !updateAcct.getPhone().equals(pho)){
				panel.addLine("accounts.txt", updateAcct.toString());
				panel.deleteLine("accounts.txt", acctSelected.toString());
			}
				String filename = ("transactions/" + acctSelected.getName() + ".txt");
				transactions = getTransFromText(filename);

				for (int i = 0; i < transactions.length; i++) {
					String tname = transactions[i][0];
					String tdate = transactions[i][1];
					String bala = (transactions[i][2]);
					bala = bala.replace("$", "");
					double tbal = Double.parseDouble(bala);

					Integer tcode = Integer.parseInt(transactions[i][3]);
					String tdes = transactions[i][4];

					Transaction t = new Transaction(updateAcct.getName(), tdate, tbal, tcode, tdes);
					if(!updateAcct.getName().equals(tname)|| !updateAcct.getDescription().equals(des)|| !updateAcct.getEmail().equals(ema) || !updateAcct.getPhone().equals(pho)){
					panel.addLine("transactions/" + updateAcct.getName() + ".txt",t.toString());
					panel.deleteLine("transactions/" + tname + ".txt",t.toString());
				}
				}

				if(!updateAcct.getName().equals(nam)){
				File transactionFile = new File("transactions/" + acctSelected.getName() + ".txt");
				try{
				PrintWriter pw = new PrintWriter(filename);
				pw.close();}
				catch(FileNotFoundException e){
					System.out.println("Cant find file");
					e.printStackTrace();
				}
				transactionFile.delete();
			}
				panel.updateTable();
				description.setText("");
				email.setText("");
				phone.setText("");
				panel.updateTable();
				panel.switchPanel("InitialView");

			} else if (arg0.getSource() == back) {
				name.setText("");
				description.setText("");
				email.setText("");
				phone.setText("");
				badacct.setText("");
				panel.updateTrans();
				panel.updateTable();
				panel.updateStuff();
				panel.switchPanel("InitialView");
			}
		}

		private String[][] getTransFromText(String filename) {
			ArrayList<String[]> temp = new ArrayList<String[]>();
			Scanner file = null;
			try {
				file = new Scanner(new FileReader(filename));
			} catch (FileNotFoundException e) {
				System.out.println("Cant find file");
				e.printStackTrace();
			}
			while (file.hasNext()) {
				String nextLine = file.nextLine();
				if (!nextLine.equals("")) {
					String[] transaction = nextLine.split(",");
					String strBalance = transaction[2];
					transaction[2] = fmt.format(Double.parseDouble(strBalance));
					temp.add(transaction);
				}
			}
			String[][] transactions = new String[temp.size()][4];
			for (int i = 0; i < temp.size(); i++) {
				transactions[i] = temp.get(i);
			}
			return transactions;
		}
	}

	public void updateStuff() {

		panel.updateTable();
		accounts = panel.getAccounts();
		userlist = new String[accounts.length];
		for (int i = 0; i < accounts.length; i++) {
			userlist[i] = (accounts[i][0]);
		}
		users.removeAllItems();
		for (String s : userlist) {
			users.addItem(s);
		}
	}

}
