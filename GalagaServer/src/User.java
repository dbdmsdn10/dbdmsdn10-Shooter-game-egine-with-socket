import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class User {
	private Socket 				socket;
	private fmMain				UI;
	private ArrayList <Socket> 	ConnectedList;
	private ArrayList <Game>	GameList;
	private ArrayList <String>	UserNameList;
	private ArrayList <String>	GameNameList;
	private String				UserName;
	private GameManager			GM;

	public static final String Connected 		= "CT"; 	//����
	public static final String Data_UserList 	= "DU"; 	//��������Ʈ ����
	public static final String Data_GameList 	= "DG"; 	//���Ӹ���Ʈ ����
	public static final String Create_Game 		= "CG"; 	//�� ����
	public static final String Enter_Game 		= "EG"; 	//�� ����
	public static final String Delete_Game 		= "DE"; 	//�� ����
	
	public User(Socket socket, ArrayList<Socket> ConnectedList, ArrayList<String> UserNameList, GameManager GM, fmMain UI) {
		this.socket = socket;
		this.ConnectedList = ConnectedList;
		this.GameList = GM.getGameList();
		this.UserNameList = UserNameList;
		this.GameNameList = GM.getGameNameList();
		this.UI = UI;
		this.GM = GM;
	}

	public void getStream() {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Log("SYSTEM> Open BufferedReader..");

			while(true)
				switchMsg(r.readLine());

		}catch(Exception e) {
			e.printStackTrace();
			disConnect();
		}
	}

	public void sendMsg(String msg) { //��� Ŭ���̾�Ʈ
		try {
			for(Socket s:ConnectedList) {
				PrintWriter w = new PrintWriter(s.getOutputStream(), true);
				w.println(msg);
				Log("SYSTEM> " + s + "<<<" + msg + "..");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendMsg(Socket s,String msg) { //Ư�� Ŭ���̾�Ʈ
		try {
			PrintWriter w = new PrintWriter(s.getOutputStream(), true);
			w.println(msg);
			Log("SYSTEM> " + s + "<<<" + msg + "..");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void Log(String content) {
		UI.Log(content);
	}

	public void disConnect( ) {
		//sendMsg("DC/"+UserName+"/"+UserName+"���� �������ϴ�.");	//Ŭ���̾�Ʈ�� ������ ���������
		UserNameList.remove(UserName);									//�������� �̸��� ���� �迭���� �̸��� �����Ѵ�.
		String listMsg = "";												//�ٽ� ����Ʈ �ۼ�
		for(int i = 0; i<UserNameList.size(); i++) {							
			listMsg += "/" + UserNameList.get(i);
		}
		sendMsg(Data_UserList+listMsg);												//����Ʈ �ۼ��� Ŭ���̾�Ʈ�鿡�� �޼��� ����
		ConnectedList.remove(socket);													//�� ���ϵ� �������.
		Log("SYSTEM> " + socket + "disconnect..");
	}

	public void switchMsg(String msg) {
		StringTokenizer tmsg = new StringTokenizer(msg, "/");	//�޼����� �� ��� "/"�� �������� ��ūȭ ��Ŵ
		String systemMsg = tmsg.nextToken();					//
		String nameMsg = tmsg.nextToken();						//������ �̸�
		String Msg = tmsg.nextToken();							//������ ���� �޼���
		UserName = nameMsg;
		String listMsg;
		
		Log("SYSTEM> getMessage>" + systemMsg + "..");
		
		switch(systemMsg) {
		
		case Connected:
			UserNameList.add(UserName);							//�������Ʈ�� �߰��� ->Ŭ���̾�Ʈ�鿡�� ���� ������ �������� ����� �ֱ� ����.
			listMsg = "";								
			for(int i = 0; i<UserNameList.size(); i++)		
				listMsg += "/" + UserNameList.get(i);				//String��ü�� �ϳ� ����� �� �ȿ��ٰ� User1/User2 �̷������� ������.
			sendMsg(Data_UserList+listMsg);								//�������� String��ü�� Ŭ���̾�Ʈ���� ����
			listMsg = "";
			for(int i = 0; i<GameNameList.size(); i++)		
				listMsg += "/" + GameNameList.get(i);
			sendMsg(Data_GameList+listMsg);
			break;
			
		case Create_Game:
			Game G = GM.CreateGame(Msg);

			listMsg = "";
			for(int i = 0; i<GameNameList.size(); i++)		
				listMsg += "/" + GameNameList.get(i);
			sendMsg(Data_GameList+listMsg);
			sendMsg(socket, Create_Game+"/"+G.getPort());
			break;
			
		case Enter_Game:
			sendMsg(socket, Enter_Game+"/"+GM.EnterGame(Msg));
			//UI.Log(String.valueOf(socket) + Enter_Game+"/"+GM.EnterGame(Msg));
			break;
			
		case Delete_Game:
			GM.DeleteGame(Msg);
			listMsg = "";
			for(int i = 0; i<GameNameList.size(); i++)		
				listMsg += "/" + GameNameList.get(i);
			sendMsg(Data_GameList+listMsg);
			//UI.Log(String.valueOf(socket) + Enter_Game+"/"+GM.EnterGame(Msg));
			break;
			
		default :
			break;
		}
	}
}
