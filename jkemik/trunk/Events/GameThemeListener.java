/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;
import utilities.Tools;
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
		//BoardFrame.panel2.repaint();
		// change the grid size
		if (BoardFrame.grid.drawn) {
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
		}
		BoardFrame.uiLooksUpdate(JKemik.settings_t,JKemik.template);
		JKemik.view.repaint();

	}
	public void mouseEntered(MouseEvent e) {
		Color c = BoardFrame.BOARD_COLOR;
		this.label.setForeground(Tools.fade(c));
		//label.setToolTipText("Click to change the theme.");
		label.setToolTipText(BoardFrame.messages.getString("themeHover"));
	}

	public void mouseExited(MouseEvent e) {
		Color c = BoardFrame.BOARD_COLOR;
		this.label.setForeground(c);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}