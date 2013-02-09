package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import controler.JKemik;

import api.STemplate;
import utilities.Tools;
//import view.BoardFrame;
import view.BoardFrame;
import view.SettingsPanel;

public class SysPrefsListener implements MouseListener {
	private JLabel label;

	public SysPrefsListener(JLabel label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		STemplate t = JKemik.settings_t;
		SettingsPanel.translateSettingsPanel(t);
		
		BoardFrame.disableGameControlPanel();
		BoardFrame.pColor1.removeMouseListener(ViewEvents.p1Listener);
		BoardFrame.pColor2.removeMouseListener(ViewEvents.p2Listener);
		BoardFrame.label1.removeMouseListener(ViewEvents.n1Listener);
		BoardFrame.label2.removeMouseListener(ViewEvents.n2Listener);
		BoardFrame.l1.removeMouseListener(ViewEvents.gridSizeListener);
		BoardFrame.l2.removeMouseListener(ViewEvents.gameThemeListener);
		BoardFrame.settings.removeMouseListener(ViewEvents.sysPrefsListener);
		BoardFrame.startG.removeMouseListener(ViewEvents.saveListener);
		BoardFrame.settings.removeMouseListener(ViewEvents.sysPrefsListener);
		
		BoardFrame.displayGrid(false);
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
