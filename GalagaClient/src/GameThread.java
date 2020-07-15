public class GameThread extends Thread{
	private GameSocket GS;
	private int GamePort;
	private String IP;
	
	public GameThread(GameSocket GS, String IP, int GamePort) {
		this.GS = GS;
		this.GamePort = GamePort;
		this.IP = IP;
	}
	public void run() {
		GS.Connect(IP, GamePort);
		System.out.print("thread run..\n");
	}
}
