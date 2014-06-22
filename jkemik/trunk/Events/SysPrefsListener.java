package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controler.JKemik;

//import view.BoardFrame;
import view.BoardFrame;


public class SysPrefsListener implements MouseListener {
	// private JLabel label;
	private JButton label;

	// public SysPrefsListener(JLabel label) {
	public SysPrefsListener(JButton label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		BoardFrame.settings_p.updateSettingsPanel(JKemik.settings_t);
		if (JKemik.settings_t.isPlayMode()) {
			int res = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("stopGame"),
					BoardFrame.messages.getString("warning"),
					JOptionPane.YES_OPTION);
			if (res == 0) {
				JKemik.settings_t.setSystemSetupMode(true);
				JKemik.createGame(JKemik.template, JKemik.settings_t);
				BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
				ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
			} 
			
		}
		if (JKemik.settings_t.isGameSetupMode()) {
			JKemik.settings_t.setSystemSetupMode(true);
			BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
			ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		}
	}

	public void mouseExited(MouseEvent arg0) {

//		this.label.setForeground(Globals.SYSPREFS_BUTTON_FGCOLOR);
//		if (!JKemik.settings_t.isSystemSetupMode()) {
//			System.out.println("Refreshing ...");
//			Grid.refresh = true;
//			BoardFrame.displayGrid(true);
//		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent arg0) {
//		this.label.setForeground(Tools
//				.fade(Globals.SYSPREFS_BUTTON_FGCOLOR, 20));
		// this.label.setToolTipText("System Preferences.");
		this.label
				.setToolTipText(BoardFrame.messages.getString("sysPrefHover"));
	}
}
