import java.awt.Graphics;

public class Pion implements Item {
	private Player player;
	private int pv;
	
	public Pion(Player player) {
//		this.player = player;
		player.addPion(this);
		this.pv = 50;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getPv() {
		return pv;
	}
	
	public boolean attaque(int damage) {
		pv -= damage;
		return pv <= 0;
	}
	
	public void kill() {
		player.removePion(this);
	}
	
	public void draw(Graphics g, int x, int y, int dx, int dy) {
		g.setColor(player.getColor());
		g.fillOval(x+dx/4, y+dy/4, dx/2, dy/2);
	}
}
