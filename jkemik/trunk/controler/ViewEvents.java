/**
 * 
 */
package controler;

import javax.swing.*;

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
	public static SaveButtonListener saveListener = new SaveButtonListener(BoardFrame.save);
	public static SettingsLabelListener saveSettings = new SettingsLabelListener(BoardFrame.settings);
	public static SaveSettingsListener saveSetting = new SaveSettingsListener(SettingsPanel.save);
	public static UndoListener undoListener = new UndoListener(BoardFrame.undo);
	public static EmbushListener captureListener = new EmbushListener(BoardFrame.capture);
	public static PassTurnListener passTurnListener = new PassTurnListener(BoardFrame.pass_turn);
	public static AutoCaptureListener autoCaptureListener = new AutoCaptureListener(SettingsPanel.auto_capture);
	public static AutoTurnPassAction autoTurnPassListener = new AutoTurnPassAction(SettingsPanel.auto_turn_pass);
	public static ManualCaptureListener manualCaptureListener = new ManualCaptureListener(SettingsPanel.manual_capture);
	public static ExitListener exitlistener = new ExitListener(BoardFrame.exit);
	public static HelpListener helpListener = new HelpListener(BoardFrame.help);
	public static DebugListener debugListener = new DebugListener(BoardFrame.debug);
	
	public static void debugListener(){
		BoardFrame.debug.addMouseListener(debugListener);
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
	public static void onManualCaptureAction(){
		SettingsPanel.manual_capture.addMouseListener(manualCaptureListener);//
	}
	public static void passTurnAction(JButton pass){
		pass.addMouseListener(passTurnListener);
	}
	public static void saveSettingsAction(){
		SettingsPanel.save.addMouseListener(saveSetting);
	}
	public static void settingsLabelAction(){
		BoardFrame.settings.addMouseListener(saveSettings);
	}
	
	public static void captureAction(JButton capture){
		capture.addMouseListener(captureListener);
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

	public static void ExitGameEvent() {
		BoardFrame.Game_status.addMouseListener(newGameListener);
	}
}