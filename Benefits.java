import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Benefits extends JPanel {

	private JComboBox<String> jobTypes;
	String[] strJobTypes = {"job type:", "Student", "Faculty"};
	private JButton btnCalculate;
	private JTextField txtAmount;
	private JLabel lblAmount;
	private JLabel lblTitle;
	private JButton btnBack;
	private JLabel lblTxtAmount;
	private final int txtLength = 5;
	private final double studentBenefit = .12;
	private final double facultyBenefit = .25;
	private MainPanel panel;
	private DecimalFormat fmt = new DecimalFormat("$#.00");
	public Benefits(MainPanel panel) {

		this.panel = panel;
		JPanel panel1 = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		jobTypes = new JComboBox<String>(strJobTypes);
		btnCalculate = new JButton("Calculate benefits");
		btnBack = new JButton("Back");
		lblAmount = new JLabel("");
		txtAmount = new JTextField(20);
		lblTitle = new JLabel("Calculate Benefits");
		lblTxtAmount = new JLabel("Amount: ");
		btnCalculate.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 1;
		cs.ipady = 50;
		panel1.add(lblTitle, cs);

		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 1;
		cs.ipady = 35;
		panel1.add(btnBack, cs);

		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 1;
		cs.ipady = 20;
		panel1.add(jobTypes, cs);

		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 10;
		panel1.add(lblTxtAmount, cs);

		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 1;
		cs.ipady = 10;
		panel1.add(txtAmount, cs);

		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 1;
		cs.ipady = 50;
		panel1.add(btnCalculate, cs);

		cs.gridx = 1;
		cs.gridy = 5;
		cs.gridwidth = 1;
		cs.ipady = 50;
		panel1.add(lblAmount, cs);
		add(panel1,BorderLayout.CENTER);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnBack) {

				txtAmount.setText("");
				lblAmount.setText("");
				panel.switchPanel("InitialView");
			}
			else if (e.getSource() == btnCalculate) {

				double amount = Double.parseDouble(txtAmount.getText());
				double benefits = 0;
				String type = jobTypes.getSelectedItem().toString();
				if (type.equals("Student")) {
					benefits = amount*studentBenefit;
				}
				else if(type.equals("Faculty")) {
					benefits = amount*facultyBenefit;
				}
				else {
					lblAmount.setText("Please give a valid job type in the dropdown menu");
					return;
				}
				lblAmount.setText("Amount: " + fmt.format(amount) + " Benefits: " + fmt.format(benefits));
			}
		}

	}
}
