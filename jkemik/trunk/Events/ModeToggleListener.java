package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;

import controler.JKemik;

import view.BoardFrame;

public class ModeToggleListener implements MouseListener{
	private JCheckBox manual;
	public ModeToggleListener(JCheckBox manual){
		setManual(manual);
	}

	public JCheckBox getManual() {
		return manual;
	}

	public void setManual(JCheckBox manual) {
		this.manual = manual;
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
		//manual.setToolTipText("Check to enable temporary manual mode.");
		manual.setToolTipText(BoardFrame.messages.getString("manualMode"));
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
