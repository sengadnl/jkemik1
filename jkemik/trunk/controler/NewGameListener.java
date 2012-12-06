/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import api.Point;

import utilities.Tools;
import view.BoardFrame;
import view.Grid;

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
			if(BoardFrame.manual.isSelected()){
				JKemik.game.getCurrentP().setSelected(new ArrayList<Point>());
				Grid.manualc = false;
				BoardFrame.manual.setSelected(false);
				BoardFrame.manual_c.setSelected(false);
				JKemik.settings_t.restaureMemo();
				BoardFrame.updateSettingPanel();
			}
			
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
			
			//Disable control buttons
			BoardFrame.refresh.removeMouseListener(ViewEvents.refreshListener);
			BoardFrame.manual_c.removeMouseListener(ViewEvents.manualCaptureButtonListener);
			BoardFrame.undo.removeMouseListener(ViewEvents.undoListener);
			BoardFrame.pass_turn.removeMouseListener(ViewEvents.passTurnListener);
			
			BoardFrame.enableGameControlPanel();
			
			//Reset game exit label
			BoardFrame.Game_status.setText("NEW");
			BoardFrame.Game_status.setForeground(Color.GREEN);
			
			BoardFrame.boostLabel(BoardFrame.settings);
			
			BoardFrame.showControlButtons();
			BoardFrame.grid.initCursorLocation();
			//Reset grid
			BoardFrame.panel2.repaint();
			BoardFrame.grid.drawn = false;
			BoardFrame.grid.repaint();
			
			BoardFrame.p1panel.initPanelForNewGame(JKemik.template.getP1_name(), BoardFrame.BOARD_COLOR);
			BoardFrame.p2panel.initPanelForNewGame(JKemik.template.getP2_name(), BoardFrame.BOARD_COLOR);

		} else if (response == 1) {
			int res = JOptionPane.showConfirmDialog(null,
					"Exit JKemik?\n", "Question",
					JOptionPane.YES_NO_OPTION);
			if(res == 0){
				//JKemik.writeGame();
				JKemik.writeSettings();
				System.exit(0);
				System.out.println("Exitting ... ");
			}else{
				BoardFrame.setMakingGame(false);
			}
		}
		BoardFrame.manual.setVisible(false);
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
		this.label.setToolTipText("Start new game.");
	}
}