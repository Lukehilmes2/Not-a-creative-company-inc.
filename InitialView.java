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

	public InitialView(MainPanel panel) {
		
		this.panel = panel;
		btnDelete = new JButton("Delete Account");
		lblDelete = new JLabel("Are you sure you want to delte your account?");
		btnYesDelete = new JButton("Yes, delete account");
		btnNoDelete = new JButton("No");
		lblDelete.setVisible(false);
		btnYesDelete.setVisible(false);
		btnNoDelete.setVisible(false);
		btnDelete.addActionListener(new ButtonListener());
		btnNoDelete.addActionListener(new ButtonListener());
		btnYesDelete.addActionListener(new ButtonListener());
		add(btnDelete);
		add(lblDelete);
		add(btnYesDelete);
		add(btnNoDelete);
	}

	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent evt) {

			if (evt.getSource() == btnDelete) {
				
				btnDelete.setVisible(false);
				btnNoDelete.setVisible(true);
				btnYesDelete.setVisible(true);
				lblDelete.setVisible(true);
			}
			else if (evt.getSource() == btnNoDelete) {
			
				btnNoDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				lblDelete.setVisible(false);	
				btnDelete.setVisible(true);
			}
			else if(evt.getSource() == btnYesDelete) {
				
				btnNoDelete.setVisible(false);
				btnYesDelete.setVisible(false);
				lblDelete.setVisible(false);
				btnDelete.setVisible(true);
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
