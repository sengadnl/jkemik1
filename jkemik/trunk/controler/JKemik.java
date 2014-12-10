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
 * COPYRIGHT(C)2014 Daniel Senga. All Right Reserved Main class of Jkemik.
 * 
 * @author Daniel Senga
 * @version Beta 4.0 12-2012
 * 
 */
public class JKemik extends Application {

	private static AbstractGame game;
	public static GTemplate template;
	public static STemplate settings_t;
	public static BoardFrame view;
	private static boolean isStarted = false;
	private static Load load;
	private static final File s_object = new File(Tools.fullPath()
			+ Globals.settingsTemplateObjectFile);
	private static final File t_object = new File(Tools.fullPath()
			+ Globals.templateObjectFile);
	private static final File g_object = new File(Tools.fullPath() + Globals.gameObjectFile);
	private static String endingMessage = "This Game has not ended you";


	@Override
	protected void init() {
		try {
			load = new Load(362, 183);
			File tmp = new File(Globals.tempFile);
			if (!tmp.exists()) {
				if (tmp.mkdir()) {
				}
			}
                        readSettings();
			readTemplate();
			readGameObj();

                        setStarted(true);
		} catch (Exception e) {
			System.err.println("Initialization Error: " + e.getMessage());
		}

	}

	@Override
	protected void idle() {
		if (screenResolutionCheck()) {
			view = BoardFrame.getInstance(Globals.FRAME_WIDTH,
					Globals.FRAME_HEIGHT);
		} else {
			System.out.println("Exiting ...");
			System.exit(0);
			// setDone();
		}
	 
	}

	@Override
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

	/**
	 * 
	 * @param t
	 *            is the game template
	 * @param s
	 *            is the settings template
	 */

	public static void createGame(GTemplate t, STemplate s) {
                game = null;
		GameCreator create = new GameCreator();
		System.out.println("CH: " + s.isCh() + "\nHH: " + s.isHh() + "\nNet: "
				+ s.isNet());
		if (s.isCh()) {
			System.out.println("Creating a Human vs AI game ...");
			t.setP2_name("COM");
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

	public static void writeTemplates() {
		try {
                        //templateGameSync(game);
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
				if (g_object.delete()) {
					System.out.println(g_object.getAbsolutePath()
							+ " is deleted!");
				} else {
					System.out.println("Deleting " + g_object.getAbsolutePath()
							+ " failed!");
				}
			}

			/* Is there a saved template */
			if (s_object.exists()) {
				if (s_object.delete()) {
					System.out.println(s_object.getAbsolutePath()
							+ " is deleted!");
				} else {
					System.out.println("Deleting " + s_object.getAbsolutePath()
							+ " failed!");
				}
			}

			/* Is there a saved template */
			if (t_object.exists()) {
				if (t_object.delete()) {
					System.out.println(t_object.getAbsolutePath()
							+ " is deleted!");
				} else {
					System.out.println("Deleting " + t_object.getAbsolutePath()
							+ " failed!");
				}
			}
		} catch (Exception exception1) {
			System.out.println("JKemik: removeGameObj "
					+ exception1.getMessage());
		}
	}

	public static void readGameObj() {
		try {
			/* Is there a saved game */
			if (g_object.exists()) {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(g_object));
                                if(settings_t.isCh()){
                                    game = (AIGame) input.readObject();
                                }else{
                                   game = (Game) input.readObject();
                                }
                                int response = JOptionPane.showConfirmDialog(null,
						"Continues with saved Game?\n", "Question",
						JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					game.init();
					Grid.refresh = true;
					input.close();
				} else {
					
					System.out.println("instantiating one...");
					createGame(template, settings_t);
                                        settings_t.setGameSetupMode(true);
					//BoardFrame.addstarterPoints(4);
				}
                                //templateGameSync(game);
			} else {
				createGame(template, settings_t);
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readGame " + exception1.getMessage());
		} catch (IOException | ClassNotFoundException exception2) {
			System.out.println("JKemik: readGame " + exception2.getMessage());
		}
	}

	public static void readTemplate() {
		try {

			if (t_object.exists()) {
				try (ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(t_object))) {
                                    template = (GTemplate) input.readObject();
				}
			} else {
				template = new GTemplate();
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readSettings "
					+ exception1.getMessage());
		} catch (IOException | ClassNotFoundException exception2) {
			System.out.println("JKemik: readSettings "
					+ exception2.getMessage());
		}
	}

