import java.awt.Point;

public class Enemy {
	Point pos;
	int HP;

	public Enemy(int x, int y, int HP) {
		this.pos = new Point(x, y);
		this.HP = HP;
	}
}
