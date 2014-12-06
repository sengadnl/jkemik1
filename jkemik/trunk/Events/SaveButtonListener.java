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
import controler.AgentMoveRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
             System.out.println("Starting a new Game");
		Color c1, c2;
                String str1, str2;
                GTemplate t;
                STemplate s;
		c1 = JKemik.template.getP1_c();
		c2 = JKemik.template.getP2_c();

                t = JKemik.template;
		s = JKemik.settings_t;
		
		str1 = JKemik.template.getP1_name();
		str2 = JKemik.template.getP2_name();

		if (ValidateInput.names(str1, str2)
				&& ValidateInput.validateColors(c1, c2)) {
			s.setPlayMode(true);
			t.setP1_c(c1);
			t.setP2_c(c2);
			t.setP1_name(str1);
			t.setP2_name(str2);
			JKemik.createGame(t, s);

			JKemik.getGame().setMaxScore(s.getMaxWinVal());//

			/* Toggling from manual mode to auto */
			if (!JKemik.settings_t.getMemo()[0]
					&& !JKemik.settings_t.getMemo()[1]) {
				BoardFrame.mode.setSelected(true);
			} else {
				BoardFrame.mode.setSelected(false);
			}
			BoardFrame.addstarterPoints(s.getStarterPoints());
			BoardFrame.updateBoardStatus();
			ViewEvents.uiEventUpdates(s, t);

			int response = JOptionPane.showConfirmDialog(null,
					BoardFrame.messages.getString("startGameWill") + str1 + " "
							+ BoardFrame.messages.getString("starGameFirst")
							+ "\n", BoardFrame.messages.getString("question"),
					JOptionPane.YES_NO_OPTION);
			if (response == 1) {
                            JKemik.getGame().switchPlayTurns();
                            if(JKemik.settings_t.isCh()){
                                AgentMoveRunnable thread = new AgentMoveRunnable();
                                Thread ht = new Thread(thread);
                                ExecutorService pool = Executors.newFixedThreadPool(1);
                                pool.execute(ht);   
                            }
                        }
                        BoardFrame.uiLooksUpdate(s, t);
			JKemik.writeTemplates();
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