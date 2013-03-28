/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import controler.JKemik;

import utilities.Tools;
import view.BoardFrame;
import view.RotateColor;

/**
 * @author dalet
 *
 */
public class ColorChangeListenerB implements MouseListener{
	private JPanel panel;

	public ColorChangeListenerB(JPanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		((RotateColor) this.panel).rotateColor();
		JKemik.template.setP1_c(this.panel.getBackground());
	}

	public void mouseEntered(MouseEvent e) {
		Color c = this.panel.getBackground();
		c = Tools.fade(c);
		this.panel.setBackground(c);
		//panel.setToolTipText("Click to change the color.");
		panel.setToolTipText(BoardFrame.messages.getString("changeColorHover"));
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
