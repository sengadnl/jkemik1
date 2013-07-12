/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;

import api.Point;

import utilities.Tools;
import view.BoardFrame;

/**
 * @author dalet
 * 
 */
public class NewGameListener implements MouseListener {
	private JButton label;

	public NewGameListener(JButton label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (JKemik.settings_t.isSystemSetupMode()) {
			int res = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("exitSysPrefs"),
					BoardFrame.messages.getString("warning"),
					JOptionPane.YES_OPTION);// "Exit System Preferences?","Warning"
			if (res == 0) {
				if (BoardFrame.isMakingGame()) {
					return;
				} else {
					BoardFrame.setMakingGame(true);
				}
				JKemik.settings_t.setGameSetupMode(true);
				BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
				ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
			} else {
				return;
			}
		}
		if (BoardFrame.isMakingGame()) {
			return;
		} else {
			BoardFrame.setMakingGame(true);
		}
		int response = JOptionPane.showConfirmDialog(null,
				BoardFrame.messages.getString("startNewGame") + "\n",
				BoardFrame.messages.getString("question"),
				JOptionPane.YES_NO_OPTION);
		if (response == 0) {
			// set listeners
			JKemik.settings_t.setGameSetupMode(true);
			JKemik.game.getCurrentP().setSelected(new ArrayList<Point>());
		} else if (response == 1) {
			int res = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("exitGame") + "\n",
					BoardFrame.messages.getString("question"),
					JOptionPane.YES_NO_OPTION);
			if (res == 0) {
				// JKemik.writeGame();
				JKemik.writeSettings();
				System.exit(0);
				System.out.println("Exitting ... ");
			} else {
				BoardFrame.setMakingGame(false);
			}
		}
		JKemik.settings_t.restaureMemo();
		ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
		BoardFrame.mode.setVisible(false);
	}

	public void mouseExited(MouseEvent arg0) {
		Color color;
		color = Tools.boost(this.label.getForeground());
		this.label.setForeground(color);
		// BoardFrame.displayGrid(true);
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
		// this.label.setToolTipText("Start new game.");
		this.label.setToolTipText(BoardFrame.messages
				.getString("startNewGamelHover"));
	}
}