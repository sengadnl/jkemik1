package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import controler.JKemik;

import utilities.Tools;
//import view.BoardFrame;
import view.BoardFrame;


public class SysPrefsListener implements MouseListener {
	private JLabel label;

	public SysPrefsListener(JLabel label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		JKemik.settings_t.setSystemSetupMode(true);
		BoardFrame.uiLooksUpdate(JKemik.settings_t,JKemik.template);
		ViewEvents.uiEventUpdates(JKemik.settings_t,JKemik.template);
	}

	public void mouseExited(MouseEvent arg0) {
		Color color;
		color = Tools.boost(this.label.getForeground());
		this.label.setForeground(color);
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent arg0) {
		Color color;
		color = Tools.fade(this.label.getForeground());
		this.label.setForeground(color);
		//this.label.setToolTipText("System Preferences.");
		this.label.setToolTipText(BoardFrame.messages.getString("sysPrefHover"));
	}
}
