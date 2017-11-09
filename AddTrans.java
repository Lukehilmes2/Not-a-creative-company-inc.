import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddTrans extends JPanel {

	private JLabel lblAmount, lblDescription, lblAdd;
	private JTextField txtAmount, txtDescription;
	private JButton btnAddTrans, btnBack;
	private int txtFieldLength = 20;
	private MainPanel panel;
	public AddTrans(MainPanel panel) {

		this.panel = panel;

		JPanel panel1 = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		lblAdd = new JLabel("Add Transaction: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAdd, cs);

		lblAmount = new JLabel("Amount: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblAmount, cs);
		txtAmount = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtAmount, cs);

		lblDescription = new JLabel("Description: ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(lblDescription, cs);
		txtDescription = new JTextField(txtFieldLength);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(txtDescription, cs);

		btnAddTrans = new JButton("Add Transaction");
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(btnAddTrans, cs);

		btnBack = new JButton("Back");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel2.add(btnBack, cs);
		btnAddTrans.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.NORTH);

	}

	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnBack) {
				panel.switchPanel("ViewAcct");
			}
			else if (e.getSource() == btnAddTrans) {
				addTrans();
				panel.switchPanel("ViewAcct");
			}
		}
	}

	private void addTrans() {

		panel.addLine("transactions/" + panel.getAcct().getName() + ".txt", Double.parseDouble(txtAmount.getText()) + "," + txtDescription.getText());
		panel.updateTrans();
	}
}
