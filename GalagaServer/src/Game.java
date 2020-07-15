
public class Game {
	
	private String 	GameIP;
	private int 	GamePort;

	public Game(String GameIP, int GamePort) {
		this.GameIP 	= GameIP;
		this.GamePort	= GamePort;
	}
	
	public int getPort() {
		return GamePort;
	}
	
	public String getIP() {
		return GameIP;
	}
}
