/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controler.JKemik;

import api.AbstractGame;
import api.Player;
import api.Point;

import utilities.Tools;
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
		// if the point is not out of the board's bounds
		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = new Point(Grid.x, Grid.y);
		if (Grid.manualc) {
			if (game.select(temp, Grid.squareSize)) {
				Grid.selectPoint = true;
				Grid.setSelectedP(temp);
				if (temp.adjacentTo(current.getOrigin(), Grid.squareSize)
						&& current.getSelected().size() >= 4) {
					Grid.cell = game.capture((int) Grid.squareSize);//
					game.getCurrentP().setSelected(new ArrayList<Point>());
					// System.out.println(current.getSelected().size()
					// + " were selected");
					BoardFrame.manual_c.setSelected(false);
					BoardFrame.manual.setVisible(true);
					//if (BoardFrame.manual.isSelected()) {
						//BoardFrame.manual.setSelected(false);
//						JKemik.settings_t.restaureMemo();
//						BoardFrame.showControlButtons();
//						BoardFrame.updateSettingPanel();
					//}
					Grid.manualc = false;
				}
				BoardFrame.grid.repaint();
			}
		} else {
			// System.out.println("About to Plot ...");
			if (game.getCurrentP().isTurn()) {
				// System.out.println("Saw turn ...");
				if (!Tools.containPoint(temp, game.getCurrentP().getPloted())
						&& !Tools.containPoint(temp, game.getGuest()
								.getPloted())
						&& !Tools.containPoint(temp, game.getDeadDots())) {
					// System.out.println("Plotting ...");
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
		if (game.isEmbuche_on() && JKemik.settings_t.isAutoCapture()) {
			BoardFrame.progressB.setVisible(true);
			BoardFrame.progressB.setIndeterminate(true);
			Grid.cell = game.embush(Grid.squareSize);// new line
			BoardFrame.progressB.setIndeterminate(false);
			BoardFrame.progressB.setVisible(false);
			BoardFrame.grid.repaint();
			System.out.println("Embush attempt");
			game.getCurrentP().setSelected(new ArrayList<Point>());
		}
		if (game.getCurrentP().isTurn()) {
			System.out
					.println("Marking " + game.getCurrentP() + " end of turn");
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
		// if (!Grid.outOfBoard(Grid.x, Grid.y, (int) Grid.squareSize)) {
		Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
		Point temp = Grid.makeDrawable(Grid.x, Grid.y);
		AbstractGame game = JKemik.game;
		if (Tools.containPoint(temp, game.getCurrentP().getPloted())
				|| Tools.containPoint(temp, game.getGuest().getPloted())
				|| Tools.containPoint(temp, game.getDeadDots())) {

		} else {
			BoardFrame.grid.repaint();
			Grid.mouseMove = true;
		}
		// }
		if (JKemik.game.checkEndGame()) {
			JOptionPane.showMessageDialog(null, ""
					+ JKemik.game.getGuest().getName() + BoardFrame.messages.getString("winM"), " Win",
					JOptionPane.OK_OPTION);
			BoardFrame.panel2.repaint();
			BoardFrame.pColor1.addMouseListener(ViewEvents.p1Listener);
			BoardFrame.pColor2.addMouseListener(ViewEvents.p2Listener);
			BoardFrame.label1.addMouseListener(ViewEvents.n1Listener);
			BoardFrame.label2.addMouseListener(ViewEvents.n2Listener);
			BoardFrame.l1.addMouseListener(ViewEvents.gridSizeListener);
			BoardFrame.l2.addMouseListener(ViewEvents.gameThemeListener);
			BoardFrame.startG.addMouseListener(ViewEvents.saveListener);
			BoardFrame.settings.addMouseListener(ViewEvents.sysPrefsListener);
			BoardFrame.grid.removeMouseListener(ViewEvents.gridListener);
			BoardFrame.grid.removeMouseMotionListener(ViewEvents.gridListener);
			BoardFrame.enableGameControlPanel();

			// Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			BoardFrame.Game_status.setForeground(Color.GREEN);

			BoardFrame.boostLabel(BoardFrame.settings,Color.WHITE, BoardFrame.THEME_COLOR);

			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
		}
		BoardFrame.grid.repaint();
		// }
		// BoardFrame.grid.setToolTipText("" + new Point(Grid.x, Grid.y));
	}
}