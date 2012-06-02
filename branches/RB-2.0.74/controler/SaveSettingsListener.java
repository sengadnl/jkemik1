package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.BoardFrame;
import view.SettingsPanel;

public class SaveSettingsListener implements MouseListener {
	private JButton save;

	public SaveSettingsListener(JButton save) {
		this.setSave(save);
	}

	public void mouseClicked(MouseEvent e) {
		try {
			if (!JKemik.settings_t.isAutoCapture()
					&& JKemik.settings_t.isAutoPass()) {
				JOptionPane.showMessageDialog(null, "Auto Capture MUST be ON"
						+ "\nwhen automatic turn pass is ON", "Ellegal Action",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String str = SettingsPanel.max_win.getText();
				JKemik.settings_t.setMaxWinVal(Integer.parseInt(str));
				BoardFrame.upDateSetting();
				BoardFrame.capture.setVisible(false);
				JKemik.settings.setVisible(false);
			}
		} catch (Exception exp) {

		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JButton getSave() {
		return save;
	}
}
