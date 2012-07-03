/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.BoardFrame;
//import view.Grid;

/**
 * @author dalet
 * 
 */
public class UndoListener implements MouseListener {
	private JButton undo;

	public UndoListener(JButton undo) {
		this.setUndo(undo);
	}

	public void mouseClicked(MouseEvent arg0) {
		if(JKemik.game.getCurrentP().getPlay_flag() == 1){
			if (!JKemik.game.getCurrentP().isTurn()) {
				//Grid.undo = true;
				BoardFrame.repaintGrid();
				JKemik.game.getCurrentP().setTurn(true);
			}
		}else{
			JOptionPane.showMessageDialog(null, "YOU MUST DO SOMETHING BEFORE UNDOING IT\n" +
					"PLEASE PLAY FIRST!!!", "Ellegal", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public void setUndo(JButton undo) {
		this.undo = undo;
	}

	public JButton getUndo() {
		return undo;
	}
}
