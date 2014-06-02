package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import controler.JKemik;
import view.BoardFrame;

public class SysPrefsCancelListener implements MouseListener {

	private JButton cancel;

	public SysPrefsCancelListener(JButton cancel) {
		this.setSave(this.cancel);
	}

	public void mouseClicked(MouseEvent e) {
		JKemik.settings_t.setGameSetupMode(true);
		BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void setSave(JButton cancel) {
		this.cancel = cancel;
	}

	public JButton getSave() {
		return cancel;
	}
}
