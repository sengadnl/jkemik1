package Events;

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
		if (!BoardFrame.mouseSelection.isSelected()) {
			System.out.println("" + JKemik.game.getCurrentP().getName()+ " > "+
					"Play Flag: " + JKemik.game.getCurrentP().getPlay_flag());
			/*
			 * Pass turn only if mouse was clicked and it's no longer currentP's
			 * turn
			 */
			if (JKemik.game.getCurrentP().getPlay_flag() == 1) {
				JKemik.game.switchPlayTurns();
				//Grid.setCcolor(JKemik.game.getCurrentP().getColor());

			} else {
//				JOptionPane.showMessageDialog(null,
//						"YOU HAVE NOT PLAYED YET!!!",
//						"Ellegal Action", JOptionPane.WARNING_MESSAGE);
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