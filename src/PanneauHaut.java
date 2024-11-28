import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanneauHaut extends JPanel implements ActionListener {
	private Fenetre fen;
	private int player;
	private JLabel[] lPlayersEnergie;
	private boolean[] alivePlayers;
	private JButton bFinDeTour;
	
	public PanneauHaut(Fenetre fen) {
		this.fen = fen;
		this.player = 3;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lPlayersEnergie = new JLabel[4];
		alivePlayers = new boolean[4];
		for (int i = 0; i < 4; i++) {
			lPlayersEnergie[i] = new JLabel(" Joueur " + (i + 1) + " : 42");
			lPlayersEnergie[i].setOpaque(true);
			add(lPlayersEnergie[i]);
			alivePlayers[i] = true;
		}
		add(Box.createRigidArea(new Dimension(0, 30)));
		bFinDeTour = new JButton("Fin de tour");
		bFinDeTour.addActionListener(this);
		bFinDeTour.setFocusable(false);
		add(bFinDeTour);
		nextPlayer();
	}
	
	public void nextPlayer() {
		lPlayersEnergie[player].setBackground(null);
		if (player >= 3) {
			player = 0;
		} else {
			player++;
		}
		lPlayersEnergie[player].setBackground(Color.CYAN);
	}
	
	public int getPlayer() {
		return player;
	}
	
	public boolean endGame() {
		return false;
	}
	
	public void kill(int player) {
		alivePlayers[player] = false;
		lPlayersEnergie[player].setBackground(Color.RED);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		fen.nextPlayer();
	}
}
