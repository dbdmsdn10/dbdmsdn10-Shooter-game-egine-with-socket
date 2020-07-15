import javax.swing.*;

public class ServerThread extends Thread {
	private UserManager UM;
	private GameManager GM;
	private fmMain 		UI;
	
	public ServerThread(UserManager UM, GameManager GM, fmMain UI) {
		this.UM = UM;
		this.GM = GM;
		this.UI = UI;
	}
	public void run() {
		UM.SocketOpen();
		
	}
}
