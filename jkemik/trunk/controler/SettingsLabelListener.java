package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import api.STemplate;
import utilities.Tools;
import view.SettingsPanel;

public class SettingsLabelListener implements MouseListener {
	private JLabel label;

	public SettingsLabelListener(JLabel label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		STemplate t = JKemik.settings_t;
		
		JKemik.settings.setAutoCap(t.isAutoCapture());
		JKemik.settings.setAutoPass(t.isAutoPass());
		SettingsPanel.setMax_win(t.getMaxWinVal());
		JKemik.settings.setVisible(true);
		
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
	}
}
