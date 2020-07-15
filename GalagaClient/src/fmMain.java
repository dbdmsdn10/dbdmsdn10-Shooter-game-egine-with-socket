import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Window.Type;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.util.*;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
/*
 * 										##Client fmMain##
 */

public class fmMain extends JFrame{
	private static JList lstUserList;
	private static JList lstGameList;
	private static JList lstAirplane;
	private ClientSocket CS;
	private final Map<String, ImageIcon> imageMap;
	private String IP;
	
	public fmMain(String UserName, String IP) {
		setResizable(false);

		setSize(600,530);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lstGameList = new JList();
		lstGameList.setBackground(Color.WHITE);
		lstGameList.setBounds(14, 43, 200, 400);
		getContentPane().add(lstGameList);

		lstUserList = new JList();
		lstUserList.setBounds(228, 43, 200, 400);
		getContentPane().add(lstUserList);

		JButton btCreateGame = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
		btCreateGame.setBounds(442, 40, 138, 27);
		getContentPane().add(btCreateGame);

		JButton btEnterGame = new JButton("\uBC29 \uC785\uC7A5");
		btEnterGame.setBounds(442, 79, 138, 27);
		getContentPane().add(btEnterGame);

		JMenuBar mnbSetting = new JMenuBar();
		setJMenuBar(mnbSetting);

		JMenu mnSetting = new JMenu("Setting");
		mnbSetting.add(mnSetting);
		setVisible(true);

		
		JLabel lblNewLabel = new JLabel("\uAC8C\uC784 \uB9AC\uC2A4\uD2B8");
		lblNewLabel.setBounds(14, 13, 88, 18);
		getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("\uC720\uC800 \uB9AC\uC2A4\uD2B8");
		label.setBounds(228, 13, 88, 18);
		getContentPane().add(label);
		
		
		String[] nameList = {"Kim", "Yu", "Rika"};
		imageMap = createImageMap(nameList);
		lstAirplane = new JList(nameList);
		lstAirplane.setCellRenderer(new AirplaneListRenderer());
		
		
		//JScrollPane scroll = new JScrollPane(lstAirplane);
		//scroll.setPreferredSize(new Dimension(300, 400));
		lstAirplane.setBounds(442, 278, 138, 165);
		getContentPane().add(lstAirplane);
		
		JLabel label_1 = new JLabel("\uBE44\uD589\uAE30 \uB9AC\uC2A4\uD2B8");
		label_1.setBounds(442, 248, 103, 18);
		getContentPane().add(label_1);
		
		CS = new ClientSocket(lstUserList, lstGameList, UserName, IP);
		Thread SocketThread = new ClientThread(CS);

		SocketThread.start();
		
		btCreateGame.addActionListener(new actCreateGame());
		btEnterGame.addActionListener(new actEnterGame());
	}



	public class AirplaneListRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);
			label.setIcon(imageMap.get((String) value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			return label;
		}
	}

	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			map.put("Kim", new ImageIcon(getToolkit().getImage("비행기1.png")));
			map.put("Yu", new ImageIcon(getToolkit().getImage("비행기2.png")));
			map.put("Rika", new ImageIcon(getToolkit().getImage("비행기3.png")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	class actCreateGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CS.CreateGame();
			System.out.println("fmMain CreateGame..");
			
		}
	}

	class actEnterGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			
			if(lstAirplane.getSelectedIndex() != -1||lstAirplane.getSelectedIndex()!=-1) {
				CS.EnterGame(String.valueOf(lstGameList.getSelectedValue()), String.valueOf(lstAirplane.getSelectedIndex()+1));
			}
			
		}
	}
}
