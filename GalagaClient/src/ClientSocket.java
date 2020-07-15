import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Random;
import javax.swing.*;
import java.net.InetAddress; 


public class ClientSocket {
	private String			 UserName;
	private Socket			 socket;
	private PrintWriter		 writer;
	private BufferedReader	 reader;
	private String			 state;
	private String			 IP;
	private String			 Airplane;
	

	public static final String Connected 		= "CT"; 	//접속
	public static final String Data_UserList 	= "DU"; 	//유저리스트 전달
	public static final String Data_GameList 	= "DG"; 	//게임리스트 전달
	public static final String Create_Game 		= "CG"; 	//방 생성
	public static final String Enter_Game 		= "EG"; 	//방 입장
	public static final String Delete_Game 		= "DE"; 	//방 삭제
	
	private JList lstUserList;
	private JList lstGameList;
	
	public ClientSocket(JList lstUserList, JList lstGameList, String UserName, String IP) {
		this.lstUserList = lstUserList;
		this.lstGameList = lstGameList;
		this.UserName = UserName;
		this.IP = IP;
	}

	public void Connect() {
		try {
			socket = new Socket(IP, 70);
			System.out.println("Connected..");
			writer = new PrintWriter(socket.getOutputStream(), true);
			sendMsg(Msg(Connected, UserName, "입장"));		//서버에게 입장했다고 메세지전송
			state = "RestRoom";

			System.out.println("Send..");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				StringTokenizer tmsg = new StringTokenizer(reader.readLine(), "/");
				String systemMsg = tmsg.nextToken();

				switchMsg(tmsg, systemMsg);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error\n");
		}
	}

	public void switchMsg(StringTokenizer tmsg, String systemMsg) {
		switch(systemMsg) {

		case Data_UserList:
			String[] UserList = new String[tmsg.countTokens()];
			int i=0;
			while(tmsg.hasMoreTokens()) {
				UserList[i] = tmsg.nextToken();
				System.out.println(UserList[i]);
				i++;
			}
			lstUserList.setListData(UserList);
			break;
			
		case Create_Game:
			state = "Game";
			try { 
				Thread serverthread = new GameServerThread(new GameServer(Integer.valueOf(tmsg.nextToken()), this));
				serverthread.start();
			} catch (Exception e) { 
				System.out.println(e); 
			} 
			
			break;
		case Data_GameList:
			String[] GameList = new String[tmsg.countTokens()];
			int g=0;
			while(tmsg.hasMoreTokens()) {
				GameList[g++] = tmsg.nextToken();
				System.out.println(GameList[g-1]);
			}
			lstGameList.setListData(GameList);
			break;
			
		case Enter_Game:
			state = "Game";
			
			//new fmGame(tmsg.nextToken(), Integer.valueOf(tmsg.nextToken()));
			new GameThread(new GameSocket(Airplane), tmsg.nextToken(), Integer.valueOf(tmsg.nextToken())).start();;
		default:
			break;
		}
	}

	public String Msg(String Protocol, String UserName, String Message) {
		return Protocol + "/" + UserName + "/" + Message;
	}

	public void sendMsg(String msg) {
		writer.println(msg);
		System.out.println(msg);
	}

	public void CreateGame() {
		try { 
			InetAddress ip = InetAddress.getLocalHost();
			sendMsg(Msg(Create_Game, UserName, ip.getHostAddress()));
		} catch (Exception e) { 
			System.out.println(e); 
		} 
		
	}
	
	public void EnterGame(String GameName, String Airplane) {
		sendMsg(Msg(Enter_Game, UserName, GameName));
		this.Airplane = Airplane;
	}
	
	public void DeleteGame() {
		try { 
			InetAddress ip = InetAddress.getLocalHost();
			sendMsg(Msg(Delete_Game, UserName, ip.getHostAddress()));
		} catch (Exception e) { 
			System.out.println(e); 
		} 
	}
}
