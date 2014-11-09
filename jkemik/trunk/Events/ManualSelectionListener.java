package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import controler.JKemik;
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
			BoardFrame.mode.setVisible(false);
			BoardFrame.pass_turn.setVisible(false);
			JKemik.getGame().setEmbuche_on(false);
		} else {
			JKemik.getGame().getCurrentP().setSelected(new ArrayList<Point>());
			JKemik.view.repaint();
			BoardFrame.grid.drawn = false;
			Grid.manualc = false;
			Grid.refresh = true;
			BoardFrame.mode.setVisible(true);
			BoardFrame.pass_turn.setVisible(true);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		//capture.setToolTipText("Check to select manually.");
		capture.setToolTipText(BoardFrame.messages.getString("checkSelect"));
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
