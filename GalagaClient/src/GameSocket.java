import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;

public class GameSocket {
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private fmGame UI;
	private Game G;
	private String Airplane;


	public GameSocket(String Airplane) {
		this.Airplane = Airplane;
	}
	
	public void Connect(String IP, int GamePort) {
		System.out.println("Connect..");
		
		try {
			socket = new Socket(IP, GamePort);
			writer = new PrintWriter(socket.getOutputStream(), true);
			G = new Game();
			UI = new fmGame(this, G, Airplane);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				StringTokenizer tmsg = new StringTokenizer(reader.readLine(), "/");
				String ptMsg = tmsg.nextToken();
				
				if(ptMsg.equals("delete")) {
					G.bullet.removeAll(G.bullet);
				}
				if(ptMsg.equals("deleteall")) {
					G.bullet.removeAll(G.bullet);
					G.enemy.removeAll(G.enemy);
				}
				else if(ptMsg.equals("pos"))
				{
					G.createTeam(tmsg.nextToken(), Integer.valueOf(tmsg.nextToken()), Integer.valueOf(tmsg.nextToken()));
				}
				else if(ptMsg.equals("createEnemy"))
				{
					G.createEnemy(Integer.valueOf(tmsg.nextToken()), Integer.valueOf(tmsg.nextToken()),Integer.valueOf(tmsg.nextToken()));
					//writer.println("createEnemy/complete");
				}
				else if(ptMsg.equals("posEnemy"))
				{
					G.posEnemy(Integer.valueOf(tmsg.nextToken()), Integer.valueOf(tmsg.nextToken()),Integer.valueOf(tmsg.nextToken()));
				}
				else if(ptMsg.equals("createBullet"))
				{
					G.createBullet(Integer.valueOf(tmsg.nextToken()), Integer.valueOf(tmsg.nextToken()));
					//writer.println("createEnemy/complete");
				}
				else if(ptMsg.equals("posBullet"))
				{
					G.posBullet(Integer.valueOf(tmsg.nextToken()), Integer.valueOf(tmsg.nextToken()));
				}
				else if(ptMsg.equals("kill"))
				{
					G.kill=Integer.valueOf(tmsg.nextToken());
				}
				else if(ptMsg.equals("life"))
				{
					G.life=Integer.valueOf(tmsg.nextToken());
				}
				// UI.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Sendpos() {
		writer.println("pos/" + Airplane + "/" + UI.pos.x + "/" + UI.pos.y);
	}
	public void SendBullet() {
		writer.println(UI.key[4]+"/" + UI.pos.x + "/" + UI.pos.y);
	}
}
