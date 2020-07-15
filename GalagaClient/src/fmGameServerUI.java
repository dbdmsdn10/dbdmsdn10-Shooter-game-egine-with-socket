import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.event.*;


public class fmGameServerUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea txtGameLog;

	public fmGameServerUI(ClientSocket S) {
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 12, 654, 431);
		getContentPane().add(scrollPane);

		txtGameLog = new JTextArea();
		txtGameLog.setEditable(false);
		txtGameLog.setLineWrap(true);
		scrollPane.setViewportView(txtGameLog);

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) { 
				S.DeleteGame();
				System.exit(0);
			}
		});
		setSize(700,500);
		setVisible(true);

	}

	public void Log(String Msg) {
		txtGameLog.append(Msg+"\n");
		txtGameLog.setCaretPosition(txtGameLog.getDocument().getLength());
	}

}
