import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Confirm extends Thread{
	public ArrayList<Enemy> enemy;
	public ArrayList<Bullet> bullet;
	Socket socket;
	ArrayList<Socket> ConnectedList;
	int kill;
	
	public Confirm(Socket socket, ArrayList<Socket> ConnectedList,ArrayList<Enemy> enemy,ArrayList<Bullet> bullet,int kill)
	{
		this.enemy=enemy;
		this.bullet=bullet;
		this.socket=socket;
		this.ConnectedList=ConnectedList;
		this.kill=kill;
	}
	public void run() {
		long nowTime, drawTime;

		nowTime = System.currentTimeMillis();
		drawTime = nowTime + 200;
		long how = drawTime;
		while (true) {
			nowTime = System.currentTimeMillis();
			if (drawTime < nowTime) {
				drawTime = nowTime + 10;// speed마다 움직이게하기
				try {
				for (int j = 0; j < bullet.size(); j++) {
					bullet.get(j).pos.y -= 15;
					
					for (int i = 0; i < enemy.size(); i++) {
						if (enemy.get(i).pos.y - 35 <= bullet.get(j).pos.y
								&& enemy.get(i).pos.y + 60 >= bullet.get(j).pos.y) {
							if (enemy.get(i).pos.x - 10 <= bullet.get(j).pos.x
									&& enemy.get(i).pos.x + 60 >= bullet.get(j).pos.x) {
								bullet.remove(j);
								enemy.get(i).HP--;
								
								if(enemy.get(i).HP<=0)
								{
									enemy.remove(i);
									kill++;
									sendAll("kill/"+kill);
								}
								sendAll("deleteall");
								for (int k = 0; k < bullet.size(); k++) {
									sendAll("createBullet/" + bullet.get(k).pos.x + "/" + bullet.get(k).pos.y);
									
								}
								for (int k = 0; k < enemy.size(); k++) {
									sendAll("createEnemy/" + enemy.get(k).pos.x + "/" + enemy.get(k).pos.y+"/"+enemy.get(k).HP);
									
								}
							}
						}
					}
					try {
					if(bullet.get(j).pos.y<0)
					{
						bullet.remove(j);
						
					}
					}catch(Exception e)
					{
						for(int c=0;c<bullet.size();c++)
						{
							if(bullet.get(c).pos.y<0)
							{
								bullet.remove(c);
								
							}
						}
					}
				}
			}catch(Exception e)
				{
				
				}
				sendAll("delete");
				for (int k = 0; k < bullet.size(); k++) {
					sendAll("createBullet/" + bullet.get(k).pos.x + "/" + bullet.get(k).pos.y);
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
