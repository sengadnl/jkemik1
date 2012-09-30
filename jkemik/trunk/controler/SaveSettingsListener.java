package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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
				String str = SettingsPanel.max_win.getText();
				int maxw = Integer.parseInt(str);
				int size = Grid.squareCount()/2; //Half the number of squares on the board
				if(Tools.isMaxWinLessThanGrid(size, maxw)){
					JKemik.settings_t.setMaxWinVal(maxw);
					BoardFrame.upDateSetting();
					JKemik.settings.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "The maximum win must be <= " 
							+ size + " due to the board size", "Wrong Input",
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
