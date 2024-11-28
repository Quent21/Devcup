import java.awt.Color;
import java.awt.Graphics;

public class Hole implements Item {
	
	public Hole() {
		
	}
	
	public void draw(Graphics g, int x, int y, int dx, int dy) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, dx, dy);
	}
}
