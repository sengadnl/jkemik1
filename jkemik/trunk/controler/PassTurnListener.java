package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
		
		/* Pass turn on if mouse was clicked and it's no longer currentP's turn */
		if (JKemik.game.getCurrentP().getPlay_flag() == 1) {
			JKemik.game.switchPlayTurns();
			Grid.setCcolor(JKemik.game.getCurrentP().getColor());
		} else {
			JOptionPane.showMessageDialog(null,
					"YOU MUST PLAY BEFORE PASSING A TURN!!!", "Ellegal Action",
					JOptionPane.WARNING_MESSAGE);
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
}