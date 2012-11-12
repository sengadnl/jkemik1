/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import api.Game;

import view.BoardFrame;
import view.Grid;

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
		Game game = JKemik.game;
		System.out.println("entering undo > flag = " 
				+ game.getCurrentP().getPlay_flag() + "\nturn = " + 
				game.getCurrentP().isTurn());
		if(game.getCurrentP().getPlay_flag() == 1){
			if (!game.getCurrentP().isTurn()) {
				Grid.undo = true;
				BoardFrame.grid.repaint();
				BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
				BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
				if(!Grid.manualc){
					game.getCurrentP().setTurn(true);
				}
				
			}
		}else{
			JOptionPane.showMessageDialog(null, "NOTHING TO UNDOING IT\n","Illegal Action", JOptionPane.WARNING_MESSAGE);
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