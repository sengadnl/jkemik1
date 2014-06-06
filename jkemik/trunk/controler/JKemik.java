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
	private static String endingMessage = "This Game has not ended you";
	
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
			readGameObj();// TODO
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

	public static void writeGame() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(g_object));
			out.writeObject(game);
			out.close();
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: writeGame " + exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: writeGame " + exception2.getMessage());
		}
	}
	public static void removeGameObj() {
		try {
			/* Is there a saved game */
			if (g_object.exists()) {
				if(g_object.delete()){
	    			System.out.println(g_object.getAbsolutePath() + " is deleted!");
	    		}else{
	    			System.out.println("Delete operation failed.");
	    		}
			} 
			
			/* Is there a saved template */
			if(t_object.exists()){
				if(t_object.delete()){
					System.out.println(t_object.getAbsolutePath() + " is deleted!");
				}else{
					System.out.println("Delete operation failed.");
				}
			}
		} catch (Exception exception1) {
			System.out.println("JKemik: removeGameObj " + exception1.getMessage());
		}
	}

	public static void readGameObj() {
		try {
			/* Is there a saved game */
			if (g_object.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(g_object));
				game = (Game) input.readObject();

				int response = JOptionPane.showConfirmDialog(null,
						"Continues with saved Game?\n", "Question",
						JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					//BoardFrame.setThereIsSavedGame(response);
					game.init();
					Grid.refresh = true;
					input.close();
				} else {
					//BoardFrame.setThereIsSavedGame(1);
					settings_t.setGameSetupMode(true);
					Game.getInstance(
							new Player(template.getP1_c(), template.getP1_name()),
							new Player(template.getP2_c(), template.getP1_name()));
				}

			} else {
//				Game.getInstance(
//						new Player(template.getP1_c(), template.getP1_name()),
//						new Player(template.getP2_c(), template.getP1_name()));
				settings_t.setGameSetupMode(true);
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readGame " + exception1.getMessage());
		} catch (IOException exception2) {
			System.out.println("JKemik: readGame " + exception2.getMessage());
		} catch (ClassNotFoundException exception3) {
			System.out.println("JKemik: readGame " + exception3.getMessage());
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
				// settings_t.setGameSetupMode(true);
				settings_t.setPlayMode(true);

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
		String backtrack = SettingsPanel.backtrack.getText();
		// SettingsPanel.translateUI();
		String lang = (String) SettingsPanel.getLanguageList()
				.getSelectedItem();
		String key = Tools.languageKey(lang);
		String properties = Tools.propertiesFilename(key);
		int maxw = Integer.parseInt(str);
		int btrack = Integer.parseInt(backtrack);

		if (Tools.isMaxWinLessThanGrid(Grid.getDimension().positions(), maxw)) {
			t.setMaxWinVal(maxw);
			t.setMemo(t.isAutoCapture(), t.isAutoPass());
			t.setLanguage(lang);
			t.setBacktrackingDistance(btrack);
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
			System.err.println("after connectDots : " + temp);
			long end = System.currentTimeMillis();
			double total = (end - start);

			DecimalFormat format = new DecimalFormat("##.##");
			String str = format.format(total);

			if (settings_t.isAutoCapture()) {

				if (temp != null) {
					System.err.println("- Capture Duration = " + str + " MilliSecs");
					BoardFrame.feedbackarea
							.setText((game.getCaptured_count() + game
									.getRedeemed_count())
									+ " "
									+ BoardFrame.messages
											.getString("feedback3")
									+ " "
									+ game.getCurrentP().getName()
									+ " (" + str + " MilliSecs)");
					return temp;
				} else {
					game.setEmbuche_on(false);
				}

			} else {
				if (settings_t.isManualCapture()) {

					if (temp != null) {
						System.err.println("Capture Duration = " + total);
						BoardFrame.feedbackarea
								.setText((game.getCaptured_count() + game
										.getRedeemed_count())
										+ " "
										+ BoardFrame.messages
												.getString("feedback3")
										+ " "
										+ game.getCurrentP().getName());
						game.setEmbuche_on(false);
						return temp;
					} else {
						game.setEmbuche_on(false);
					}
					JKemik.settings_t.setManualCapture(false);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Error in embush: capture "
					+ e.getLocalizedMessage());
		}
		return null;
	}
	public static boolean checkEndGame() {
		boolean result = false;
		if ((game.getGuest().getScore()) >= game.getMaxScore()) {
			endingMessage = game.getGuest().getName() + " " + BoardFrame.messages.getString("winM");
			game.setStatus(1);
			result = true;
		}
		
		if(game.getPlay_count() < 1){
			game.setStatus(1);
			
			if(game.getGuest().getScore() > game.getCurrentP().getScore()){
				endingMessage = game.getGuest().getName() + " " + BoardFrame.messages.getString("winM");
			} else if(game.getGuest().getScore() < game.getCurrentP().getScore()){
				endingMessage = game.getCurrentP().getName() + " " + BoardFrame.messages.getString("winM");
			} else{
				endingMessage = "Tide";
			}
			result = true;
		}
		return result;
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
			System.out.println("\nJ-Kemik Version " + Globals.VERSION + 
					"\nJDK: 1.7");//
		}
	}

	public static String getEndingMessage() {
		return endingMessage.toUpperCase();
	}

	public static void setEndingMessage(String endingMessage) {
		JKemik.endingMessage = endingMessage;
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			options(args);
		} else {
			(new JKemik()).run();
		}
	}
}
