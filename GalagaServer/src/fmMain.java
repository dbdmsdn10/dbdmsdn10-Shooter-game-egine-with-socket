import javax.swing.*;
import java.awt.*;
/*
 * 										##Server fmMain##
 */


public class fmMain extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static JTextArea txtLog;
	
	public fmMain() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 12, 654, 431);
		getContentPane().add(scrollPane);
		
		txtLog = new JTextArea();
		txtLog.setFont(new Font("Gisha", Font.PLAIN, 13));
		txtLog.setEditable(false);
		txtLog.setLineWrap(true);
		
		scrollPane.setViewportView(txtLog);
		setSize(700,500);
		setVisible(true);
	}
	
	public void Log(String Msg) {
		txtLog.append(Msg+"\n");
		txtLog.setCaretPosition(txtLog.getDocument().getLength());
	}

}
