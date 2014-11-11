/**
 * 
 */
package Events;

import javax.swing.*;

import api.GTemplate;
import api.STemplate;
import controler.JKemik;

import view.*;

/**
 * @author dalet
 * 
 */
public class ViewEvents {
	public static ColorChangeListenerA p1Listener = new ColorChangeListenerA(
			BoardFrame.pColor1);
	public static ColorChangeListenerB p2Listener = new ColorChangeListenerB(
			BoardFrame.pColor2);
	public static NameListener n1Listener = new NameListener(BoardFrame.pnamelabel1,
			1);
	public static NameListener n2Listener = new NameListener(BoardFrame.pnamelabel2,
			2);
	public static NewGameListener newGameListener = new NewGameListener(
			BoardFrame.Game_status);
	public static GridMouseListener gridListener = new GridMouseListener(
			BoardFrame.grid);
        public static GridAIMouseListener AIgridListener = new GridAIMouseListener(
			BoardFrame.grid);
	public static BoardSizeListener gridSizeListener = new BoardSizeListener(
			BoardFrame.l1);
	public static GameThemeListener gameThemeListener = new GameThemeListener(
			BoardFrame.l2);
	public static SaveButtonListener saveListener = new SaveButtonListener(
			BoardFrame.startG);
	public static SysPrefsListener sysPrefsListener = new SysPrefsListener(
			BoardFrame.settings);
	public static SaveSettingsListener saveSetting = new SaveSettingsListener(
			SettingsPanel.save);
	public static SysPrefsCancelListener cancelSettings = new SysPrefsCancelListener(
			SettingsPanel.cancel);
	public static UndoListener undoListener = new UndoListener(BoardFrame.undo);

	public static PassTurnListener passTurnListener = new PassTurnListener(
			BoardFrame.pass_turn);
	public static AutoCaptureListener autoCaptureListener = new AutoCaptureListener(
			SettingsPanel.auto_capture);
	public static AutoTurnPassAction autoTurnPassListener = new AutoTurnPassAction(
			SettingsPanel.auto_turn_pass);
	public static ExitListener exitlistener = new ExitListener(BoardFrame.exit);
	public static HelpListener helpListener = new HelpListener(BoardFrame.help);
	public static RefreshListener refreshListener = new RefreshListener(
			BoardFrame.refresh);
	// public static WindowEvents windowListener = new
	// WindowEvents(JKemik.view);
	public static ManualSelectionListener manualCaptureButtonListener = new ManualSelectionListener(
			BoardFrame.mouseSelection);
	public static ModeToggleListener modeToggleListener = new ModeToggleListener(
			BoardFrame.mode);
	public static HvsHListener hvsh = new HvsHListener(
			SettingsPanel.humHumButton);
	public static NetworkGameListener network = new NetworkGameListener(
			SettingsPanel.networkButton);
	public static HvsAIListener HvsAI = new HvsAIListener(
			SettingsPanel.humComButton);
	public static ManualCaptureButtonListener captureButtonListener = new ManualCaptureButtonListener(
			BoardFrame.capture);

	public static void hvsAIListener() {
		//if (SettingsPanel.humComButton.getComponentListeners().length == 0) {
			SettingsPanel.humComButton.addMouseListener(HvsAI);
		//}
	}

	public static void networkGameListener() {
		SettingsPanel.networkButton.addMouseListener(network);
	}

	public static void hvsHListener() {
		SettingsPanel.humHumButton.addMouseListener(hvsh);
	}

	public static void modeToggleActionListener() {
		//if (BoardFrame.mode.getComponentListeners().length == 0) {
			BoardFrame.mode.addMouseListener(modeToggleListener);
		//}
	}

	public static void manualSelectionActionListener(JCheckBox manual) {
		//if (manual.getComponentListeners().length == 0) {
			manual.addMouseListener(manualCaptureButtonListener);
		//}
	}

	// public static void windowActionListener() {
	// if (JKemik.view.getComponentListeners().length == 0) {
	// JKemik.view.addComponentListener(windowListener);
	// }
	// }

	public static void refreshListener() {
		//if (BoardFrame.refresh.getComponentListeners().length == 0) {
			BoardFrame.refresh.addMouseListener(refreshListener);
		//}
	}

	public static void sysPrefsListener() {
		//if (BoardFrame.settings.getComponentListeners().length == 0) {
			BoardFrame.settings.addMouseListener(sysPrefsListener);
		//}
	}

	public static void helpListener() {
		//if (BoardFrame.help.getComponentListeners().length == 0) {
			BoardFrame.help.addMouseListener(helpListener);
		//}
	}

	public static void exitListener() {
		//if (BoardFrame.exit.getComponentListeners().length == 0) {
			BoardFrame.exit.addMouseListener(exitlistener);
		//}
	}
	
	public static void captureButtonListener(JButton capture) {
		//if (capture.getComponentListeners().length == 0) {
			capture.addMouseListener(captureButtonListener);
		//}
	}

	public static void onAutoCaptureAction() {
		//if (SettingsPanel.auto_capture.getComponentListeners().length == 0) {
			SettingsPanel.auto_capture.addMouseListener(autoCaptureListener);
		//}
	}

	public static void onAutoPassTurnAction() {
		//if (SettingsPanel.auto_turn_pass.getComponentListeners().length == 0) {
			SettingsPanel.auto_turn_pass.addMouseListener(autoTurnPassListener);
		//}
	}

	public static void passTurnAction(JButton pass) {
		//if (pass.getComponentListeners().length == 0) {
			pass.addMouseListener(passTurnListener);
		//}
	}

	public static void saveSettingsAction() {
		//if (SettingsPanel.save.getComponentListeners().length == 0) {
			SettingsPanel.save.addMouseListener(saveSetting);
		//}
	}

