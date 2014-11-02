/**
 * 
 */
package Events;

import api.AbstractGame;
import api.Point;
import controler.JKemik;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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

        @Override
	public void mouseClicked(MouseEvent arg0) {
		AbstractGame game = JKemik.getGame();
		if(game.getCurrentP().getPlay_flag() == 1){
			if (!game.getCurrentP().isTurn()) {
				
				if(!Grid.manualc){
					game.getCurrentP().setTurn(true);
				}
				//
                                Grid.setRefresh(true);
                                BoardFrame.displayGrid(true);
                                Point temp = game.getLastp();
                                //BoardFrame.grid.repaint();
                                Grid.undo = true;
                                BoardFrame.grid.repaint((int)temp.getXC() - (int)Grid.squareSize * 2, (int)temp.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
			}
		}else{
                    if(Grid.manualc){
                        System.out.println("Trying to undo selection...");
                        Grid.setRefresh(true);
                        BoardFrame.displayGrid(true);
                        Point temp = game.getLastp();
                        BoardFrame.grid.repaint((int)temp.getXC() - (int)Grid.squareSize * 2, (int)temp.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
                        Grid.undo = true;
                        return;
                    }
                    JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("ilUndo")+"\n", BoardFrame.messages.getString("ilAction"), JOptionPane.WARNING_MESSAGE);
		}
		BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
		BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
	}

        @Override
	public void mouseEntered(MouseEvent arg0) {
		undo.setToolTipText(BoardFrame.messages.getString("undoHover"));
	}

        @Override
	public void mouseExited(MouseEvent arg0) {

	}

        @Override
	public void mousePressed(MouseEvent arg0) {

	}

        @Override
	public void mouseReleased(MouseEvent arg0) {

	}

	private void setUndo(JButton undo) {
		this.undo = undo;
	}

	public JButton getUndo() {
		return undo;
	}
}