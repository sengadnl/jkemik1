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
				JOptionPane.showMessageDialog(null, "Auto Capture MUST be ON"
						+ "\nwhen automatic turn pass is ON", "Ellegal Action",
						JOptionPane.WARNING_MESSAGE);
			} else {
				STemplate t = JKemik.settings_t;
				String str = SettingsPanel.max_win.getText();
				String lang = (String)SettingsPanel.getLanguageList().getSelectedItem();
				String key = Tools.languageKey(lang);
				String properties = Tools.propertiesFilename(key);
				int maxw = Integer.parseInt(str);
				if(Tools.isMaxWinLessThanGrid(Grid.getBoardSize(), maxw)){
					t.setMaxWinVal(maxw);
					BoardFrame.updateSettingPanel();
					t.setMemo(t.isAutoCapture(), t.isAutoPass());
					t.setLanguage(lang);
					
					Locale local = new Locale(key);
					BoardFrame.setMessages(ResourceBundle.getBundle(properties, local));
					
					System.out.println("The language is : " + lang);
					
					JKemik.settings.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "The maximum win must be <= " 
							+ Grid.getBoardSize() + " due to the board size", "Wrong Input",
							JOptionPane.WARNING_MESSAGE);
				}
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
