package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;

import utilities.Globals;
import utilities.Tools;
import view.BoardFrame;
import view.RotateLabel;

public class ManualCaptureListener implements MouseListener {
	private RotateLabel label;

	public ManualCaptureListener(RotateLabel label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent e) {
		this.label.rotateLabel();
		String str = this.label.getActiveLabel();
		if (str.equals("ON")) {
			JKemik.settings_t.setManualCapture(true);
		} else {
			JKemik.settings_t.setManualCapture(false);
		}
	}

	public void mouseEntered(MouseEvent e) {
		Color c = Tools.boost(BoardFrame.BOARD_COLOR, Globals.LABEL_VARIANT);
		this.label.setForeground(Tools.fade(c));
	}

	public void mouseExited(MouseEvent e) {
		//this.label.resetBGC();
		Color c = BoardFrame.BOARD_COLOR;
		this.label.setForeground(c);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