	public static void readSettings() {
		try {

			if (s_object.exists()) {
				try (ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(s_object))) {
					settings_t = (STemplate) input.readObject();
				}
			} else {
				settings_t = new STemplate();
			}
		} catch (FileNotFoundException exception1) {
			System.out.println("JKemik: readSettings "
					+ exception1.getMessage());
		} catch (IOException | ClassNotFoundException exception2) {
			System.out.println("JKemik: readSettings "
					+ exception2.getMessage());
		}
	}

	public static void saveSysPrefs() {
		STemplate t = JKemik.settings_t;
		String str = SettingsPanel.max_win.getText();
		String backtrack = SettingsPanel.backtrack.getText();
		String maxPointScaler = SettingsPanel.maxPointScaler.getText();
                String starterPoints = SettingsPanel.starterPoints.getText();
		// SettingsPanel.translateUI();
		String lang = (String) SettingsPanel.getLanguageList()
				.getSelectedItem();
		String key = Tools.languageKey(lang);
		String properties = Tools.propertiesFilename(key);
		int maxw = Integer.parseInt(str);
		int btrack = Integer.parseInt(backtrack);
                int stpts = Integer.parseInt(starterPoints);
		double maxPtScaler = Double.parseDouble(maxPointScaler);
                
                if(!ValidateInput.starterPoints(stpts)){
                    return;
                }

		if (!ValidateInput.maxWin(maxw, t.getMaxPointPerPlayer())) {
			return;
		}

		if (!ValidateInput.maxPointScaler(maxPtScaler)) {
			return;
		}

		if (!ValidateInput.backtrack(btrack)) {
			return;
		}

		if (SettingsPanel.humComButton.isSelected()) {
			t.setCh(true);
		}
		if (SettingsPanel.humHumButton.isSelected()) {
			t.setHh(true);
		}
		if (SettingsPanel.networkButton.isSelected()) {
			t.setNet(true);
		}

		t.setMaxWinVal(maxw);
		t.setMemo(t.isAutoCapture(), t.isAutoPass());
		t.setLanguage(lang);
		t.setBacktrackingDistance(btrack);
		t.setMaxPointScaler(maxPtScaler);
                t.setStarterPoints(stpts);

		Locale local = new Locale(key);
		BoardFrame.setMessages(ResourceBundle.getBundle(properties, local));
		BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
		ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);

	}

	public static Cell embush(double squareSize, AbstractPlayer player) {
		try {
			long start = System.currentTimeMillis();
			AIGame game = (AIGame) JKemik.game;
			Cell temp = game.connectDots(squareSize);//
			long end = System.currentTimeMillis();
			double total = (end - start);

			DecimalFormat format = new DecimalFormat("##.##");
			String str = format.format(total);

			if (settings_t.isAutoCapture()) {

				if (temp != null) {
//					System.err.println("- Capture Duration = " + str
//							+ " MilliSecs");
					BoardFrame.feedbackarea
							.setText((game.getCaptured_count() + game
									.getRedeemed_count())
									+ " "
									+ BoardFrame.messages
											.getString("feedback3")
									+ " "
									+ game.getCurrentP().getName()
									+ " ("
									+ str
									+ " MilliSecs)");
					return temp;
				} else {
					game.setEmbuche_on(false);
				}

			} else {
				if (settings_t.isManualCapture()) {

					if (temp != null) {
						//System.err.println("Capture Duration = " + total);
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

	public static Cell embush(double squareSize) {
		try {
			long start = System.currentTimeMillis();
			Cell temp = game.connectDots(squareSize);//
			long end = System.currentTimeMillis();
			double total = (end - start);

			DecimalFormat format = new DecimalFormat("##.##");
			String str = format.format(total);

			if (settings_t.isAutoCapture()) {

				if (temp != null) {
//					System.err.println("- Capture Duration = " + str
//							+ " MilliSecs");
					BoardFrame.feedbackarea
							.setText((game.getCaptured_count() + game
									.getRedeemed_count())
									+ " "
									+ BoardFrame.messages
											.getString("feedback3")
									+ " "
									+ game.getCurrentP().getName()
									+ " ("
									+ str
									+ " MilliSecs)");
					return temp;
				} else {
					game.setEmbuche_on(false);
				}

			} else {
				if (settings_t.isManualCapture()) {

					if (temp != null) {
						//System.err.println("Capture Duration = " + total);
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
			endingMessage = game.getGuest().getName() + " "
					+ BoardFrame.messages.getString("winM");
			game.setStatus(1);
			result = true;
		}

		if (game.getPlay_count() < 1
				|| BoardFrame.boardDeadAreaInPercent() >= 75) {
			game.setStatus(1);

			if (game.getGuest().getScore() > game.getCurrentP().getScore()) {
				endingMessage = game.getGuest().getName() + " "
						+ BoardFrame.messages.getString("winM");
			} else if (game.getGuest().getScore() < game.getCurrentP()
					.getScore()) {
				endingMessage = game.getCurrentP().getName() + " "
						+ BoardFrame.messages.getString("winM");
			} else {
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
			System.out.println("\nJ-Kemik Version " + Globals.VERSION
					+ "\nJDK: 1.9");//
		}
	}

        public static AbstractGame getGame() {
            return game;
        }

        public static void setGame(AbstractGame game) {
            JKemik.game = game;
        }

	public static String getEndingMessage() {
		return endingMessage.toUpperCase();
	}

	public static void setEndingMessage(String endingMessage) {
		JKemik.endingMessage = endingMessage;
	}

        public static Load getLoad() {
            return load;
        }

        public static void setLoad(Load load) {
            JKemik.load = load;
        }        
	public static void main(String[] args) {
		if (args.length > 0) {
			options(args);
		} else {
			(new JKemik()).run();
		}
	}
        //private Lock initializationLock;
}
