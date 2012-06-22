/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


import view.BoardFrame;
/**
 * @author dalet
 * 
 */
public class NameListener implements MouseListener {

	private JLabel lab;
	private int one;

	public NameListener(JLabel label, int one) {
		this.lab = label;
		this.one = one;
		if (one == 1) {
			this.lab = BoardFrame.label1;
		} else {
			this.lab = BoardFrame.label2;
		}
	}

	public void mouseClicked(MouseEvent e) {
		String name = JOptionPane.showInputDialog("Player Name <= 5 chars");
		try{
		this.lab.setText(name.toUpperCase());
		name = BoardFrame.getLabel1().getText();
		
		if (this.one == 1) {
			JKemik.template.setP1_name(name);
		} else {
			JKemik.template.setP1_name(name);
		}
		}catch(Exception ex){
			System.out.println("Exception in NameListener : " + ex.getMessage());
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
		if(this.one == 1){
		BoardFrame.highlightP1();
		}else{BoardFrame.highlightP2();}
	}

	public void mouseExited(MouseEvent e) {
		BoardFrame.resetBGCP();
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
