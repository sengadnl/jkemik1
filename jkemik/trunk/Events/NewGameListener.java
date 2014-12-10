/**
 * 
 */
package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;
import static controler.JKemik.template;

import utilities.Globals;
import utilities.Tools;
import view.BoardFrame;
import view.Grid;

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
			JKemik.createGame( JKemik.template,JKemik.settings_t);
		} else if (response == 1) {
			int res = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("exitGame") + "\n",
					BoardFrame.messages.getString("question"),
					JOptionPane.YES_NO_OPTION);
			if (res == 0) {
				JKemik.writeGame();
                                System.err.println("Writing game template to disk...");
                                System.err.println("TEMPLATE: \n" + template.toString());
				JKemik.writeTemplates();
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
		this.label.setForeground(Globals.NEWG_BUTTON_FRCOLOR);
		if (!JKemik.settings_t.isSystemSetupMode()) {
			Grid.refresh = true;
		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent arg0) {
		this.label.setForeground(Tools.fade(Globals.NEWG_BUTTON_FRCOLOR, 20));
		// this.label.setToolTipText("Start new game.");
		this.label.setToolTipText(BoardFrame.messages
				.getString("startNewGamelHover"));
	}
}