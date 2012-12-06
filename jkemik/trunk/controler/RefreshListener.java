package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import view.BoardFrame;
import view.Grid;

public class RefreshListener implements MouseListener{
	private JButton refresh;

	public RefreshListener(JButton refresh) {
		this.setDebug(refresh);
	}

	public void mouseClicked(MouseEvent arg0) {
		Grid.setDebug(true);
		BoardFrame.repaintGrid();
	}

	public void mouseEntered(MouseEvent arg0) {
		refresh.setToolTipText("Click to refresh the board");
	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public JButton getDebug() {
		return refresh;
	}

	public void setDebug(JButton debug) {
		this.refresh = debug;
	}

}
