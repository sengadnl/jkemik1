package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;

import view.BoardFrame;

public class ModeToggleListener implements MouseListener{
	public ModeToggleListener(JCheckBox manual){
		
	}

	public void mouseClicked(MouseEvent arg0) {
		if(BoardFrame.manual.isSelected()){
			System.out.println("Manual is selected");
			JKemik.settings_t.setAutoCapture(false);
			JKemik.settings_t.setAutoPass(false);
			BoardFrame.showControlButtons();
			BoardFrame.updateSettingPanel();
		}else{
			System.out.println("Manual is NOT selected");
			JKemik.settings_t.restaureMemo();
			BoardFrame.showControlButtons();
			BoardFrame.updateSettingPanel();
		}
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}