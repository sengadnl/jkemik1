package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class NetworkGameListener implements MouseListener{
	private JRadioButton network;
	public NetworkGameListener(JRadioButton network){
		setNetwork(network);
	}
	public JRadioButton getNetwork() {
		return network;
	}

	public void setNetwork(JRadioButton network) {
		this.network = network;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JOptionPane.showMessageDialog(null, "Network");
		
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