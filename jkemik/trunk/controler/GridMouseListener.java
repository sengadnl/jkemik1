/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import api.Game;
import api.Player;
import api.Point;

import utilities.Tools;
import view.BoardFrame;
import view.Grid;

/**
 * @author dalet
 * 
 */
public class GridMouseListener implements MouseListener, MouseMotionListener {

	private Grid grid;
	public GridMouseListener(Grid grid) {
		this.grid = grid;
	}

	public void mouseClicked(MouseEvent e) {
		Grid.mouseclicked = true;
		Grid.x = e.getX();
		Grid.y = e.getY();
		Game game = JKemik.game;
		Player current = game.getCurrentP();
		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = new Point(Grid.x, Grid.y);
		if (Grid.manualc) {
			
			if (Tools.containPoint(temp, game.getCurrentP().getPloted())
					&& !Tools.containPoint(temp, game.getDeadDots())) {
				Grid.selectPoint = true;
				current.getSelected().add(temp);
				Grid.setSelectedP(temp);
				if (temp.compareTo(current.getOrigin()) == 0
						&& current.getSelected().size() >= 4) {
					Grid.cell = game.capture((int)Grid.squareSize);// new line
					BoardFrame.grid.repaint();
					game.getCurrentP().setSelected(new ArrayList<Point>());
					Grid.manualc = false;
				}
				//BoardFrame.grid.repaint();
			}
		} else {
			if (game.getCurrentP().isTurn()) {
				if (!Tools.containPoint(temp, game.getCurrentP().getPloted())
						&& !Tools.containPoint(temp, game.getGuest()
								.getPloted())
						&& !Tools.containPoint(temp, game.getDeadDots())) {
					Grid.plotPoint = true;
					BoardFrame.grid.repaint();
					game.getCurrentP().getPloted().add(temp);
					game.lastp = temp;
					game.setEmbuche_on(true);
					game.setPlayFlag();
					game.getCurrentP().setTurn(false);
					Grid.mouseMove = false;
				}
			}
		}

		if (game.isEmbuche_on() && !Grid.manualc) {
			 Grid.cell = game.embush(Grid.squareSize);// new line
			BoardFrame.grid.repaint();
			System.out.println("in 1");
			game.getCurrentP().setSelected(new ArrayList<Point>());
		}

		if (JKemik.game.getCurrentP().isTurn()) {
			System.out.println("in 2");
			this.grid.setMouseclicked(true);
		}
		BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
		BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent e) {
		Grid.mouseMove = true;
		Grid.x = e.getX();
		Grid.y = e.getY();

		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = Grid.makeDrawable(Grid.x, Grid.y);
		Game game = JKemik.game;
		if (Tools.containPoint(temp, game.getCurrentP().getPloted())
				|| Tools.containPoint(temp, game.getGuest().getPloted())
				|| Tools.containPoint(temp, game.getDeadDots())) {

		} else {
			BoardFrame.grid.repaint();
			Grid.mouseMove = true;
		}

		if (JKemik.game.checkEndGame()) {
			JOptionPane.showMessageDialog(null, ""
					+ JKemik.game.getGuest().getName() + " WINS !!!", " Win",
					JOptionPane.OK_OPTION);

			BoardFrame.pColor1.addMouseListener(ViewEvents.p1Listener);
			BoardFrame.pColor2.addMouseListener(ViewEvents.p2Listener);
			BoardFrame.label1.addMouseListener(ViewEvents.n1Listener);
			BoardFrame.label2.addMouseListener(ViewEvents.n2Listener);
			BoardFrame.l1.addMouseListener(ViewEvents.gridSizeListener);
			BoardFrame.l2.addMouseListener(ViewEvents.gameThemeListener);
			BoardFrame.save.addMouseListener(ViewEvents.saveListener);
			BoardFrame.settings.addMouseListener(ViewEvents.saveSettings);
			BoardFrame.grid.removeMouseListener(ViewEvents.gridListener);
			BoardFrame.grid.removeMouseMotionListener(ViewEvents.gridListener);
			BoardFrame.enableGameControlPanel();

			// Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			BoardFrame.Game_status.setForeground(Color.GREEN);

			BoardFrame.boostLabel(BoardFrame.settings);
			BoardFrame.fadeButton(BoardFrame.pass_turn);
			BoardFrame.fadeButton(BoardFrame.capture);
			BoardFrame.fadeButton(BoardFrame.undo);

			// Reset grid
//			BoardFrame.panel2.repaint();
//			BoardFrame.panel1.repaint();
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();

//			BoardFrame.p1panel.initPanelForNewGame("P1", Color.WHITE);
//			BoardFrame.p2panel.initPanelForNewGame("P2", Color.WHITE);
		}

		BoardFrame.grid.repaint();
	}
}