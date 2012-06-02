/**
 * 
 */
package controler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import utilities.Tools;
import view.RotateColor;

/**
 * @author dalet
 * 
 */

public class ColorChangeListenerA implements MouseListener {
	private JPanel panel;

	public ColorChangeListenerA(JPanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		((RotateColor) this.panel).rotateColor();
//		Color color = this.panel.getBackground();
//		BoardFrame.p1panel.setBorder(BorderFactory.createLineBorder(color, 1));
//		BoardFrame.p1panel.setBg(color);
//		BoardFrame.p1panel.getPlayer().setForeground(color);
//		Color p1 = BoardFrame.pColor1.getBackground();
		JKemik.template.setP1_c(this.panel.getBackground());
	}

	public void mouseEntered(MouseEvent e) {
		Color c = this.panel.getBackground();
		c = Tools.fade(c);
		this.panel.setBackground(c);
	}

	public void mouseExited(MouseEvent e) {
		Color c = this.panel.getBackground();
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
