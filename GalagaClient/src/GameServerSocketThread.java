
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import java.awt.*;

public class GameServerSocketThread extends Thread {
	private Socket socket;
	private Socket TeamSocket;
	private ArrayList<Socket> ConnectedList;
	private fmGameServerUI UI;
	private PrintWriter writer;
	public ArrayList<Enemy> enemy;
	public ArrayList<Bullet> bullet;
	Random random = new Random();

	int speed = 100;

	public GameServerSocketThread(Socket socket, ArrayList<Socket> ConnectedList, fmGameServerUI UI,
			ArrayList<Enemy> enemy, ArrayList<Bullet> bullet) {
		this.socket = socket;
		this.ConnectedList = ConnectedList;
		this.UI = UI;
		this.enemy = enemy;
		this.bullet = bullet;
	}

	public void initEnemy() {
		for (int i = 0; i < enemy.size(); i++) {
			send(socket, "createEnemy/" + enemy.get(i).pos.x + "/" + enemy.get(i).pos.y + "/" + enemy.get(i).HP);
		}
	}

	public void run() {
		try {

			BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			initEnemy();

			while (true) {

				String msg = r.readLine();
				// UI.Log(msg);
				StringTokenizer confirm = new StringTokenizer(msg, "/");
				String what = confirm.nextToken();
				if (what.equals("0")) {
				} else if (what.equals("1")) {
					int a = Integer.valueOf(confirm.nextToken()) + 25;
					int b = Integer.valueOf(confirm.nextToken()) - 10;
					
					bullet.add(new Bullet(a,b));
					
				}
				
				for (Socket s : ConnectedList) {
					if (s != this.socket) {
						send(s, msg);
					}
				}
			}

		} catch (Exception e) {

		}

	}

	public void send(Socket s, String Msg) {
		try {
			new PrintWriter(s.getOutputStream(), true).println(Msg);
			// UI.Log(Msg);
		} catch (Exception e) {

		}

	}

	public void sendAll(String Msg) {

		try {
			for (Socket s : ConnectedList) {

				new PrintWriter(s.getOutputStream(), true).println(Msg);
				// UI.Log(Msg);
			}
		} catch (Exception e) {

		}
	}

	public void SetTeamSocket() {

		for (Socket s : ConnectedList) {

			if (s == this.socket) {
				System.out.println("ME : " + s);
			}
			if (s != this.socket) {
				System.out.println("TM : " + s);
				TeamSocket = s;
			}
		}
	}

	
}
