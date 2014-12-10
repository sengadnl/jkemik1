package Events;

import api.GTemplate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JRadioButton;

import controler.JKemik;

public class NetworkGameListener implements MouseListener{
	private JRadioButton network;
	public NetworkGameListener(JRadioButton network){
		setNetwork(network);
	}
	public JRadioButton getNetwork() {
		return network;
	}

	private void setNetwork(JRadioButton network) {
		this.network = network;
                //JKemik.template = new GTemplate();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JKemik.settings_t.setNet(true);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
