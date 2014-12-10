package Events;

import api.GTemplate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JRadioButton;

import controler.JKemik;

public class HvsHListener implements MouseListener{
	private JRadioButton gtype;
	public HvsHListener(JRadioButton gtype){
		setGtype(gtype);
	}
	
	public JRadioButton getGtype() {
		return gtype;
	}

	private void setGtype(JRadioButton gtype) {
		this.gtype = gtype;
                //JKemik.template = new GTemplate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JKemik.settings_t.setHh(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
