package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import api.Point;

import view.BoardFrame;
import view.Grid;

public class ManualSelectionListener implements MouseListener {
	private JCheckBox capture;

	public ManualSelectionListener(JCheckBox capture) {
		this.setDebug(capture);
	}

	public void mouseClicked(MouseEvent arg0) {
		if (capture.isSelected()) {
			Grid.setManualc(true);
			Grid.selectPoint = true;
			//JKemik.game.getCurrentP().setOrigin(JKemik.game.getLastp());
			//JKemik.game.getCurrentP().getSelected().add(JKemik.game.getLastp());
			//Grid.setSelectedP(JKemik.game.getLastp());
			JKemik.game.setEmbuche_on(false);
			//BoardFrame.grid.repaint();
		} else {
			JKemik.game.getCurrentP().setSelected(new ArrayList<Point>());
			BoardFrame.panel2.setBackground(BoardFrame.BOARD_COLOR);
			JKemik.view.repaint();
			BoardFrame.grid.drawn = false;
			Grid.manualc = false;
			Grid.debug = true;
			BoardFrame.repaintGrid();
		}
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public JCheckBox getDebug() {
		return capture;
	}

	public void setDebug(JCheckBox debug) {
		this.capture = debug;
	}
}
