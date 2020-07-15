import java.net.*;
import java.util.*;
public class UserManager {

	private ServerSocket server;
	private Socket socket;
	private GameManager GM;
	private ArrayList <Socket> ConnectedList = new ArrayList<Socket>();
	private ArrayList <String> UserNameList = new ArrayList<String>();
	private fmMain UI;

	public UserManager(fmMain UI, GameManager GM) {
		this.UI = UI;
		this.GM = GM;
	}

	public void SocketOpen() {

		try {
			server = new ServerSocket(70);
			UI.Log("SYSTEM> ServerSocket open..");
			while(true) {
				socket = server.accept();
				UI.Log("SYSTEM> Connected..");
				ConnectedList.add(socket);
				new UserThread(new User(socket, ConnectedList, UserNameList, GM, UI)).start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
