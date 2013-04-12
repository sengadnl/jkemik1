/**
 * 
 */
package Events;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;
import api.GTemplate;
import utilities.Globals;
import utilities.Tools;
import view.BoardFrame;
import view.Grid;
import view.RotateLabel;
/**
 * @author Dalet
 *
 */
public class BoardSizeListener implements MouseListener{

	private RotateLabel label;
	public BoardSizeListener(RotateLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent e) {
		GTemplate t = JKemik.template;
		this.label.rotateLabel();
		String s = this.label.getActiveLabel();
		//Grid.setSquareSize(Grid.getSqrSize(s));
		Grid.setSquareSize(JKemik.settings_t.getSqrSize(s));
		t.setG_size(Grid.getSquareSize());
		BoardFrame.middle_container.repaint();
		
		// change the grid size
		if (BoardFrame.grid.drawn) {
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
		}
		BoardFrame.Win.setText((int)(Grid.getBoardSize()* Globals.MAX_WIN) + "");
		JKemik.settings_t.setMaxWinVal((int)(Grid.getBoardSize() * Globals.MAX_WIN));
	}

	
	public void mouseEntered(MouseEvent e) {
		Color c = Tools.boost(BoardFrame.BOARD_COLOR,Globals.LABEL_VARIANT);
		this.label.setForeground(Tools.fade(c));
		//label.setToolTipText("Click to change board size.");
		label.setToolTipText(BoardFrame.messages.getString("boardSizeHover"));
		//boardSizeHover
	}
	
	public void mouseExited(MouseEvent e) {
		//this.label.resetBGC();
		//Color c = Tools.boost(BoardFrame.BOARD_COLOR,Globals.LABEL_VARIANT);
		this.label.setForeground(Color.WHITE);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
