/**
 * 
 */
package controler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import utilities.Globals;
import view.BoardFrame;
import view.Load;
import view.SettingsPanel;
import api.GTemplate;
import api.Game;
import api.Player;
import api.STemplate;

/**
 * @author dalet
 * 
 */
public class JKemik extends Application {
	public static Game game;
	//public static Manual manual;
	public static GTemplate template;
	public static STemplate settings_t;
	public static BoardFrame view;
	public static SettingsPanel settings;
	private static boolean isStarted = false;
	public static Load load;
	static File file = new File(Globals.settingsTemplateObjectFile);

	protected void init() {
		//manual = new Manual();
		load = new Load(400, 210);
		load.plus("Building game template ...");// 1
		template = new GTemplate();
		readSettings();
		//settings_t = new STemplate();
		game = new Game(new Player(Color.WHITE, "Dany"), new Player(
				Color.WHITE, "Sarah"));
		settings = new SettingsPanel(250, 200);
	}

	protected void idle() {
		try {
			load.plus("Checking screen resolution...");
			if (screenResolutionCheck()) {
				load.plus("Constructing the BoarFrame");// 2
				view = new BoardFrame(Globals.FRAME_WIDTH, Globals.FRAME_HEIGHT);
			} else {
				setDone();
			}
		} catch (Exception e) {

		}
	}

	protected void cleanup() {
		System.exit(0);
	}

	/**
	 * @return the isStarted
	 */
	public static boolean isStarted() {
		return isStarted;
	}

	/**
	 * @param isStarted
	 *            the isStarted to set
	 */
	public static void setStarted(boolean isStarted) {
		JKemik.isStarted = isStarted;
	}

	public static void writeSettings() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(settings_t);
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: writeSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: writeSettings "
					+ exception2.getMessage());
		}

	}
	public static void updateSettingsPanel(){
			settings.setAutoCap(settings_t.getAutoCaptureStatus());
			settings.setAutoPass(settings_t.getAutoPassStatus());
			settings.setMaxWinVal(settings_t.getMaxWinVal());
			String str = "" + settings_t.getMaxWinVal();
			SettingsPanel.setMax_win(str);
	}
	public static void readSettings() {
		try {
			
			if (file.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(file));
				settings_t = (STemplate) input.readObject();
				//updateSettingsPanel();
				input.close();
			}else{
				settings_t = new STemplate();
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: readSettings "
					+ exception2.getMessage());
		}catch(ClassNotFoundException exception3){
			System.out.println("JKemik: readSettings "
					+ exception3.getMessage());
		}

	}

	public boolean screenResolutionCheck() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (Globals.FRAME_HEIGHT != dimension.getHeight()
				&& Globals.FRAME_WIDTH != dimension.getWidth()) {
			JOptionPane.showMessageDialog(null,
					"JKemik only supports 1280 X 800 for the moment.");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		(new JKemik()).run();
	}

}
