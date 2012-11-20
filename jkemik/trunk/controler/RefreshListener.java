package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import view.BoardFrame;
import view.Grid;

public class RefreshListener implements MouseListener{
	private JButton debug;

	public RefreshListener(JButton debug) {
		this.setDebug(debug);
	}

	public void mouseClicked(MouseEvent arg0) {
		Grid.setDebug(true);
		BoardFrame.repaintGrid();
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public JButton getDebug() {
		return debug;
	}

	public void setDebug(JButton debug) {
		this.debug = debug;
	}

}
