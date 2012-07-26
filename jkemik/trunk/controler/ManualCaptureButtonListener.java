package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import view.BoardFrame;
import view.Grid;

public class ManualCaptureButtonListener implements MouseListener{
	private JButton capture;

	public ManualCaptureButtonListener(JButton capture) {
		this.setDebug(capture);
	}

	public void mouseClicked(MouseEvent arg0) {
		Grid.setManualc(true);
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
		return capture;
	}

	public void setDebug(JButton debug) {
		this.capture = debug;
	}
}
