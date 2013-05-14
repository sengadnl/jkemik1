/**
 * 
 */
package Events;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.JKemik;
import utilities.Tools;
import view.BoardFrame;
import view.RotateColor;

/**
 * @author dalet
 * 
 */

public class ColorChangeListenerA implements MouseListener {
	private RotateColor panel;

	public ColorChangeListenerA(RotateColor panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		this.panel.rotateColor();
		int i = this.panel.getColorIndex();
		JKemik.template.setP1_c(this.panel.rotateColor(i));
	}

	public void mouseEntered(MouseEvent e) {
		Color c = JKemik.template.getP1_c();
		c = Tools.fade(c);
		this.panel.setBackground(c);
		//panel.setToolTipText("Click to change the color.");
		panel.setToolTipText(BoardFrame.messages.getString("changeColorHover"));
	}

	public void mouseExited(MouseEvent e) {
		Color c = JKemik.template.getP1_c();
		c = Tools.boost(c);
		this.panel.setBackground(c);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
}
