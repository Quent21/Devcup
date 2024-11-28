import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private ArrayList<Pion> pions;
	private Color color;
	private int energie;
	
	public Player(Color color) {
		this.pions = new ArrayList<>();
		this.color = color;
		this.energie = 200;
	}
	
	public void addPion(Pion pion) {
		this.pions.add(pion);
		pion.setPlayer(this);
	}
	
	public void removePion(Pion pion) {
		pions.remove(pion);
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getEnergie() {
		return energie;
	}
	
	public boolean isDead() {
		return pions.size() == 0;
	}
	
	public void useEnergie(int energie) {
		if (energie > this.energie) {
			this.energie = 0;
		} else {
			this.energie -= energie;
		}
	}
}
