
public class GameServerThread extends Thread{
	private GameServer GS;
	
	public GameServerThread(GameServer GS) {
		this.GS = GS;
	}
	
	public void run() {
		GS.SocketOpen();
	}
}
