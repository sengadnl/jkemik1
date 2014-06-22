/**
 * 
 */
package Events;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controler.JKemik;

import utilities.Tools;
import utilities.ValidateInput;
import view.BoardFrame;

/**
 * @author dalet
 * 
 */
public class NameListener implements MouseListener {

	private JLabel lab;
	
	/**Identifies which player name we are dealing with*/
	private int one;

	public NameListener(JLabel label, int one) {
		this.lab = label;
		this.one = one;

		if (one == 1) {
			this.lab = BoardFrame.pnamelabel1;
		} else {
			this.lab = BoardFrame.pnamelabel2;
		}
	}

	public void mouseClicked(MouseEvent e) {
		String name = JOptionPane.showInputDialog(BoardFrame.messages
				.getString("validatePlayerName"));
		try {
			if (ValidateInput.nameLength(name)) {
				this.lab.setText(name.toUpperCase());
				if (this.one == 1) {
					JKemik.template.setP1_name(name);
				} else {
					JKemik.template.setP2_name(name);
				}
			}
		} catch (Exception ex) {
			System.out
					.println("Exception in NameListener : " + ex.getMessage());
		}
	}

	/**
	 * @return the lab
	 */
	public JLabel getLab() {
		return this.lab;
	}

	/**
	 * @param lab
	 *            the lab to set
	 */
	public void setLab(JLabel lab) {
		this.lab = lab;
	}

	public void mouseEntered(MouseEvent e) {
		if (this.one == 1) {
			Color c = JKemik.template.getP1_c();
			BoardFrame.pnamelabel1.setForeground(Tools.fade(c));
		} else {
			Color c = JKemik.template.getP2_c();
			BoardFrame.pnamelabel2.setForeground(Tools.fade(c));
		}
		lab.setToolTipText(BoardFrame.messages.getString("playerNameHover"));
	}

	public void mouseExited(MouseEvent e) {
		BoardFrame.pnamelabel1.setForeground(Color.WHITE);
		BoardFrame.pnamelabel2.setForeground(Color.WHITE);
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
