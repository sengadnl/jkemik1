package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import controler.JKemik;
import view.BoardFrame;
import view.Grid;

public class RefreshListener implements MouseListener {
	private JButton refresh;

	public RefreshListener(JButton refresh) {
		this.setDebug(refresh);
	}

	public void mouseClicked(MouseEvent arg0) {
		if (JKemik.settings_t.isGameSetupMode()
				|| JKemik.settings_t.isPlayMode()) {
			Grid.setRefresh(true);
			BoardFrame.displayGrid(true);
                        BoardFrame.grid.repaint();
			BoardFrame.updateBoardStatus();
		} else {
			BoardFrame.displayGrid(false);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		refresh.setToolTipText(BoardFrame.messages.getString("refreshHover"));
	}

	public void mouseExited(MouseEvent arg0) {
//		if (!JKemik.settings_t.isSystemSetupMode()) {
//			Grid.refresh = true;
//			BoardFrame.displayGrid(true);
//		}
		//BoardFrame.displayGrid(true);
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
