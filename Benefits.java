import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Benefits extends JPanel {

	private JComboBox<String> jobTypes;
	String[] strJobTypes = {"job type:", "student", "faculty"};
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
		jobTypes = new JComboBox<String>(strJobTypes);
		btnCalculate = new JButton("Calculate benefits");
		btnBack = new JButton("Back");
		lblAmount = new JLabel("");
		txtAmount = new JTextField(txtLength);
		lblTitle = new JLabel("Calculate Benefits");
		lblTxtAmount = new JLabel("Amount: ");
		btnCalculate.addActionListener(new ButtonListener());
		btnBack.addActionListener(new ButtonListener());
		add(lblTitle);
		add(btnBack);
		add(jobTypes);
		add(lblTxtAmount);
		add(txtAmount);
		add(btnCalculate);
		add(lblAmount);
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
				if (type.equals("student")) {
					benefits = amount*studentBenefit;
				}
				else if(type.equals("faculty")) {
					benefits = amount*facultyBenefit;
				}
				else {
					lblAmount.setText("Please give a valid job type in the dropdown menu");
					return;
				}
				lblAmount.setText("amount: " + fmt.format(amount) + " benefits: " + fmt.format(benefits));
			}
		}
		
	}
}