	public static void cancelSettingsAction() {
		//if (SettingsPanel.cancel.getComponentListeners().length == 0) {
			SettingsPanel.cancel.addMouseListener(saveSetting);
		//}
	}

	public static void undoAction(JButton undo) {
		//if (undo.getComponentListeners().length == 0) {
			undo.addMouseListener(undoListener);
		//}
	}

	public static void saveAction(JButton save) {
		//if (save.getComponentListeners().length == 0) {
			save.addMouseListener(saveListener);
		//}
	}

	public static void gridMouseAction(final Grid grid) {
		//if (grid.getComponentListeners().length == 0) {
			grid.addMouseListener(gridListener);
			grid.addMouseMotionListener(gridListener);
		//}
	}

        public static void gridAIMouseAction(final Grid grid) {
		//if (grid.getComponentListeners().length == 0) {
            System.out.println("Setting up AI event listeners...");
			grid.addMouseListener(AIgridListener);
			grid.addMouseMotionListener(AIgridListener);
		//}
	}
	public static void changeColorPanel1Action(final RotateColor panel) {
		//if (panel.getComponentListeners().length == 0) {
			panel.addMouseListener(p1Listener);
		//}
	}

	public static void changeColorPanel2Action(final RotateColor panel) {
		//if (panel.getComponentListeners().length == 0) {
			panel.addMouseListener(p2Listener);
		//}
	}

	public static void setBoardSizeAction(final RotateLabel label) {
		//if (label.getComponentListeners().length == 0) {
			label.addMouseListener(gridSizeListener);
		//}
	}

	public static void setGameThemeAction(final RotateLabel label) {
		//if (label.getComponentListeners().length == 0) {
			label.addMouseListener(gameThemeListener);
		//}
	}

	public static void addPlayer1NameAction(JLabel label) {
		//if (label.getComponentListeners().length == 0) {
			label.addMouseListener(n1Listener);
		//}
	}

	public static void addPlayer2NameAction(JLabel label) {
		//if (label.getComponentListeners().length == 0) {
			label.addMouseListener(n2Listener);
		//}
	}

	public static void newGameEvent() {
		//if (BoardFrame.Game_status.getComponentListeners().length == 0) {
			BoardFrame.Game_status.addMouseListener(newGameListener);
		//}
	}

	public static void uiEventUpdates(STemplate s, GTemplate t) {
		if (s.isGameSetupMode()) {
			newGameEvent();
			sysPrefsListener();
			changeColorPanel1Action(BoardFrame.pColor1);
			changeColorPanel2Action(BoardFrame.pColor2);
			addPlayer1NameAction(BoardFrame.pnamelabel1);
                        
                        if(JKemik.settings_t.isCh()){
                            BoardFrame.pnamelabel2.removeMouseListener(p2Listener);
                        }else{
                            addPlayer2NameAction(BoardFrame.pnamelabel2);
                        }
                        
			setBoardSizeAction(BoardFrame.l1);
			setGameThemeAction(BoardFrame.l2);
			saveAction(BoardFrame.startG);

                        BoardFrame.grid.removeMouseListener(AIgridListener);
                        BoardFrame.grid.removeMouseMotionListener(AIgridListener);
                        BoardFrame.grid.removeMouseListener(gridListener);
                        BoardFrame.grid.removeMouseMotionListener(gridListener);

			// Disable control buttons
			BoardFrame.mouseSelection
					.removeMouseListener(manualCaptureButtonListener);
			BoardFrame.undo.removeMouseListener(undoListener);
			BoardFrame.capture.removeMouseListener(captureButtonListener);
			BoardFrame.pass_turn.removeMouseListener(passTurnListener);
		}

		if (s.isPlayMode()) {
			BoardFrame.pColor1.removeMouseListener(p1Listener);
			BoardFrame.pColor2.removeMouseListener(p2Listener);
			BoardFrame.pnamelabel1.removeMouseListener(n1Listener);
			BoardFrame.pnamelabel2.removeMouseListener(n2Listener);
			BoardFrame.l1.removeMouseListener(gridSizeListener);
			BoardFrame.l2.removeMouseListener(gameThemeListener);
			BoardFrame.startG.removeMouseListener(saveListener);
                        
                        // Resetting grid listener
                        BoardFrame.grid.removeMouseListener(AIgridListener);
                        BoardFrame.grid.removeMouseMotionListener(AIgridListener);
                        BoardFrame.grid.removeMouseListener(gridListener);
                        BoardFrame.grid.removeMouseMotionListener(gridListener);
                        if(JKemik.settings_t.isCh()){
                           gridAIMouseAction(BoardFrame.grid);
                        }else{
                           gridMouseAction(BoardFrame.grid);
                        }

			// Enable control buttons
			sysPrefsListener();
			manualSelectionActionListener(BoardFrame.mouseSelection);
			undoAction(BoardFrame.undo);
			captureButtonListener(BoardFrame.capture);
			passTurnAction(BoardFrame.pass_turn);
                        
		}
		if (s.isSystemSetupMode()) {
			BoardFrame.pColor1.removeMouseListener(p1Listener);
			BoardFrame.pColor2.removeMouseListener(p2Listener);
			BoardFrame.pnamelabel1.removeMouseListener(n1Listener);
			BoardFrame.pnamelabel2.removeMouseListener(n2Listener);
			BoardFrame.l1.removeMouseListener(gridSizeListener);
			BoardFrame.l2.removeMouseListener(gameThemeListener);
			BoardFrame.startG.removeMouseListener(saveListener);
			BoardFrame.settings.removeMouseListener(sysPrefsListener);
		}
	}
}