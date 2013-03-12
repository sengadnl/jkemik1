package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		if (JKemik.settings_t.isPlayMode()) {
			int res = JOptionPane.showConfirmDialog(null,"Would you like to stop the current Game?","Warning",
					JOptionPane.YES_OPTION);
			if(res == 0){
				JKemik.settings_t.setSystemSetupMode(true);
				BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
				ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
			}else{
				
			}
		}
		
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
		// this.label.setToolTipText("System Preferences.");
		this.label
				.setToolTipText(BoardFrame.messages.getString("sysPrefHover"));
	}
}
