import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Fenetre implements KeyListener {
	private final static int widthMap = 500;
	private final static int height = 500;
	private final static int widthPanneau = 200;
	private JFrame fen;
	private Map map;
	private PanneauHaut panneauHaut;
	private PanneauBas panneauBas;
//	private Panneau panneau;
	private JSplitPane panneau;
	private JSplitPane splitPane;
	
	public Fenetre() {
		fen = new JFrame();
		fen.setTitle("Battle Royale !");
		fen.setResizable(false);
		fen.setSize(widthMap + widthPanneau + 20, height + 40);
		fen.setLocationRelativeTo(null);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setBackground(Color.WHITE);
		map = new Map(widthMap, height, 10, 10, this);
		panneauHaut = new PanneauHaut(this);
		panneauBas = new PanneauBas(this);
//		panneau = new Panneau(this);
		panneau = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panneau.setTopComponent(panneauHaut);
		panneau.setBottomComponent(panneauBas);
		panneau.setEnabled(false);
		panneau.setDividerSize(1);
		panneau.setDividerLocation(height / 2);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(map);
		splitPane.setRightComponent(panneau);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(widthMap + 20);
		fen.setContentPane(splitPane);
		fen.addKeyListener(this);
		fen.setVisible(true);
	}
	
	public void nextPlayer() {
		panneauHaut.nextPlayer();
		map.unselect();
		map.repaint();
	}
	
	public Player getPlayer() {
		return map.getPlayer(panneauHaut.getPlayer());
	}
	
	public void showPanneau(Pion pion) {
		panneauBas.setVisible(true);
		panneauBas.reload(pion.getPlayer().getEnergie(), pion.getPv());
		splitPane.setDividerLocation(widthMap + 20);
		panneau.setDividerLocation(height / 2);
	}
	
	public void hidePanneau() {
		panneauBas.setVisible(false);
	}
	
	public boolean actionSelected() {
		return panneauBas.getAction() != 0;
	}
	
	public int getAction() {
		return panneauBas.getAction();
	}
	
	public void kill(Player player) {
		panneauHaut.kill(map.getIndex(player));
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		switch (code) {
		case 65:
			panneauBas.setAttaque();
			break;
		case 81:
			panneauBas.setVent();
			break;
		case 87:
			panneauBas.setClone();
			break;
		case 27:
			panneauBas.unset();
			break;
		default :
//			System.out.println(code);
			break;
		}
		panneauBas.repaint();
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
}
