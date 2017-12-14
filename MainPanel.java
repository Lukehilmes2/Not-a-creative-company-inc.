import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainPanel extends JPanel {

	private CardLayout cards;
	private JPanel pnlCreateAcct, pnlConfirmDelete, pnlAddTrans, pnlBenefits;
	private AddTransAll pnlAddTransAll;
	private ModifyAcct pnlModifyAcct;
	private Login pnlLogin;
	private ViewAcct pnlViewAcct;
	private InitialView pnlInitialView;
	private TransactionView pnlTransactionView;
	private Account acct;
	private final double minutesAutoLogOut = 1;
	private Timer timer;
	private DecimalFormat fmt = new DecimalFormat("$0.00");
	private Image img;
	private boolean loggedIn = false;
	
	public MainPanel() {

		try {
			img = ImageIO.read(new File("smallMT2000.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		cards = new CardLayout();
		setLayout(cards);
		pnlLogin = new Login(this);
		pnlInitialView = new InitialView(this);
		pnlCreateAcct = new CreateAcct(this);
		pnlViewAcct = new ViewAcct(this);
		pnlModifyAcct = new ModifyAcct(this);
		pnlAddTrans = new AddTrans(this);
		pnlBenefits = new Benefits(this);
		pnlTransactionView = new TransactionView(this);
		pnlAddTransAll = new AddTransAll(this);
		add(pnlLogin, "Login");
		add(pnlInitialView, "InitialView");
		add(pnlCreateAcct, "CreateAcct");
		add(pnlViewAcct, "ViewAcct");
		add(pnlModifyAcct, "ModifyAcct");
		add(pnlAddTrans, "AddTrans");
		add(pnlBenefits, "Benefits");
		add(pnlTransactionView, "TransactionView");
		add(pnlAddTransAll, "AddTransAll");
		cards.show(this, "Login");
		timer = new Timer(((int) (1000 * 60 * minutesAutoLogOut)),
				new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						switchPanel("Login");
						loggedIn = false;
						pnlLogin.autoLogOut();
					}
				});
		timer.start();
		addMouseMotionListener(new IdleListener());
	}

	public double getUniFee() {

		return pnlInitialView.getUniFee();
	}

	public double getCreditCardFee() {

		return pnlInitialView.getCreditCardFee();
	}

	public double getTotal() {

		return pnlInitialView.getTotal();
	}

	public String[][] getAccounts() {
		return pnlInitialView.getAccounts();
	}

	public void setAccount(Account acct) {
		this.acct = acct;
		pnlViewAcct.setAcct(acct);
	}

	public Account getAcct() {

		return acct;
	}

	public void switchPanel(String panel) {

		cards.show(this, panel);
	}

	public void updateTable() {

		pnlInitialView.updateTable();

	}

	public void updateTrans() {

		pnlViewAcct.updateTable();
		pnlTransactionView.updateTable();
	}

	public void updateStuff() {

		pnlModifyAcct.updateStuff();
		pnlAddTransAll.updateStuff();
	}

	public String[][] getTransFromText(String filename) {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		Scanner file = null;
		try {
			file = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {

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

	public void addLine(String filename, String lineToWrite) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename, true));
			writer.append(lineToWrite + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteLine(String filename, String lineToRemove) {

		File inputFile = new File(filename);
		File tempFile = new File("myTempFile.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(tempFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String currentLine;
		try {
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.equals(lineToRemove)) {
					continue;
				}
				try {
					writer.write(currentLine + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter writer1 = null;
		try {
			writer1 = new BufferedWriter(new FileWriter(inputFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader1 = null;
		try {
			reader1 = new BufferedReader(new FileReader(tempFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentLine1 = null;
		try {
			while ((currentLine1 = reader1.readLine()) != null) {
				String trimmedLine = currentLine1.trim();
				writer1.write(currentLine1
						+ System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			reader1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	private class IdleListener implements MouseMotionListener {

		public void mouseDragged(MouseEvent arg0) {
		}

		public void mouseMoved(MouseEvent arg0) {

			timer.restart();
		}
	}
}
