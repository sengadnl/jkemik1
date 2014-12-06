/**
 * 
 */
package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controler.JKemik;
import utilities.Globals;
import utilities.Tools;
import view.BoardFrame;
import view.Grid;

/**
 * @author dalet
 * 
 */
public class ExitListener implements MouseListener {
	// private JLabel label;
	private JButton label;

	// public ExitListener (JLabel label){
	public ExitListener(JButton label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
            System.out.println("exit clicked");
		int response = JOptionPane.showConfirmDialog(null,
				BoardFrame.messages.getString("exitMessage") + "\n",
				BoardFrame.messages.getString("question"),
				JOptionPane.YES_OPTION);
		if (response == 0) {
			if (JKemik.settings_t.isGameSetupMode()
					|| JKemik.settings_t.isSystemSetupMode()) {
				JKemik.settings_t.setGameSetupMode(true);
				JKemik.removeGameObj();
			} else if (JKemik.getGame() != null
					&& JKemik.getGame().getCollection().isEmpty()) {
				JKemik.settings_t.setGameSetupMode(true);
				JKemik.removeGameObj();
			} else {
				JKemik.writeGame();
				JKemik.writeTemplates();
			}
			// Save current Game
			System.exit(0);
			// Application.setDone();
		} else {
			//
		}
	}

	public void mouseExited(MouseEvent arg0) {
		this.label.setForeground(Globals.EXIT_BUTTON_FGCOLOR);
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
		this.label.setForeground(Tools.fade(Globals.EXIT_BUTTON_FGCOLOR, 20));
		// this.label.setToolTipText("Exit game");
		this.label.setToolTipText(BoardFrame.messages
				.getString("exitGameHover"));
	}
}