import java.util.*;
public class GameManager {
	
	private ArrayList<String> 	GameNameList = new ArrayList<String>();
	private ArrayList<Game> 	GameList = new ArrayList<Game>(); 
	
	private int 				GamePort = 100;
	
	public GameManager() {
		
	}
	
	public ArrayList<String> getGameNameList() {
		return GameNameList;
	}
	
	public ArrayList<Game> getGameList() {
		return GameList;
	}
	
	public Game CreateGame(String IP) {
		Game G = new Game(IP, ++GamePort);
		GameList.add(G);
		GameNameList.add(IP);
		
		return G;
	}
	
	public String EnterGame(String IP) {
		for(Game G:GameList) {
			if(IP.equals(G.getIP()))
				return G.getIP() + "/" + G.getPort();
		}
		return "false";
	}
	
	public void DeleteGame(String Msg) {
		GameNameList.remove(Msg);
		for(Game G:GameList) {
			if(Msg.equals(G.getIP()))
				GameList.remove(G);
		}
	}
}
