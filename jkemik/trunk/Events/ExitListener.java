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
import view.BoardFrame;


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
				JOptionPane.YES_OPTION);
		if (response == 0) {
			//STemplate t = JKemik.settings_t;
			//t.setMemo(t.isAutoCapture(), t.isAutoPass());
			JKemik.settings_t.restaureMemo();
			// Save settings
			//JKemik.writeGame();
			JKemik.writeSettings();
			// Save current Game
			System.exit(0);
			//Application.setDone();
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
		//this.label.setToolTipText("Exit game");
		this.label.setToolTipText(BoardFrame.messages.getString("exitGameHover"));
	}
}