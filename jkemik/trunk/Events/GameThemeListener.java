/**
 * 
 */
package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;

import api.GTemplate;
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
		
		GTemplate t = JKemik.template;
		BoardFrame.p1panel.initPanelForNewGame(t.getP1_name(), BoardFrame.BOARD_COLOR);
		BoardFrame.p2panel.initPanelForNewGame(t.getP2_name(), BoardFrame.BOARD_COLOR);
		JKemik.view.repaint();

	}
	public void mouseEntered(MouseEvent e) {
		this.label.highlight();
		//label.setToolTipText("Click to change the theme.");
		label.setToolTipText(BoardFrame.messages.getString("themeHover"));
	}

	public void mouseExited(MouseEvent e) {
		this.label.resetBGC();	
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}