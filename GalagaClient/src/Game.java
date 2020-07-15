import java.util.ArrayList;

public class Game {
	
	Team team = new Team("img1", 190, 830);
	ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	int kill=0;
	int life=10;
	public void createEnemy(int x, int y,int hp) {
		enemy.add(new Enemy(x,y, hp));
	}
	
	public void posEnemy(int i, int y,int hp) {
		try {
			enemy.set(i, new Enemy(enemy.get(i).pos.x, y, hp));
			if(y>900)
			{
				enemy.remove(i);
			}
			else if(hp<=0)
			{
				enemy.remove(i);
			}
		} catch(Exception e) {
		
		}
		
		
	}
	public void createBullet(int x, int y) {
		bullet.add(new Bullet(x,y));
	}
	
	public void posBullet(int i, int y) {
		try {
			bullet.set(i, new Bullet(bullet.get(i).pos.x, y));
			if(y<0)
			{
				bullet.remove(i);
			}
			
		} catch(Exception e) {
			
			
		}
		
		
	}
	
	public void createTeam(String c, int x, int y) {
		this.team.Img = c;
		this.team.pos.x = x;
		this.team.pos.y = y;
	}
}
