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
	public static ColorChangeListenerA p1Listener = new ColorChangeListenerA(BoardFrame.pColor1);
	public static ColorChangeListenerB p2Listener = new ColorChangeListenerB(BoardFrame.pColor2);
	public static NameListener n1Listener = new NameListener(BoardFrame.label1, 1);
	public static NameListener n2Listener = new NameListener(BoardFrame.label2, 2);
	public static NewGameListener newGameListener = new NewGameListener(BoardFrame.Game_status);
	public static GridMouseListener gridListener = new GridMouseListener(BoardFrame.grid);
	public static BoardSizeListener gridSizeListener = new BoardSizeListener(BoardFrame.l1);
	public static GameThemeListener gameThemeListener = new GameThemeListener(BoardFrame.l2);
	public static SaveButtonListener saveListener = new SaveButtonListener(BoardFrame.startG);
	public static SysPrefsListener sysPrefsListener = new SysPrefsListener(BoardFrame.settings);
	public static SaveSettingsListener saveSetting = new SaveSettingsListener(SettingsPanel.save);
	public static UndoListener undoListener = new UndoListener(BoardFrame.undo);

	public static PassTurnListener passTurnListener = new PassTurnListener(BoardFrame.pass_turn);
	public static AutoCaptureListener autoCaptureListener = new AutoCaptureListener(SettingsPanel.auto_capture);
	public static AutoTurnPassAction autoTurnPassListener = new AutoTurnPassAction(SettingsPanel.auto_turn_pass);
	public static ExitListener exitlistener = new ExitListener(BoardFrame.exit);
	public static HelpListener helpListener = new HelpListener(BoardFrame.help);
	public static RefreshListener refreshListener = new RefreshListener(BoardFrame.refresh);
	public static WindowEvents windowListener = new WindowEvents(JKemik.view);
	public static ManualSelectionListener manualCaptureButtonListener = new ManualSelectionListener(BoardFrame.manual_c);	
	public static ModeToggleListener modeToggleListener = new ModeToggleListener(BoardFrame.manual);
	public static HvsHListener hvsh = new HvsHListener(SettingsPanel.humHumButton);
	public static NetworkGameListener network = new NetworkGameListener(SettingsPanel.networkButton);
	public static HvsAIListener HvsAI = new HvsAIListener(SettingsPanel.humComButton);
	
	public static void hvsAIListener(){
		SettingsPanel.humComButton.addMouseListener(HvsAI);
	}
	
	public static void networkGameListener(){
		SettingsPanel.networkButton.addMouseListener(network);
	}
	
	public static void hvsHListener(){
		SettingsPanel.humHumButton.addMouseListener(hvsh);
	}
	public static void modeToggleActionListener(){
		BoardFrame.manual.addMouseListener(modeToggleListener);
	}
	public static void manualSelectionActionListener(JCheckBox manual){
		manual.addMouseListener(manualCaptureButtonListener);
	}
	public static void windowActionListener(){
		JKemik.view.addComponentListener(windowListener);
	}
	
	public static void refreshListener(){
		BoardFrame.refresh.addMouseListener(refreshListener);
	}
	public static void helpListener(){
		BoardFrame.help.addMouseListener(helpListener);
	}
	
	public static void exitListener(){
		BoardFrame.exit.addMouseListener(exitlistener);
	}
	public static void onAutoCaptureAction(){
		SettingsPanel.auto_capture.addMouseListener(autoCaptureListener);
	}
	public static void onAutoPassTurnAction(){
		SettingsPanel.auto_turn_pass.addMouseListener(autoTurnPassListener);
	}

	public static void passTurnAction(JButton pass){
		pass.addMouseListener(passTurnListener);
	}
	public static void saveSettingsAction(){
		SettingsPanel.save.addMouseListener(saveSetting);
	}
	public static void settingsLabelAction(){
		BoardFrame.settings.addMouseListener(sysPrefsListener);
	}

	public static void undoAction(JButton undo){
		undo.addMouseListener(undoListener);
	}
	public static void saveAction(JButton save) {
		save.addMouseListener(saveListener);
	}
	public static void gridMouseAction(final Grid grid) {
		grid.addMouseListener(gridListener);
		grid.addMouseMotionListener(gridListener);
	}

	
	public static void changeColorPanel1Action(final RotateColor panel) {
		panel.addMouseListener(p1Listener);
	}
	
	public static void changeColorPanel2Action(final RotateColor panel) {
		panel.addMouseListener(p2Listener);
	}
	public static void setBoardSizeAction(final RotateLabel label) {
		label.addMouseListener(gridSizeListener);
	}
	
	public static void setGameThemeAction(final RotateLabel label) {
		label.addMouseListener(gameThemeListener);
	}
	
	public static void addPlayer1NameAction(JLabel labe1) {
		labe1.addMouseListener(n1Listener);
	}
	public static void addPlayer2NameAction(JLabel labe1) {
		labe1.addMouseListener(n2Listener);
	}

	public static void newGameEvent() {
		BoardFrame.Game_status.addMouseListener(newGameListener);
	}
	public static void uiEventUpdates(STemplate s, GTemplate t){
		if (s.isGameSetupMode()) {
			BoardFrame.pColor1.addMouseListener(p1Listener);
			BoardFrame.pColor2.addMouseListener(p2Listener);
			BoardFrame.label1.addMouseListener(n1Listener);
			BoardFrame.label2.addMouseListener(n2Listener);
			BoardFrame.l1.addMouseListener(gridSizeListener);
			BoardFrame.l2.addMouseListener(gameThemeListener);
			BoardFrame.startG.addMouseListener(saveListener);
			BoardFrame.settings.addMouseListener(sysPrefsListener);
			BoardFrame.grid.removeMouseListener(gridListener);
			BoardFrame.grid.removeMouseMotionListener(gridListener);
			
			//Disable control buttons
			BoardFrame.refresh.removeMouseListener(refreshListener);
			BoardFrame.manual_c.removeMouseListener(manualCaptureButtonListener);
			BoardFrame.undo.removeMouseListener(undoListener);
			BoardFrame.pass_turn.removeMouseListener(passTurnListener);
		}
		if (s.isPlayMode()) {
			BoardFrame.pColor1.removeMouseListener(p1Listener);
			BoardFrame.pColor2.removeMouseListener(p2Listener);
			BoardFrame.label1.removeMouseListener(n1Listener);
			BoardFrame.label2.removeMouseListener(n2Listener);
			BoardFrame.l1.removeMouseListener(gridSizeListener);
			BoardFrame.l2.removeMouseListener(gameThemeListener);
			BoardFrame.settings.removeMouseListener(sysPrefsListener);
			BoardFrame.startG.removeMouseListener(saveListener);
			
			// Enable control buttons
			BoardFrame.refresh.addMouseListener(refreshListener);
			BoardFrame.manual_c.addMouseListener(manualCaptureButtonListener);
			BoardFrame.undo.addMouseListener(undoListener);
			BoardFrame.pass_turn.addMouseListener(passTurnListener);
			
			BoardFrame.grid.addMouseListener(ViewEvents.gridListener);
			BoardFrame.grid.addMouseMotionListener(ViewEvents.gridListener);

			
		}
		if (s.isSystemSetupMode()) {
			BoardFrame.pColor1.removeMouseListener(p1Listener);
			BoardFrame.pColor2.removeMouseListener(p2Listener);
			BoardFrame.label1.removeMouseListener(n1Listener);
			BoardFrame.label2.removeMouseListener(n2Listener);
			BoardFrame.l1.removeMouseListener(gridSizeListener);
			BoardFrame.l2.removeMouseListener(gameThemeListener);
			//BoardFrame.settings.removeMouseListener(sysPrefsListener);
			BoardFrame.startG.removeMouseListener(saveListener);
			BoardFrame.settings.removeMouseListener(sysPrefsListener);
		}
	}
}