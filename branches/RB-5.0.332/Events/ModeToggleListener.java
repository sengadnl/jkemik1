package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;

import controler.JKemik;

import view.BoardFrame;

public class ModeToggleListener implements MouseListener {
	private JCheckBox mode;

	public ModeToggleListener(JCheckBox mode) {
		setManual(mode);
	}

	public JCheckBox getManual() {
		return mode;
	}

	public void setManual(JCheckBox manual) {
		this.mode = manual;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (BoardFrame.mode.isSelected()) {
			System.out.println("Manual is selected");
			if (!JKemik.settings_t.isAutoCapture()
					&& !JKemik.settings_t.isAutoPass()) {
				JKemik.settings_t.restaureMemo();
			}else{
				JKemik.settings_t.setAutoCapture(false);
				JKemik.settings_t.setAutoPass(false);
			}
		} else {
			System.out.println("Manual is NOT selected");
			if (!JKemik.settings_t.getMemo()[0]
					&& !JKemik.settings_t.getMemo()[1]) {
				JKemik.settings_t.setAutoCapture(true);
				JKemik.settings_t.setAutoPass(true);
			}else{
				JKemik.settings_t.restaureMemo();
			}
		}
		BoardFrame.showControlButtons();
		BoardFrame.updateSettingPanel();
	}

	public void mouseEntered(MouseEvent arg0) {
		// manual.setToolTipText("Check to enable temporary manual mode.");
		mode.setToolTipText(BoardFrame.messages.getString("manualMode"));
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
