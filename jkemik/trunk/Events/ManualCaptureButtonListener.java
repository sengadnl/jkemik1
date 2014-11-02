package Events;

import api.AbstractGame;
import api.Point;
import controler.JKemik;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import view.BoardFrame;
import view.Grid;


public class ManualCaptureButtonListener implements MouseListener{
	private JButton capture;

	public ManualCaptureButtonListener(JButton capture) {
		this.setDebug(capture);
	}

        @Override
	public void mouseClicked(MouseEvent arg0) {
		AbstractGame game = JKemik.getGame();
		System.out.println("trying to capture...");
		Grid.cell = game.capture(Grid.squareSize);
		System.out.println("Capture result: " + Grid.cell);
		game.getCurrentP().setSelected(new ArrayList<Point>());
                Grid.setRefresh(true);
                BoardFrame.displayGrid(true);
		BoardFrame.grid.repaint();//
		BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
		BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
		BoardFrame.updateBoardStatus();
	}

        @Override
	public void mouseEntered(MouseEvent arg0) {

	}

        @Override
	public void mouseExited(MouseEvent arg0) {

	}

        @Override
	public void mousePressed(MouseEvent arg0) {

	}

        @Override
	public void mouseReleased(MouseEvent arg0) {

	}

	public JButton getDebug() {
		return capture;
	}

	private void setDebug(JButton debug) {
		this.capture = debug;
	}
}
