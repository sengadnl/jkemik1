package Events;

import api.AbstractGame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;
import view.BoardFrame;
import view.Grid;


/**
 * @author dalet
 * 
 */
public class PassTurnListener implements MouseListener {
	private JButton pass;

	public PassTurnListener(JButton pass) {
		this.setPass(pass);
	}

	public JButton getPass() {
		return pass;
	}

	public void setPass(JButton pass) {
		this.pass = pass;
	}

	public void mouseClicked(MouseEvent e) {
		AbstractGame game;
                game = JKemik.getGame();
		if (!BoardFrame.mouseSelection.isSelected()) {
			System.out.println("" + game.getCurrentP().getName()+ " > "+
					"Play Flag: " + game.getCurrentP().getPlay_flag());
			/*
			 * Pass turn only if mouse was clicked and it's no longer currentP's
			 * turn
			 */
			if (game.getCurrentP().getPlay_flag() == 1) {
				game.switchPlayTurns();
			} else {
				JOptionPane.showMessageDialog(null,
						BoardFrame.messages.getString("ilPass"),
						BoardFrame.messages.getString("ilAction"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		//pass.setToolTipText("Click to pass the turn.");
		pass.setToolTipText(BoardFrame.messages.getString("passHover"));
		Grid.refresh = true;
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}