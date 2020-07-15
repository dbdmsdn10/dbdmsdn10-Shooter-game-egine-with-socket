import java.awt.Point;

public class Team {
	Point pos;
	String Img;

	public Team(String Img, int x, int y) {
		this.pos = new Point(x, y);
		this.Img = Img;
	}
}
