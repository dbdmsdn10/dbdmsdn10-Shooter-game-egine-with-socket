
public class GalagaServer {
	/*
	 * ó�� ����
	 */
	private static UserManager 	UM;
	private static GameManager 	GM;
	private static fmMain 		UI;
	
	public GalagaServer() {

	}
	public static void main(String[] args) {
		UI = new fmMain();									//UI����
		GM = new GameManager();								//GM����(GameManager)
		UM = new UserManager(UI, GM);						//UM����(UserManager)
		
		Thread Server = new ServerThread(UM, GM, UI);		//
		Server.start();
	}
}
