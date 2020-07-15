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

	public static final String Connected 		= "CT"; 	//접속
	public static final String Data_UserList 	= "DU"; 	//유저리스트 전달
	public static final String Data_GameList 	= "DG"; 	//게임리스트 전달
	public static final String Create_Game 		= "CG"; 	//방 생성
	public static final String Enter_Game 		= "EG"; 	//방 입장
	public static final String Delete_Game 		= "DE"; 	//방 입장
	
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

	public void sendMsg(String msg) { //모든 클라이언트
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

	public void sendMsg(Socket s,String msg) { //특정 클라이언트
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
		//sendMsg("DC/"+UserName+"/"+UserName+"님이 나갔습니다.");	//클라이언트의 연결이 끊어질경우
		UserNameList.remove(UserName);									//유저들의 이름이 적힌 배열에서 이름을 제거한다.
		String listMsg = "";												//다시 리스트 작성
		for(int i = 0; i<UserNameList.size(); i++) {							
			listMsg += "/" + UserNameList.get(i);
		}
		sendMsg(Data_UserList+listMsg);												//리스트 작성후 클라이언트들에게 메세지 전송
		ConnectedList.remove(socket);													//이 소켓도 연결끊음.
		Log("SYSTEM> " + socket + "disconnect..");
	}

	public void switchMsg(String msg) {
		StringTokenizer tmsg = new StringTokenizer(msg, "/");	//메세지가 올 경우 "/"를 기준으로 토큰화 시킴
		String systemMsg = tmsg.nextToken();					//
		String nameMsg = tmsg.nextToken();						//유저의 이름
		String Msg = tmsg.nextToken();							//유저가 보낸 메세지
		UserName = nameMsg;
		String listMsg;
		
		Log("SYSTEM> getMessage>" + systemMsg + "..");
		
		switch(systemMsg) {
		
		case Connected:
			UserNameList.add(UserName);							//멤버리스트에 추가함 ->클라이언트들에게 현재 접속한 유저들의 목록을 주기 위함.
			listMsg = "";								
			for(int i = 0; i<UserNameList.size(); i++)		
				listMsg += "/" + UserNameList.get(i);				//String객체를 하나 만들어 그 안에다가 User1/User2 이런식으로 저장함.
			sendMsg(Data_UserList+listMsg);								//저장한후 String객체를 클라이언트에게 보냄
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
