import javax.swing.*;
public class ClientThread extends Thread{
	private ClientSocket CS;
	
	public ClientThread(ClientSocket CS) {
		this.CS = CS;
	}
	public void run() {
		CS.Connect();
	}
}
