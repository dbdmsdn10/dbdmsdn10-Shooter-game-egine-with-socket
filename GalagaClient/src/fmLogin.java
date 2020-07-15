import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class fmLogin extends JFrame{
	private JTextField txtID;
	private JTextField txtIP;

	public fmLogin() {
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		txtID = new JTextField();
		txtID.setText("USER");
		txtID.setBounds(14, 12, 136, 24);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JButton btLogin = new JButton("Login");
		btLogin.setBounds(164, 12, 116, 53);
		getContentPane().add(btLogin);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setColumns(10);
		txtIP.setBounds(14, 41, 136, 24);
		getContentPane().add(txtIP);

		setSize(300,110);
		setVisible(true);

		btLogin.addActionListener(new actLogin());
	}

	public static void main(String[] args) {
		new fmLogin();

	}

	class actLogin implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!txtID.getText().equals("")) {

				fmMain M = new fmMain(txtID.getText(), txtIP.getText());
				dispose();
			}
		}
	}
}
