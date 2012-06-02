/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import view.BoardFrame;


/**
 * @author dalet
 *
 */
public class EmbushListener implements MouseListener{

	private JButton capt;
	public EmbushListener(JButton capture){
		this.setCapt(capture);
	}
	public void mouseClicked(MouseEvent arg0) {
		//Grid.capture = true;
		JKemik.settings_t.setManualCapture(true);
		BoardFrame.repaintGrid();
	}

	
	public void mouseEntered(MouseEvent arg0) {
		
	}

	
	public void mouseExited(MouseEvent arg0) {
		
	}

	
	public void mousePressed(MouseEvent arg0) {
		
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
		
	}
	public void setCapt(JButton capt) {
		this.capt = capt;
	}
	public JButton getCapt() {
		return capt;
	}

}
