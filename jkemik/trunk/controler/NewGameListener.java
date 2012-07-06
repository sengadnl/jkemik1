/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utilities.Tools;
import view.BoardFrame;

/**
 * @author dalet
 *
 */
public class NewGameListener implements MouseListener{
	private JLabel label; 
	public NewGameListener (JLabel label){
		this.label = label;
	}
	public void mouseClicked(MouseEvent arg0) {
		if(BoardFrame.isMakingGame()){
			return;
		}else{
			BoardFrame.setMakingGame(true);
		}
		int response = JOptionPane.showConfirmDialog(null,
				"Start new Game?\n", "Question",
				JOptionPane.YES_NO_OPTION);
		if (response == 0) {
			// set listeners
			BoardFrame.pColor1.addMouseListener(ViewEvents.p1Listener);
			BoardFrame.pColor2.addMouseListener(ViewEvents.p2Listener);
			BoardFrame.label1.addMouseListener(ViewEvents.n1Listener);
			BoardFrame.label2.addMouseListener(ViewEvents.n2Listener);
			BoardFrame.l1.addMouseListener(ViewEvents.gridSizeListener);
			BoardFrame.l2.addMouseListener(ViewEvents.gameThemeListener);
			BoardFrame.save.addMouseListener(ViewEvents.saveListener);
			BoardFrame.settings.addMouseListener(ViewEvents.saveSettings);
			BoardFrame.grid.removeMouseListener(ViewEvents.gridListener);
			BoardFrame.grid.removeMouseMotionListener(ViewEvents.gridListener);
			BoardFrame.enableGameControlPanel();
			
			//Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			BoardFrame.Game_status.setForeground(Color.GREEN);
			
			BoardFrame.boostLabel(BoardFrame.settings);
			BoardFrame.fadeButton(BoardFrame.pass_turn);
			BoardFrame.fadeButton(BoardFrame.capture);
			BoardFrame.fadeButton(BoardFrame.undo);
			BoardFrame.grid.initCursorLocation();
			//Reset grid
			BoardFrame.panel2.repaint();
			BoardFrame.grid.DRAWN = false;
			BoardFrame.grid.repaint();
			
			BoardFrame.p1panel.initPanelForNewGame("P1", Color.WHITE);
			BoardFrame.p2panel.initPanelForNewGame("P2", Color.WHITE);

		} else if (response == 1) {
			int res = JOptionPane.showConfirmDialog(null,
					"Exit JKemik?\n", "Question",
					JOptionPane.YES_NO_OPTION);
			if(res == 0){
				JKemik.writeSettings();
				System.exit(0);
				System.out.println("Exitting ... ");
			}else{
				BoardFrame.setMakingGame(false);
			}
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
	}
}