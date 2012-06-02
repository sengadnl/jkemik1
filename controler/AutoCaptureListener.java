package controler;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import view.RotateLabel;

public class AutoCaptureListener implements MouseListener{
	private RotateLabel label;
	public AutoCaptureListener(RotateLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent e) {
		this.label.rotateLabel();
		String str = this.label.getActiveLabel();
		System.out.println("Auto Cap: active label is: " + str);
		if (str.equals("ON")) {
			JKemik.settings_t.setAutoCapture(true);
		} else {
			JKemik.settings_t.setAutoCapture(false);
		}
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
