import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Enemyime2 extends Thread {
	int speed = 100;
	Socket socket;
	ArrayList<Socket> ConnectedList;
	ArrayList<Enemy> enemy;
	ArrayList<Bullet> bullet;
	int life=10;
	int kill;
	public Enemyime2(Socket socket, ArrayList<Socket> ConnectedList, ArrayList<Enemy> enemy, ArrayList<Bullet> bullet,int kill) {
		this.socket = socket;
		this.ConnectedList = ConnectedList;
		this.enemy=enemy;
		this.bullet=bullet;
		this.kill=kill;
	}

	public void run() {
		long nowTime, drawTime;

		nowTime = System.currentTimeMillis();
		drawTime = nowTime + 2000;
		long how = drawTime;
		while (true) {
			nowTime = System.currentTimeMillis();
			if (drawTime < nowTime) {
				drawTime = nowTime + speed;// speed���� �����̰��ϱ�
				if (how + 10000 < nowTime) {// 10������������ -20
					how = nowTime;
					if (speed < 40) {
						if (speed < 10) {// �ӵ��� 20�Ǹ� -5��
						} else {
							speed -= 5;
						}
					} else {
						speed -= 20;
					}
				}
				try {
				for (int i = 0; i < enemy.size(); i++) {// ��ǥ���ٲٰ� reprint�ϰ��ϱ�
					enemy.get(i).pos.y += 4;
					sendAll("posEnemy/" + i + "/" + enemy.get(i).pos.y + "/" + enemy.get(i).HP);
					if (enemy.get(i).pos.y > 900) {
						enemy.remove(i);
						life--;
						sendAll("life/"+life);
					}
					try {
						if (enemy.get(i).HP <= 0) {
							enemy.remove(i);
							kill++;
							sendAll("kill/"+kill);
						}
					} catch (Exception e) {
						for (int k = 0; k < enemy.size(); k++) {
							if (enemy.get(k).HP <= 0) {
								enemy.remove(k);
								kill++;
								sendAll("kill/"+kill);
							}
						}
					}

				}
			}catch(Exception e)
				{
				enemy.remove(enemy);
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
