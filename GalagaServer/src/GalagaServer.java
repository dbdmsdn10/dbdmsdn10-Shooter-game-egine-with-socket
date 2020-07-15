
public class GalagaServer {
	/*
	 * 贸澜 矫累
	 */
	private static UserManager 	UM;
	private static GameManager 	GM;
	private static fmMain 		UI;
	
	public GalagaServer() {

	}
	public static void main(String[] args) {
		UI = new fmMain();									//UI积己
		GM = new GameManager();								//GM积己(GameManager)
		UM = new UserManager(UI, GM);						//UM积己(UserManager)
		
		Thread Server = new ServerThread(UM, GM, UI);		//
		Server.start();
	}
}
