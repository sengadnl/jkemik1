/**
 * 
 */
package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controler.JKemik;
import api.AbstractGame;
import api.Player;
import api.Point;
import utilities.Globals;
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

		// Pass turn is this is a right click
		if (SwingUtilities.isRightMouseButton(e)
				&& !JKemik.settings_t.isAutoPass()) {
			if (!BoardFrame.mouseSelection.isSelected()) {
				/*
				 * Pass turn only if mouse was clicked and it's no longer
				 * currentP's turn
				 */
				if (JKemik.game.getCurrentP().getPlay_flag() == 1) {
					JKemik.game.switchPlayTurns();
				} else {
					JOptionPane.showMessageDialog(null,
							BoardFrame.messages.getString("ilPass"),
							BoardFrame.messages.getString("ilAction"),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {

			AbstractGame game = JKemik.game;
			Player current = (Player) game.getCurrentP();
			// Allow mouse click
			Grid.mouseclicked = true;

			// Get X and Y
			Grid.x = e.getX();
			Grid.y = e.getY();

			// Guess player's point
			Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
			Point temp = new Point(Grid.x, Grid.y);

			if (Grid.manualc) {
				temp = game.getCollection().get(temp.toString());
				if (temp == null) {
					return;
				}
				if (game.select(temp, Grid.squareSize)) {
					System.out.println("Current Selection: "
							+ game.getCurrentP().getSelected());
					Grid.selectPoint = true;
					Grid.setSelectedP(temp);
					if (temp.adjacentTo(current.getOrigin(), Grid.squareSize)
							&& current.getSelected().size() >= 4) {
						Grid.cell = game.capture((int) Grid.squareSize);//
						game.getCurrentP().setSelected(new ArrayList<Point>());
						BoardFrame.feedbackarea
								.setText((game.getCaptured_count() + game
										.getRedeemed_count())
										+ " "
										+ BoardFrame.messages
												.getString("feedback3")
										+ " "
										+ game.getCurrentP().getName());
						BoardFrame.mouseSelection.setSelected(false);
						BoardFrame.mode.setVisible(true);
						BoardFrame.pass_turn.setVisible(true);
						Grid.manualc = false;
						if (Grid.cell == null) {
							Grid.refresh = true;
							BoardFrame.errorFeedback("THIS CELL IS NULL!!!");
							BoardFrame.displayGrid(true);
						}
						if (Grid.cell.getStatus() == Globals.CELL_EMPTY) {
							Grid.refresh = true;
							BoardFrame.errorFeedback("CELL STATUS IS EMPTY!!!");
							BoardFrame.displayGrid(true);
						}
					}
					BoardFrame.grid.repaint();
				}
			} else {
				if (game.getCurrentP().isTurn()) {
					if (!game.getCollection().containsKey(temp.toString())) {

						Grid.plotPoint = true;
						BoardFrame.grid.repaint();

						// Mark point as played
						temp.setStatus(Point.PLAYED);

						// Mark point as belonging to current player
						temp.setId(current.getId());

						// Remember last play
						current.setLatestP(temp);

						// Add to the board
						game.getCollection().put(temp.toString(), temp);

						game.getCurrentP().rememberPoint(temp,
								JKemik.settings_t.getBacktrackingDistance());

						game.setEmbuche_on(true);

						// Setting turn
						game.setPlayFlag();
						game.getCurrentP().setTurn(false);
						Grid.mouseMove = false;
						BoardFrame.feedback(game.getCurrentP().getName() + " "
								+ BoardFrame.messages.getString("feedback4"));
					}
				}
			}

			if (game.isEmbuche_on()) {
				if (JKemik.settings_t.isAutoCapture()) {
					BoardFrame.progressB.setVisible(true);
					BoardFrame.progressB.setIndeterminate(true);
					Grid.cell = JKemik.embush(Grid.squareSize);
					BoardFrame.progressB.setIndeterminate(false);
					BoardFrame.progressB.setVisible(false);
					BoardFrame.grid.repaint();
				}
			}

			if (game.getCurrentP().isTurn()) {
				this.grid.setMouseclicked(true);
			}

			BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
			BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
			BoardFrame.updateBoardStatus();
		}
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

		if (JKemik.checkEndGame()) {
			JOptionPane.showMessageDialog(null, "" + JKemik.getEndingMessage(),
					" Win", JOptionPane.OK_OPTION);
			BoardFrame.feedback(JKemik.getEndingMessage());
			JKemik.game.setStatus(1);
			JKemik.createGame(JKemik.template, JKemik.settings_t);
			JKemik.settings_t.setGameSetupMode(true);
			BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
			ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);

			// Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			// BoardFrame.Game_status.setForeground(Color.GREEN);
		}
		BoardFrame.grid.repaint();

		// }
		// BoardFrame.grid.setToolTipText("" + new Point(Grid.x, Grid.y));
	}
}