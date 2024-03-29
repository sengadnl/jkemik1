/**
 * 
 */
package controler;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import api.GTemplate;
import view.BoardFrame;
import view.Grid;
import view.RotateLabel;
/**
 * @author dalet
 *
 */
public class BoardSizeListener implements MouseListener{

	private RotateLabel label;
	public BoardSizeListener(RotateLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent e) {
		this.label.rotateLabel();
		GTemplate t = JKemik.template;
		String s = this.label.getActiveLabel();
		Grid.setSquareSize(Grid.getSqrSize(s));
		t.setG_size(Grid.getSquareSize());
		BoardFrame.panel2.repaint();
		// change the grid size
		if (BoardFrame.grid.DRAWN) {
			BoardFrame.grid.DRAWN = false;
			BoardFrame.grid.repaint();
		}
	}

	
	public void mouseEntered(MouseEvent e) {
		this.label.highlight();
	}
	
	public void mouseExited(MouseEvent e) {
		this.label.resetBGC();
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
