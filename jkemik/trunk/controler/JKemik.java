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
 * @author Daniel Senga
 * 
 */
public class JKemik extends Application {
	public static Game game;
	// public static Manual manual;
	public static GTemplate template;
	public static STemplate settings_t;
	public static BoardFrame view;
	public static SettingsPanel settings;
	private static boolean isStarted = false;
	public static Load load;
	static File s_object = new File(Globals.settingsTemplateObjectFile);
	static File t_object = new File(Globals.templateObjectFile);
	static File g_object = new File(Globals.gameObjectFile);

	protected void init() {
		try {
			load = new Load(400, 210);
			System.out.println("Before checking tmp");
			File tmp = new File(Globals.tempFile);
			if (tmp.exists()) {
				System.out.println("tmp exists");
				readTemplate();
				readSettings();
			} else {
				if (tmp.mkdir()) {
					System.out.println(tmp + " created...");
					template = new GTemplate();
					settings_t = new STemplate();
				}
			}
			game = new Game(new Player(Color.WHITE, "Dany"), new Player(
					Color.WHITE, "Sarah"));
			settings = new SettingsPanel(300, 200);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	protected void idle() {
		try {
			if (screenResolutionCheck()) {
				view = new BoardFrame(Globals.FRAME_WIDTH, Globals.FRAME_HEIGHT);
			} else {
				System.out.println("Exiting ...");
				System.exit(0);
				//setDone();
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
					new FileOutputStream(s_object));
			out.writeObject(settings_t);

			ObjectOutputStream out1 = new ObjectOutputStream(
					new FileOutputStream(t_object));
			out1.writeObject(template);

		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: writeSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: writeSettings "
					+ exception2.getMessage());
		}

	}
	
	public static void writeGame() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(g_object));
			out.writeObject(game);

		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: writeGame "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: writeGame "
					+ exception2.getMessage());
		}

	}

	public static void updateSettingsPanel() {
		settings.setAutoCap(settings_t.getAutoCaptureStatus());
		settings.setAutoPass(settings_t.getAutoPassStatus());
		settings.setMaxWinVal(settings_t.getMaxWinVal());
		SettingsPanel.setManual_capture(settings_t.getManualCaptureStatus());
		String str = "" + settings_t.getMaxWinVal();
		SettingsPanel.setMax_win(str);
	}
	public static void readGameObj() {
		try {

			if (g_object.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(g_object));
				game = (Game)input.readObject();
				// updateSettingsPanel();
				input.close();
			} else {
				game = new Game(new Player(Color.WHITE, "Dany"), new Player(
						Color.WHITE, "Sarah"));
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readGame "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: readGame "
					+ exception2.getMessage());
		} catch (ClassNotFoundException exception3) {
			System.out.println("JKemik: readGame "
					+ exception3.getMessage());
		}

	}

	public static void readTemplate() {
		try {

			if (t_object.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(t_object));
				template = (GTemplate) input.readObject();
				// updateSettingsPanel();
				input.close();
			} else {
				template = new GTemplate();
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: readSettings "
					+ exception2.getMessage());
		} catch (ClassNotFoundException exception3) {
			System.out.println("JKemik: readSettings "
					+ exception3.getMessage());
		}

	}

	public static void readSettings() {
		try {

			if (s_object.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(s_object));

				settings_t = (STemplate) input.readObject();

				input.close();
			} else {
				settings_t = new STemplate();
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: readSettings "
					+ exception2.getMessage());
		} catch (ClassNotFoundException exception3) {
			System.out.println("JKemik: readSettings "
					+ exception3.getMessage());
		}

	}

	public boolean screenResolutionCheck() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (Globals.FRAME_HEIGHT > dimension.getHeight()
				&& Globals.FRAME_WIDTH > dimension.getWidth()) {
			JOptionPane.showMessageDialog(null,
					"JKemik only supports 1280 X 800 screen resolution and greater.");
			return false;
		}
		return true;
	}

	public static void options(String args[]) {
		if (args[0].equals("-v")) {
			System.out.println("\nJ-Kemik Version " + Globals.VERSION);//
		}
	}

	// private boolean createTempDir() {
	// File tmp = new File(Globals.tempFile);
	// return tmp.mkdir();
	// }

	public static void main(String[] args) {
		if (args.length > 0) {
			options(args);
		} else {
			(new JKemik()).run();
		}
	}

}
