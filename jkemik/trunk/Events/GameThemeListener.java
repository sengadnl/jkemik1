/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;
import utilities.Globals;
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
		//JKemik.view.setTheme(str);
		JKemik.view.initTheme(str);
		// change the grid size
                BoardFrame.displayGrid(true);
                BoardFrame.grid.repaint();
		BoardFrame.uiLooksUpdate(JKemik.settings_t,JKemik.template);
	}
	
	public void mouseEntered(MouseEvent e) {
		Color c = Tools.boost(BoardFrame.BOARD_COLOR,Globals.LABEL_VARIANT);
		this.label.setForeground(Tools.fade(c));
		//label.setToolTipText("Click to change the theme.");
		label.setToolTipText(BoardFrame.messages.getString("themeHover"));
	}

	public void mouseExited(MouseEvent e) {
		//Color c = Tools.boost(BoardFrame.BOARD_COLOR,Globals.LABEL_VARIANT);
		this.label.setForeground(Color.WHITE);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}