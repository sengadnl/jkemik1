package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;

import api.STemplate;
import utilities.Tools;
import view.BoardFrame;
import view.Grid;
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
//				STemplate t = JKemik.settings_t;
//				String str = SettingsPanel.max_win.getText();
//				// SettingsPanel.translateUI();
//				String lang = (String) SettingsPanel.getLanguageList()
//						.getSelectedItem();
//				String key = Tools.languageKey(lang);
//				String properties = Tools.propertiesFilename(key);
//				int maxw = Integer.parseInt(str);
//				if (Tools.isMaxWinLessThanGrid(Grid.getBoardSize(), maxw)) {
//					t.setMaxWinVal(maxw);
//					t.setMemo(t.isAutoCapture(), t.isAutoPass());
//					t.setLanguage(lang);
//					Locale local = new Locale(key);
//					BoardFrame.setMessages(ResourceBundle.getBundle(properties,
//							local));
//					BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
//					ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
//				} else {
//					JOptionPane.showMessageDialog(null, BoardFrame.messages
//							.getString("maxWinSizeMustbBe1")
//							+ Grid.getBoardSize()
//							+ BoardFrame.messages
//									.getString("maxWinSizeMustbBe2"),
//							BoardFrame.messages.getString("wrongInput"),
//							JOptionPane.WARNING_MESSAGE);
//					
//				}
				
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
