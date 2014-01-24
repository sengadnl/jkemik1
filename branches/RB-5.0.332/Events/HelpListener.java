package Events;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
//import javax.swing.JLabel;

import utilities.Manual;

import view.BoardFrame;

public class HelpListener implements MouseListener {
	//private JLabel label;
	private JButton label; 
//	public HelpListener(JLabel label) {
	public HelpListener(JButton label) {
		this.label = label;
	}

	public void mouseClicked(MouseEvent arg0) {
		Manual.openUrl("http://code.google.com/p/jkemik/wiki/GetStarted?tm=6");
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		this.label.setToolTipText(BoardFrame.messages.getString("onlineManualHover"));
	}
}
