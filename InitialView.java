import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InitialView extends JPanel{

	private JLabel lblDelete;
	private JButton btnDelete;
	private JButton btnYesDelete;
	private JButton btnNoDelete;
	private MainPanel panel;
	private JButton btnMakeAcct;
	private JButton btnLogOut;
	private JLabel lblName;
	private JLabel lblFName;
	private JLabel lblLName;
	public InitialView(MainPanel panel) {
		
		this.panel = panel;
		btnDelete = new JButton("Delete Account");
		lblDelete = new JLabel("Are you sure you want to delete your account?");
		btnYesDelete = new JButton("Yes, delete account");
		btnMakeAcct = new JButton("Make new Account");
		btnNoDelete = new JButton("No");
		btnLogOut = new JButton("Logout");
		lblName = new JLabel("");
		lblFName = new JLabel("");
		lblLName = new JLabel("");
		lblDelete.setVisible(false);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		btnDelete.addActionListener(new ButtonListener());
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		btnMakeAcct.addActionListener(new ButtonListener());
		btnLogOut.addActionListener(new ButtonListener());
		add(lblName);
		add(lblFName);
		add(lblLName);
		add(btnDelete);
		add(lblDelete);
		add(btnYesDelete);
		add(btnNoDelete);
		add(btnMakeAcct);
		add(btnLogOut);
	}

	public void setAccount(){
		
		lblName.setText("User Name: " + panel.getAcct().getName());	
		lblFName.setText("First Name: " + panel.getAcct().getfName());	
		lblLName.setText("Last Name: " + panel.getAcct().getlName());	
	}
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent evt) {

			if (evt.getSource() == btnMakeAcct) {
				panel.switchPanel("CreateAcct");
			}
			
			else if (evt.getSource() == btnLogOut) {
				panel.switchPanel("Login");
				panel.setAcct(null);
			}
			else if (evt.getSource() == btnDelete) {
				
				lblName.setVisible(false);
				lblFName.setVisible(false);
				lblLName.setVisible(false);
				btnDelete.setVisible(false);
				btnLogOut.setVisible(false);
				btnMakeAcct.setVisible(false);
				btnNoDelete.setVisible(true);
				btnYesDelete.setVisible(true);
				lblDelete.setVisible(true);
			}
			else if (evt.getSource() == btnNoDelete) {
			
				btnNoDelete.setVisible(false);
				lblName.setVisible(true);
				lblFName.setVisible(true);
				lblLName.setVisible(true);
				btnLogOut.setVisible(true);
				btnMakeAcct.setVisible(true);
				btnYesDelete.setVisible(false);
				lblDelete.setVisible(false);	
				btnDelete.setVisible(true);
			}
			else if(evt.getSource() == btnYesDelete) {
				
				btnNoDelete.setVisible(false);
				lblName.setVisible(true);
				lblFName.setVisible(true);
				lblLName.setVisible(true);
				btnYesDelete.setVisible(false);
				lblDelete.setVisible(false);
				btnDelete.setVisible(true);
				btnLogOut.setVisible(true);
				btnMakeAcct.setVisible(true);
				deleteAcct();
			}
		}	
	}
	
	private void deleteAcct() {
		
		File inputFile = new File("login.txt");
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
        String lineToRemove = panel.getAcct().toString();
        String currentLine;
        try {
            while((currentLine = reader.readLine()) != null)
            {
                if(currentLine.equals(lineToRemove)) {
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
    	boolean successful = tempFile.renameTo(inputFile);
    	panel.switchPanel("Login");
		
	}
}
