/**
 * 
 */
package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.RotateLabel;

/**
 * @author dalet
 *
 */
public class GameModeListener implements MouseListener{
	private RotateLabel label;
	
	public GameModeListener(RotateLabel label){
			this.label = label;
	}

	public void mouseClicked(MouseEvent e) {
		this.label.rotateLabel();
	}
	
	public void mouseEntered(MouseEvent e) {
		this.label.highlight();
	}
	
	public void mouseExited(MouseEvent e) {
		this.label.resetBGC();	
	}
	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}