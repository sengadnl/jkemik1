/**
 * 
 */
package controler;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import utilities.*;
import view.*;
import Events.ViewEvents;
import agents.JkBot;
import api.*;

/**
 * COPYRIGHT(C)2012 Daniel Senga. All Right Reserved Main class of Jkemik.
 * 
 * @author Daniel Senga
 * @version Beta 4.0 12-2012
 * 
 */
public class JKemik extends Application {

	public static AbstractGame game;
	public static GTemplate template;
	public static STemplate settings_t;
	public static BoardFrame view;
	// public static SettingsPanel settings;
	private static boolean isStarted = false;
	public static Load load;
	static File s_object = new File(Tools.fullPath()
			+ Globals.settingsTemplateObjectFile);
	static File t_object = new File(Tools.fullPath()
			+ Globals.templateObjectFile);
	static File g_object = new File(Tools.fullPath() + Globals.gameObjectFile);

	protected void init() {
		try {
			load = new Load(362, 183);
			File tmp = new File(Globals.tempFile);
			if (!tmp.exists()) {
				if (tmp.mkdir()) {
				}
			}
			readTemplate();
			readSettings();
			// readGameObj();// TODO

			createGame(JKemik.template, JKemik.settings_t);
			System.out.println(game + "\n\n");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	protected void idle() {
		// try {
		if (screenResolutionCheck()) {
			view = BoardFrame.getInstance(Globals.FRAME_WIDTH,
					Globals.FRAME_HEIGHT);
		} else {
			System.out.println("Exiting ...");
			System.exit(0);
			// setDone();
		}
		// } catch (Exception e) {
		// System.err.println("Error: " + e.getMessage() + "\nStack Trace: " +
		// e.getStackTrace());
		// }
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

	/**/
	public static void createGame(GTemplate t, STemplate s) {
		GameCreator create = new GameCreator();
		System.out.println("CH: " + s.isCh() + "\nHH: " + s.isHh() + "\nNet: "
				+ s.isNet());
		if (s.isCh()) {
			System.out.println("Creating a Human vs AI game ...");
			t.setP2_name("Com");
			Player player1 = new Player(t.getP1_c(), t.getP1_name());
			JkBot player2 = new JkBot(t.getP2_c(), t.getP2_name());
			game = create.createGame(player1, player2);
			System.out.println("Is game AI: " + game.isAI());
		}

		if (s.isHh() || s.isNet()) {
			System.out.println("Creating a Human vs Human game ...");
			Player player1 = new Player(t.getP1_c(), t.getP1_name());
			Player player2 = new Player(t.getP2_c(), t.getP2_name());
			game = create.createGame(player1, player2);
			System.out.println("Is game AI: " + game.isAI());
		}
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
			ObjectOutputStream out1 = new ObjectOutputStream(
					new FileOutputStream(t_object));

			out.writeObject(settings_t);
			out1.writeObject(template);
			out.close();
			out1.close();
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: writeSettings "
					+ exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: writeSettings "
					+ exception2.getMessage());
		}
	}

	// public static void writeGame() {
	// try {
	// ObjectOutputStream out = new ObjectOutputStream(
	// new FileOutputStream(g_object));
	// out.writeObject(game);
	// out.close();
	// } catch (FileNotFoundException exception1) {
	// System.out.println("JKemik: writeGame " + exception1.getMessage());
	// } catch (IOException exception2) {
	// System.out.println("JKemik: writeGame " + exception2.getMessage());
	// }
	// }
	//
	// public static void readGameObj() {
	// try {
	//
	// if (g_object.exists()) {
	// ObjectInputStream input = new ObjectInputStream(
	// new FileInputStream(g_object));
	// game = (Game) input.readObject();
	// int response = JOptionPane.showConfirmDialog(null,
	// "Continues with saved Game?\n", "Question",
	// JOptionPane.YES_NO_OPTION);
	// if (response == 0) {
	// BoardFrame.setThereIsSavedGame(response);
	// Grid.refresh = true;
	// input.close();
	// } else {
	// BoardFrame.setThereIsSavedGame(response);
	// }
	// } else {
	// game = new Game(new Player(template.getP1_c(),
	// template.getP1_name()), new Player(template.getP2_c(),
	// template.getP1_name()));
	// }
	// } catch (FileNotFoundException exception1) {
	// System.out.println("JKemik: readGame " + exception1.getMessage());
	// } catch (IOException exception2) {
	// System.out.println("JKemik: readGame " + exception2.getMessage());
	// } catch (ClassNotFoundException exception3) {
	// System.out.println("JKemik: readGame " + exception3.getMessage());
	// }
	//
	// }

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
				settings_t.setGameSetupMode(true);

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

	public static void saveSysPrefs() {
		STemplate t = JKemik.settings_t;
		String str = SettingsPanel.max_win.getText();
		// SettingsPanel.translateUI();
		String lang = (String) SettingsPanel.getLanguageList()
				.getSelectedItem();
		String key = Tools.languageKey(lang);
		String properties = Tools.propertiesFilename(key);
		int maxw = Integer.parseInt(str);
		if (Tools.isMaxWinLessThanGrid(Grid.getDimension().positions(), maxw)) {
			t.setMaxWinVal(maxw);
			t.setMemo(t.isAutoCapture(), t.isAutoPass());
			t.setLanguage(lang);
			Locale local = new Locale(key);
			BoardFrame.setMessages(ResourceBundle.getBundle(properties, local));
			BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
			ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		} else {
			JOptionPane.showMessageDialog(
					null,
					BoardFrame.messages.getString("maxWinSizeMustbBe1")
							+ Grid.getDimension().positions()
							+ BoardFrame.messages
									.getString("maxWinSizeMustbBe2"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public static Cell embush(double squareSize) {
		try {
			long start = System.currentTimeMillis();
			Cell temp = game.connectDots(squareSize);//
			long end = System.currentTimeMillis();
			double total = (end - start)/1000;
			
			DecimalFormat format = new DecimalFormat("#.###");
			String str = format.format(total);
			
			
			if (settings_t.isAutoCapture()) {

				if (temp != null) {
					System.err.println("Capture Duration = " + str);
					return temp;
				} else {
					game.setEmbuche_on(false);
				}

			} else {
				if (settings_t.isManualCapture()) {

					if (temp != null) {
						System.err.println("Capture Duration = " + total);
						game.setEmbuche_on(false);
						return temp;
					} else {
						game.setEmbuche_on(false);
					}
					JKemik.settings_t.setManualCapture(false);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Error in embush: capture " + e.getLocalizedMessage());
		}
		return null;
	}

	public boolean screenResolutionCheck() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (Globals.FRAME_HEIGHT > dimension.getHeight()
				&& Globals.FRAME_WIDTH > dimension.getWidth()) {
			JOptionPane
					.showMessageDialog(null,
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

	public static void main(String[] args) {
		if (args.length > 0) {
			options(args);
		} else {
			(new JKemik()).run();
		}
	}
}
