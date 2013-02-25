package Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class HvsAIListener implements MouseListener {
	private JRadioButton gtype;
	
	public HvsAIListener(JRadioButton gtype) {
		setNetwork(gtype);
	}
	
	public JRadioButton getNetwork() {
		return gtype;
	}

	public void setNetwork(JRadioButton network) {
		this.gtype = network;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null, "HvsAI");
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
