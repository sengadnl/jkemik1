/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;

import utilities.ValidateInput;
import view.BoardFrame;
import api.GTemplate;
import api.STemplate;

/**
 * @author dalet
 * 
 */
public class SaveButtonListener implements MouseListener {
	private JButton save;

	public SaveButtonListener(JButton save) {
		this.setSave(save);
	}

	public void mouseClicked(MouseEvent e) {
		Color c1, c2;
		c1 = BoardFrame.pColor1.getBackground();
		c2 = BoardFrame.pColor2.getBackground();

		String str1, str2;
		str1 = BoardFrame.label1.getText();
		str2 = BoardFrame.label2.getText();

		GTemplate t = JKemik.template;
		STemplate s = JKemik.settings_t;
		// Player p1 = null, p2 = null;
		// AbstractGame game = null;

		if (ValidateInput.names(str1, str2)
				&& ValidateInput.validateColors(c1, c2)) {
			s.setPlayMode(true);
			t.setP1_c(c1);
			t.setP2_c(c2);
			t.setP1_name(str1);
			t.setP2_name(str2);
//			Grid.setPcolor(t.getP1_c());
//			Grid.setCcolor(t.getP1_c());

			JKemik.createGame(t, s);

			JKemik.game.setMaxScore(s.getMaxWinVal());//
			
			/*Toggling from manual mode to auto*/
			if (!JKemik.settings_t.getMemo()[0]
					&& !JKemik.settings_t.getMemo()[1]) {
				BoardFrame.manual.setSelected(true);
			} else {
				BoardFrame.manual.setSelected(false);
			}
			BoardFrame.uiLooksUpdate(s, t);
			ViewEvents.uiEventUpdates(s, t);
			int response = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("startGameWill") + str1 + " "
							+ BoardFrame.messages.getString("starGameFirst")
							+ "\n", BoardFrame.messages.getString("question"),
					JOptionPane.YES_NO_OPTION);
			if (response == 1) {
				System.out
						.println("Saving this game ...?????????????????????????????");
				JKemik.game.switchPlayTurns();
				//Grid.setCcolor(JKemik.game.getCurrentP().getColor());
			} else {

			}

			System.out.println(JKemik.game + "\n\n");
			JKemik.writeSettings();
		}
	}

	public void mouseEntered(MouseEvent e) {
		save.setToolTipText(BoardFrame.messages.getString("startGameHover"));
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