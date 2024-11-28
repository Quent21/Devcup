import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panneau extends JPanel {
	public final static int coutDeplacement = 10;
	public final static int coutAttaque = 20;
	public final static int coutVent = 10;
	public final static int coutClone = 10;
	private Fenetre fen;
	private int action;
	private JLabel lEnergie;
	private JLabel lPv;
	private JLabel lAttaque;
	private JLabel lVent;
	private JLabel lClone;
	private JLabel selected;
	
	public Panneau(Fenetre fen) {
		this.fen = fen;
		action = 0;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lEnergie = new JLabel();
		lPv = new JLabel();
		lAttaque = new JLabel(" A : Lancer une fleche (" + coutAttaque + ") ");
		lAttaque.setOpaque(true);
		lVent = new JLabel(" Q : Coup de vent (" + coutVent + ") ");
		lVent.setOpaque(true);
		lClone = new JLabel(" W : Cloner (" + coutClone + ") ");
		lClone.setOpaque(true);
		add(lEnergie);
		add(lPv);
		add(new JLabel(" "));
		add(new JLabel(" Déplacement (" + coutDeplacement + ")"));
		add(new JLabel(" "));
		add(lAttaque);
		add(lVent);
		add(lClone);
		add(new JLabel(" Capturer"));
		selected = null;
		setVisible(false);
	}
	
	public void reload(int energie, int pv) {
		lEnergie.setText(" Energie : " + energie);
		lPv.setText(" PV : " + pv);
		unset();
	}
	
	public void select(JLabel selected) {
		if (this.selected != null) {
			this.selected.setBackground(null);
		}
		this.selected = selected;
		if (selected != null) {
			selected.setBackground(Color.CYAN);
		}
	}
	
	public void setAttaque() {
		action = 1;
		select(lAttaque);
	}
	
	public void setVent() {
		action = 2;
		select(lVent);
	}
	
	public void setClone() {
		action = 3;
		select(lClone);
	}
	
	public void unset() {
		action = 0;
		select(null);
	}
	
	public int getAction() {
		return action;
	}
}
