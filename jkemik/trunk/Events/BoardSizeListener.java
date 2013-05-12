/**
 * 
 */
package Events;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;
import api.STemplate;
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
		STemplate t = JKemik.settings_t;
		this.label.rotateLabel();
		String s = this.label.getActiveLabel();
		double sqr = t.getSqrSize(s);
		Grid.setSquareSize(sqr);
		t.setGridDimesion(sqr);
		Grid.setDimension(t.getGridDimesion());
		System.out.println("Square Size: " + sqr + "" +
				"\nTemplate Grid dim : " + Grid.getDimension());
		// change the grid size
		if (BoardFrame.grid.drawn) {
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
		}
		BoardFrame.Win.setText((int)(Grid.getBoardSize()* Globals.MAX_WIN) + "");
		t.setMaxWinVal((int)(Grid.getBoardSize() * Globals.MAX_WIN));
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
