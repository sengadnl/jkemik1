/**
 * 
 */
package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controler.JKemik;

import api.AbstractGame;

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
		AbstractGame game = JKemik.game;
//		System.out.println("entering undo > flag = " 
//				+ game.getCurrentP().getPlay_flag() + "\nturn = " + 
//				game.getCurrentP().isTurn());
		if(game.getCurrentP().getPlay_flag() == 1){
			if (!game.getCurrentP().isTurn()) {
				Grid.undo = true;
				if(!Grid.manualc){
					game.getCurrentP().setTurn(true);
				}
				BoardFrame.grid.repaint();
			}
		}else{
			if(Grid.manualc){
				Grid.undo = true;
				System.out.println("Trying to undo selection...");
				BoardFrame.grid.repaint();
				return;
			}
			JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("ilUndo")+"\n", BoardFrame.messages.getString("ilAction"), JOptionPane.WARNING_MESSAGE);
		}
		BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
		BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
	}

	public void mouseEntered(MouseEvent arg0) {
		//undo.setToolTipText("Click to undo");
		undo.setToolTipText(BoardFrame.messages.getString("undoHover"));
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