/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utilities.Tools;


/**
 * @author dalet
 *
 */
public class ExitListener implements MouseListener{
	private JLabel label; 
	public ExitListener (JLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent arg0) {
		int response = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to exit the Game?\n", "Question",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (response == 0) {
			// Save settings
			JKemik.writeSettings();
			// Save current Game
			System.exit(0);
			//Application.setDone();
		} else if (response == 1) {
			// Save settings
			System.exit(0);
		} else {
			
		}
	}
	
	public void mouseExited(MouseEvent arg0) {
		Color color;
		color = Tools.boost(this.label.getForeground());
		this.label.setForeground(color);
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
	public void mouseEntered(MouseEvent arg0) {
		Color color;
		color = Tools.fade(this.label.getForeground());
		this.label.setForeground(color);
	}
}