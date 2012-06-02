/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import utilities.Tools;
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
//		Color color = this.panel.getBackground();
//		BoardFrame.p2panel.setBorder(BorderFactory.createLineBorder(color, 1));
//		BoardFrame.p2panel.setBg(color);
//		BoardFrame.p2panel.getPlayer().setForeground(color);
//		Color p1 = BoardFrame.pColor2.getBackground();
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
