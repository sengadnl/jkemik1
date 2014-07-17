package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import controler.JKemik;
import view.BoardFrame;


public class SaveSettingsListener implements MouseListener {
	private JButton save;

	public SaveSettingsListener(JButton save) {
		this.setSave(save);
	}

	public void mouseClicked(MouseEvent e) {
		//try {
			if (!JKemik.settings_t.isAutoCapture()
					&& JKemik.settings_t.isAutoPass()) {
				
				JOptionPane.showMessageDialog(null,
						BoardFrame.messages.getString("autoMustBeON1")
								+ "\n"
								+ BoardFrame.messages
										.getString("autoMustBeON2"),
						BoardFrame.messages.getString("ilAction"),
						JOptionPane.WARNING_MESSAGE);
			} else {
				JKemik.settings_t.setGameSetupMode(true);
				BoardFrame.print_point.setText("" + BoardFrame.messages.getString("gameSetupMode"));
				JKemik.saveSysPrefs();	
			}
			BoardFrame.settings_p.updateSettingsPanel(JKemik.settings_t);
//		} catch (Exception exp) {
//
//		}
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
