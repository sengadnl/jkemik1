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
		t.setGridDimesion(sqr);
		//Grid.setSquareSize(sqr);
		
		Grid.setDimension(t.getGridDimension());	
		BoardFrame.settings_p.updateSettingsPanel(JKemik.settings_t);
		BoardFrame.displayGrid(true);
                BoardFrame.grid.repaint();
		BoardFrame.Win.setText((int)(t.getGridDimension().positions()* Globals.MAX_WIN_PERCENT_BOARD) + "");
                t.setMaxWinVal((int)(t.getGridDimension().positions() * Globals.MAX_WIN_PERCENT_BOARD));
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
