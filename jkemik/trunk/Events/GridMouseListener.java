/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import controler.JKemik;

import api.AbstractGame;
import api.Player;
import api.Point;
import view.BoardFrame;
import view.Grid;

/**
 * @author Dalet
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
		AbstractGame game = JKemik.game;
		Player current = (Player)game.getCurrentP();
		
		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = new Point(Grid.x, Grid.y);
		
		 if (game.getCurrentP().isTurn()) {
             // System.out.println("Saw turn ...");
             if (!game.getCollection().containsKey(temp.toString())) {
                     // System.out.println("Plotting ...");
                     Grid.plotPoint = true;
                     BoardFrame.grid.repaint();
                     temp.setStatus(1);
                     temp.setId(current.getId());
                     game.getCollection().put(temp.toString(), temp);
                     //game.setEmbuche_on(true);
                     //System.out.println("Collection: " + game.getCollection());
                     game.setPlayFlag();
                     game.getCurrentP().setTurn(false);
                     Grid.mouseMove = false;
             }
		 }
		
			//System.out.println("Collection: " + game.getCollection().toString());
		BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
		BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
		BoardFrame.updateBoardStatus();
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
		// if (!Grid.outOfBoard(Grid.x, Grid.y, (int) Grid.squareSize)) {
		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = Grid.makeDrawable(Grid.x, Grid.y);
		AbstractGame game = JKemik.game;
		if (game.getCollection().containsKey(temp.toString())) {

		} else {
			BoardFrame.grid.repaint();
			Grid.mouseMove = true;
		}
		// }
		// if (JKemik.game.checkEndGame()) {
		if (JKemik.game.getStatus() == 1) {
			JOptionPane.showMessageDialog(null, ""
					+ JKemik.game.getGuest().getName() + " "
					+ BoardFrame.messages.getString("winM"), " Win",
					JOptionPane.OK_OPTION);
			JKemik.game.setStatus(1);
			JKemik.createGame(JKemik.template, JKemik.settings_t);
			JKemik.settings_t.setGameSetupMode(true);
			BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
			ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);

			// Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			BoardFrame.Game_status.setForeground(Color.GREEN);
		}
		BoardFrame.grid.repaint();
		// }
		// BoardFrame.grid.setToolTipText("" + new Point(Grid.x, Grid.y));
	}
}