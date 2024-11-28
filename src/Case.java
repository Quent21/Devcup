import java.awt.Color;
import java.awt.Graphics;

public class Case {
	private Item item;
	private boolean selected;
	
	public Case() {
		item = null;
		selected = false;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Item clean() {
		Item item = this.item;
		this.item = null;
		return item;
	}
	
	public void draw(Graphics g, int x, int y, int dx, int dy) {
		if (selected) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, dx, dy);
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, dx, dy);
		if (item != null) {
			item.draw(g, x, y, dx, dy);
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isEmpty() {
		return item == null;
	}
	
	public boolean isPion() {
		return item instanceof Pion;
	}
	
	public boolean isHole() {
		return item instanceof Hole;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
