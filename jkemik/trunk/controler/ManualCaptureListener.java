package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		this.label.highlight();
	}

	public void mouseExited(MouseEvent e) {
		this.label.resetBGC();
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

