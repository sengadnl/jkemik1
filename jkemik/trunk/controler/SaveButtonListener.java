/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import utilities.ValidateInput;
import view.BoardFrame;
import view.Grid;
import api.GTemplate;
import api.Game;
import api.Player;
import api.Point;

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

		//JKemik.template = new GTemplate();
		GTemplate t = JKemik.template;
		Player p1 = null, p2 = null;
		Game game = null;

		if (ValidateInput.names(str1, str2)
				&& ValidateInput.validateColors(c1, c2)) {
			t.setP1_c(c1);
			t.setP2_c(c2);
			t.setP1_name(str1);
			t.setP2_name(str2);
			Grid.setPcolor(t.getP1_c());
			Grid.setCcolor(t.getP1_c());
			p1 = new Player(c1, str1);
			p2 = new Player(c2, str2);
			game = new Game(p1, p2);
			JKemik.game = game;
		
			JKemik.game.setMaxScore(JKemik.settings_t.getMaxWinVal());
			
			BoardFrame.desableGameControlPanel();
			BoardFrame.pColor1.removeMouseListener(ViewEvents.p1Listener);
			BoardFrame.pColor2.removeMouseListener(ViewEvents.p2Listener);
			BoardFrame.label1.removeMouseListener(ViewEvents.n1Listener);
			BoardFrame.label2.removeMouseListener(ViewEvents.n2Listener);
			BoardFrame.l1.removeMouseListener(ViewEvents.gridSizeListener);
			BoardFrame.l2.removeMouseListener(ViewEvents.gameThemeListener);
			BoardFrame.settings.removeMouseListener(ViewEvents.saveSettings);
			BoardFrame.Game_status.setText("END");
			BoardFrame.Game_status.setForeground(Color.RED);
			
			BoardFrame.fadeLabel(BoardFrame.settings);
			BoardFrame.fadeButton(BoardFrame.save);
			BoardFrame.boostButton(BoardFrame.pass_turn);
			BoardFrame.boostButton(BoardFrame.capture);
			BoardFrame.boostButton(BoardFrame.undo);
			BoardFrame.print_point.setText("" + (new Point(0, 0)).toString());
			String p1n = t.getP1_name();
			String p2n = t.getP2_name();
			Color p1c = t.getP1_c();
			Color p2c = t.getP2_c();

			BoardFrame.p1panel.initPanelForNewGame(p1n, p1c);
			BoardFrame.p2panel.initPanelForNewGame(p2n, p2c);
			BoardFrame.save.removeMouseListener(ViewEvents.saveListener);
			
			int response = JOptionPane.showConfirmDialog(null, "Will " + str1
					+ " play first?\n", "Question",
					JOptionPane.YES_NO_OPTION);
			if (response == 1) {
				game.switchPlayTurns();
				Grid.setCcolor(game.getCurrentP().getColor());
			} else {
				
			}
			BoardFrame.grid.addMouseListener(ViewEvents.gridListener);
			BoardFrame.grid.addMouseMotionListener(ViewEvents.gridListener);
			if(JKemik.settings_t.isAutoCapture()){
				BoardFrame.capture.setVisible(false);
			}else{
				BoardFrame.capture.setVisible(true);
			}
			
			if(JKemik.settings_t.isAutoPass()){
				BoardFrame.undo.setVisible(false);
				BoardFrame.pass_turn.setVisible(false);
			}else{
				BoardFrame.undo.setVisible(true);
				BoardFrame.pass_turn.setVisible(true);
			}
			BoardFrame.setMakingGame(false);
			JKemik.settings.setVisible(false);
			JKemik.writeSettings();
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