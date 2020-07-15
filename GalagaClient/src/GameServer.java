import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import java.net.*;

public class GameServer {
	private ServerSocket Gserver;
	private Socket Gsocket;
	private fmGameServerUI UI;
	private ClientSocket S;
	private int GamePort;
	private ArrayList<Socket> ConnectedList = new ArrayList<Socket>();
	public ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	public ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	int kill=0;
	Random random = new Random();

	public GameServer(int GamePort, ClientSocket S) {
		this.GamePort = GamePort;
		this.S = S;
	}

	public void SocketOpen() {
		try {

			UI = new fmGameServerUI(this.S);
			Gserver = new ServerSocket(GamePort);
			UI.Log("Server Open");
			new Confirm(Gsocket, ConnectedList, enemy, bullet,kill).start();
			new Enemyime1().start();
			new Enemyime2(Gsocket, ConnectedList, enemy, bullet,kill).start();
			
			while(true) {
				Gsocket = Gserver.accept();
				UI.Log("Server Accepted..");
				ConnectedList.add(Gsocket);
				new GameServerSocketThread(Gsocket, ConnectedList, UI, enemy, bullet).start();
			}

		} catch (Exception e) {

		}
	}

	class Enemyime1 extends Thread {
		public void run() {
			long nowTime, drawTime;

			nowTime = System.currentTimeMillis();
			drawTime = nowTime + 2000;

			while (true) {

				nowTime = System.currentTimeMillis();
				if (drawTime < nowTime) {
					drawTime = nowTime + 1000;// 10초마다 적생성

					if (enemy.size() <= 10) {
						Enemy E = new Enemy(random.nextInt(310) + 10, 35, 4);
						enemy.add(E);
						sendAll("createEnemy/" + E.pos.x + "/" + E.pos.y+"/"+E.HP);
					}
				}
			}
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

	
	

}