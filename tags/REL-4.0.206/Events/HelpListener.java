package Events;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import utilities.Manual;
import utilities.Tools;
import view.BoardFrame;

public class HelpListener implements MouseListener {
	private JLabel label;

	public HelpListener(JLabel label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		Manual.openUrl("http://code.google.com/p/jkemik/wiki/GetStarted?tm=6");
	}

	public void mouseExited(MouseEvent arg0) {
		Color color;
		color = Tools.boost(this.label.getForeground());
		this.label.setForeground(color);
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		Color color;
		color = Tools.fade(this.label.getForeground());
		this.label.setForeground(color);
		this.label.setToolTipText(BoardFrame.messages.getString("onlineManualHover"));
	}
}
