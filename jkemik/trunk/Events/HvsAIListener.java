package Events;

import api.GTemplate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JRadioButton;

import controler.JKemik;


public class HvsAIListener implements MouseListener {
	private JRadioButton gtype;
	
	public HvsAIListener(JRadioButton gtype) {
		setNetwork(gtype);
	}
	
	public JRadioButton getNetwork() {
		return gtype;
	}

	private void setNetwork(JRadioButton network) {
		this.gtype = network;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JKemik.settings_t.setCh(true);
                //JKemik.template = new GTemplate();
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
