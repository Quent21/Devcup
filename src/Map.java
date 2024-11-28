import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Map extends JPanel implements MouseListener {
	private int width;
	private int height;
	private int nbWidth;
	private int nbHeight;
	private Case[][] map;
	private Player[] players;
	private int player;
	private int selectedX;
	private int selectedY;
	private Case selected;
	private Fenetre fen ;
	
	public Map(int width, int height, int nbWidth, int nbHeight, Fenetre fen) {
		this.width = width;
		this.height = height;
		this.nbWidth = nbWidth;
		this.nbHeight = nbHeight;
		this.fen = fen;
		map = new Case[nbWidth][nbHeight];
		for (int i = 0; i < nbWidth; i++) {
			for (int j = 0; j < nbHeight; j++) {
				map[i][j] = new Case();
			}
		}
		players = new Player[4];
		Color[] colors = new Color[] {
				Color.RED,
				Color.BLUE,
				Color.GREEN,
				Color.YELLOW};
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(colors[i]);
		}
		player = 0;
		selectedX = 0;
		selectedY = 0;
		selected = null;
		
		map[4][4].setItem(new Hole());
		map[5][4].setItem(new Hole());
		map[4][5].setItem(new Hole());
		map[5][5].setItem(new Hole());
		map[0][0].setItem(new Hole());
		map[nbWidth - 1][0].setItem(new Hole());
		map[0][nbHeight - 1].setItem(new Hole());
		map[nbWidth - 1][nbHeight - 1].setItem(new Hole());
		
		map[1][1].setItem(new Pion(players[0]));
		map[nbWidth - 2][1].setItem(new Pion(players[1]));
		map[nbWidth - 2][nbHeight - 2].setItem(new Pion(players[2]));
		map[1][nbHeight - 2].setItem(new Pion(players[3]));
		
		addMouseListener(this);
	}
	
	public int getStartX() {
		return 0;
	}
	
	public int getStartY() {
		return getHeight() / 2 - height / 2;
	}
	
	public Player getPlayer(int n) {
		return players[n];
	}
	
	public int getIndex(Player player) {
		for (int i = 0; i < 4; i++) {
			if (players[i] == player)
				return i;
		}
		return -1;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int dx = width / nbWidth;
		int dy = height / nbHeight;
		g.setColor(Color.WHITE);
		g.fillRect(getStartX(), getStartY(), width, height);
		for (int i = 0; i < nbWidth; i++) {
			for (int j = 0; j < nbHeight; j++) {
				map[i][j].draw(g, getStartX() + i * dx, getStartY() + j * dy, dx, dy);
			}
		}
	}
	
	public void select(int x, int y) {
		Pion pion = (Pion) map[x][y].getItem();
		if (!fen.getPlayer().equals(pion.getPlayer()))
			return;
		if (selected != null) {
			selected.setSelected(false);
		}
		selectedX = x;
		selectedY = y;
		selected = map[x][y];
		selected.setSelected(true);
		this.fen.showPanneau((Pion) map[x][y].getItem());
	}
	
	public void unselect() {
		if (selected != null) {
			selected.setSelected(false);
			selected = null;
			this.fen.hidePanneau();
		}
	}
	
	public boolean move(int x1, int y1, int x2, int y2) {
		Pion pion1 = (Pion) map[x1][y1].getItem();
		
		if (pion1.getPlayer().getEnergie() < Panneau.coutDeplacement)
			return false;
		if (x1 != x2 && y1 != y2)
			return false;
		if (x1 == x2) {
			if (y2 > y1) {
				for (int i = y1 + 1; i <= y2; i++) {
					if (!map[x1][i].isEmpty())
						return false;
				}
			} else {
				for (int i = y1 - 1; i >= y2; i--) {
					if (!map[x1][i].isEmpty())
						return false;
				}
			}
		} else {
			if (x2 > x1) {
				for (int i = x1 + 1; i <= x2; i++) {
					if (!map[i][y1].isEmpty())
						return false;
				}
			} else {
				for (int i = x1 - 1; i >= x2; i--) {
					if (!map[i][y1].isEmpty())
						return false;
				}
			}
		}
		
		pion1.getPlayer().useEnergie(Panneau.coutDeplacement);
		map[x2][y2].setItem(map[x1][y1].clean());
		return true;
	}
	
	public boolean attaque(int x1, int y1, int x2, int y2) {
		Pion pion1 = (Pion) map[x1][y1].getItem();
		
		if (pion1.getPlayer().getEnergie() < Panneau.coutVent)
			return false;
		if (x1 != x2 && y1 != y2)
			return false;
		if (!map[x2][y2].isPion())
			return false;

		Pion pion2 = (Pion) map[x2][y2].getItem();	
		pion1.getPlayer().useEnergie(Panneau.coutAttaque);
		pion2.attaque(10);
		return true;
	}
	
	public boolean vent(int x1, int y1, int x2, int y2) {
		Pion pion1 = (Pion) map[x1][y1].getItem();
		
		if (pion1.getPlayer().getEnergie() < Panneau.coutVent)
			return false;
		if (x1 != x2 && y1 != y2)
			return false;
		
		pion1.getPlayer().useEnergie(Panneau.coutVent);
		Pion selected = null;
		if (x1 == x2) {
			if (y2 > y1) {
				for (int i = y1 + 1; i < nbWidth; i++) {
					selected = stepVent(x1, i, x1, i - 1, selected);
				}
				if (selected != null) {
					map[x1][nbWidth - 1].setItem(selected);
				}
			} else {
				for (int i = y1 - 1; i >= 0; i--) {
					selected = stepVent(x1, i, x1, i + 1, selected);
				}
				if (selected != null) {
					map[x1][0].setItem(selected);
				}
			}
		} else {
			if (x2 > x1) {
				for (int i = x1 + 1; i < nbHeight; i++) {
					selected = stepVent(i, y1, i - 1, y1, selected);
				}
				if (selected != null) {
					map[nbHeight - 1][y1].setItem(selected);
				}
			} else {
				for (int i = x1 - 1; i >= 0; i--) {
					selected = stepVent(i, y1, i + 1, y1, selected);
				}
				if (selected != null) {
					map[0][y1].setItem(selected);
				}
			}
		}
		return true;
	}
	
	public Pion stepVent(int x1, int y1, int x2, int y2, Pion selected) {
		if (map[x1][y1].isPion()) {
			if (selected == null) {
				selected = (Pion) map[x1][y1].clean();
			} else {
				map[x2][y2].setItem(selected);
				selected = (Pion) map[x1][y1].clean();
			}
		} else if (map[x1][y1].isHole()) {
			if (selected != null) {
				kill(selected);
				selected = null;
			}
		}
		return selected;
	}
	
	public boolean clone(int x1, int y1, int x2, int y2) {
		Pion pion1 = (Pion) map[x1][y1].getItem();
		
		if (pion1.getPlayer().getEnergie() < Panneau.coutClone)
			return false;
		if (Math.abs(x2 - x1) > 1 || Math.abs(y2 - y1) > 1)
			return false;
		if (!map[x2][y2].isEmpty())
			return false;
	
		pion1.getPlayer().useEnergie(Panneau.coutClone);
		map[x2][y2].setItem(new Pion(pion1.getPlayer()));
		return true;
	}
	
	public void kill(Pion pion) {
		pion.kill();
		if (pion.getPlayer().isDead()) {
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		int dx = width / nbWidth;
		int dy = height / nbHeight;
		int realX = event.getX() - getStartX();
		int realY = event.getY() - getStartY();
		int x = realX / dx;
		int y = realY / dy;
		if (selected == null) {
			if (map[x][y].isPion()) {
				select(x, y);
			}
		} else {
			if (x == selectedX && y == selectedY) {
				unselect();
			} else {
				boolean valid = false;
				switch (fen.getAction()) {
				case 0:
					valid = move(selectedX, selectedY, x, y);
					break;
				case 1:
					valid = attaque(selectedX, selectedY, x, y);
					break;
				case 2:
					valid = vent(selectedX, selectedY, x, y);
					break;
				case 3:
					valid = clone(selectedX, selectedY, x, y);
					break;
				}
				if (valid) {
					unselect();
				} else {
					if (map[x][y].isPion()) {
						select(x, y);
					} else {
						unselect();
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}
}
