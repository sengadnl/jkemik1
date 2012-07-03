/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import view.BoardFrame;
//import view.Grid;

/**
 * @author dalet
 * 
 */
public class GridMouseListener implements MouseListener, MouseMotionListener {

//	private Grid grid;
//
//	public GridMouseListener(Grid grid) {
//		this.grid = grid;
//	}
//
	public void mouseClicked(MouseEvent e) {
//		Grid.x = e.getX();
//		Grid.y = e.getY();
//		BoardFrame.repaintGrid();
//		if (JKemik.game.getCurrentP().isTurn()) {
//			this.grid.setMouseclicked(true);
//		}
//		BoardFrame.p1panel.updatePlayerPanel(JKemik.game.getPlayer1());
//		BoardFrame.p2panel.updatePlayerPanel(JKemik.game.getPlayer2());		
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
//		Grid.mouseMove = true;
//		Grid.x = e.getX();
//		Grid.y = e.getY();
//		if (JKemik.game.checkEndGame()) {
//			JOptionPane.showMessageDialog(null, ""
//					+ JKemik.game.getGuest().getName() + " WINS !!!", " Win",
//					JOptionPane.OK_OPTION);
//			BoardFrame.pColor1.addMouseListener(ViewEvents.p1Listener);
//			BoardFrame.pColor2.addMouseListener(ViewEvents.p2Listener);
//			BoardFrame.label1.addMouseListener(ViewEvents.n1Listener);
//			BoardFrame.label2.addMouseListener(ViewEvents.n2Listener);
//			BoardFrame.l1.addMouseListener(ViewEvents.gridSizeListener);
//			BoardFrame.l2.addMouseListener(ViewEvents.gameThemeListener);
//			BoardFrame.save.addMouseListener(ViewEvents.saveListener);
//			BoardFrame.settings.addMouseListener(ViewEvents.saveSettings);
//			BoardFrame.grid.removeMouseListener(ViewEvents.gridListener);
//			BoardFrame.grid.removeMouseMotionListener(ViewEvents.gridListener);
//			BoardFrame.enableGameControlPanel();
//
//			// Reset game exit label
//			BoardFrame.Game_status.setText("NEW");
//			BoardFrame.Game_status.setForeground(Color.GREEN);
//
//			BoardFrame.boostLabel(BoardFrame.settings);
//			BoardFrame.fadeButton(BoardFrame.pass_turn);
//			BoardFrame.fadeButton(BoardFrame.capture);
//			BoardFrame.fadeButton(BoardFrame.undo);
//
//			// Reset grid
//			BoardFrame.panel2.repaint();
//			BoardFrame.grid.DRAWN = false;
//			BoardFrame.grid.repaint();
//
//			BoardFrame.p1panel.initPanelForNewGame("P1", Color.WHITE);
//			BoardFrame.p2panel.initPanelForNewGame("P2", Color.WHITE);
//		} 		
//
//		BoardFrame.grid.repaint();
	}
}
