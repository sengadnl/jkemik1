/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.BoardFrame;
import view.RotateLabel;

/**
 * @author dalet
 *
 */
public class GameThemeListener implements MouseListener{

	private RotateLabel label;
	public GameThemeListener(RotateLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent e) {
		BoardFrame.grid.drawn = true;
		this.label.rotateLabel();
		String str = this.label.getActiveLabel();
		JKemik.view.setTheme(str);
		JKemik.settings_t.setTheme(str);
		BoardFrame.panel2.repaint();
		// change the grid size
		if (BoardFrame.grid.drawn) {
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
		}
	}
	public void mouseEntered(MouseEvent e) {
		this.label.highlight();
	}

	public void mouseExited(MouseEvent e) {
		this.label.resetBGC();	
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}