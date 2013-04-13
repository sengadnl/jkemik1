/**
 * 
 */
package view;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import api.GTemplate;
import api.Point;
import api.STemplate;
import Events.*;
import controler.*;
import utilities.*;

/**
 * @author Daniel Senga Color: F7AD0C Green: 20d502 and 247, 173, 12
 */
public class BoardFrame extends JFrame {
	/**
	 * @author Daniel Senga
	 * @throws InterruptedException
	 * @since August 2011
	 **/
	private static volatile BoardFrame instance = null;
	public static ResourceBundle messages;

	private BoardFrame(double width, double height) {

		this.width = width;
		this.height = height;
		height = 800.0;
		width = 1280.0;

		setFrameSize((int) width, (int) height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContainerAttributs();

		System.out.println("Frame: " + width + " X " + height);
		System.out.println("Frame container: " + container.getWidth() + " X "
				+ container.getHeight());

		String code = Tools.languageKey(JKemik.settings_t.getLanguage());
		String properties = Tools.propertiesFilename(code);
		Locale currentLocale = new Locale(code.toLowerCase());

		messages = ResourceBundle.getBundle(properties, currentLocale);
		instantiateAllPanels();
		instantiateAllLabels();
		instantiateAllButtonss();
		instantiateAllCheckBoxes();
		setAllBorders();
		setAllLayouts();
		setPanelSizes();
		addComponentsToPanels();
		setTheme(JKemik.settings_t.getTheme());

		makingGame = true;
		init();
		BoardFrame.manual.setVisible(false);
		setTitle("J-Kemik " + Globals.VERSION);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		pack();
	}

	public static BoardFrame getInstance(double width, double height) {
		if (instance == null) {
			synchronized (BoardFrame.class) {
				if (instance == null) {
					instance = new BoardFrame(width, height);
				}
			}
		}
		return instance;
	}

	private void init() {
		JKemik.load.plus("Initializing jkemik..."); // 21
		JKemik.load.plus("Adding color picker 1 event...");
		JKemik.load.plus("Adding color picker 2 event...");
		JKemik.load.plus("Adding board size event...");
		JKemik.load.plus("Setting type event...");
		JKemik.load.plus("Adding player 1 name prompt ...");
		JKemik.load.plus("Adding player 2 name prompt ...");
		JKemik.load.plus("Adding save event listener...");
		JKemik.load.plus("Setting Grid initial state...");
		JKemik.load.plus("Setting cursor initial state...");// 24
		JKemik.load.plus("ViewEvents.ExitGameEvent();...");// 25
		JKemik.load.plus("ViewEvents.ExitGameEvent();...");// 25

		// if (BoardFrame.getThereIsSavedGame() == 0) {
		// } else {
		// }
		progressB.setVisible(false);
		l1.rotateLabel(JKemik.settings_t.getGridDimesion().toString());
		// System Preferences Events
		ViewEvents.saveSettingsAction();
		ViewEvents.onAutoCaptureAction();
		ViewEvents.onAutoPassTurnAction();
		ViewEvents.networkGameListener();
		ViewEvents.hvsHListener();
		ViewEvents.hvsAIListener();

		// constant events
		ViewEvents.exitListener();
		ViewEvents.helpListener();
		ViewEvents.refreshListener();

		ViewEvents.modeToggleActionListener();
		ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		print_point.setText("" + messages.getString("gameSetupMode"));
		uiLooksUpdate(JKemik.settings_t, JKemik.template);

	}

	/**
	 * Defines container's attributes
	 * */
	private void setContainerAttributs() {
		container = getContentPane();
		container.setBackground(new Color(0, 0, 0));
	}

	private void setFrameSize(int w, int h) {
		if (ValidateInput.validateScreenResolution(w, h)) {
			setSize(w, h);
		}
	}

	private void instantiateAllPanels() {
		top_container = new JPanel();
		middle_container = new JPanel();
		bottom_container = new JPanel();
		logo_panel = new JPanel();
		top_middle = new JPanel();
		top_right = new JPanel();
		status_panel_container = new JPanel();
		grid_container = new JPanel();
		grid = Grid.getInstance(JKemik.settings_t.getGridDimesion());//
		west_blank_panel = new JPanel();
		playerPanel_container = new JPanel();
		p1panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (PLAYER_PNL_H_SCALAR * SIDE_HEIGHT * this.height));
		p2panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (PLAYER_PNL_H_SCALAR * SIDE_HEIGHT * this.height));
		settings_p = new SettingsPanel(500, 400);
		pColor1 = new RotateColor((int) BOTTOM_COLOR_P_W,
				(int) BOTTOM_COLOR_P_H);
		pColor2 = new RotateColor((int) BOTTOM_COLOR_P_W,
				(int) BOTTOM_COLOR_P_H);
		down_left = new JPanel();
		setupP = new JPanel();
		panel32 = new JPanel();
		controler_panel = new JPanel();
		config_container = new JPanel();
		Holder1 = new JPanel();
		Holder2 = new JPanel();
		Holder3 = new JPanel();
		gridstats = new GridStatus((int)(.4 * this.width),
				(int) (.1 * this.height));
	}

	private void instantiateAllLabels() {
		print_point = new JLabel("");
		Game_status = new JLabel(messages.getString("newG"));
		settings = new JLabel(messages.getString("settings"));
		help = new JLabel(messages.getString("help"));
		exit = new JLabel(messages.getString("exit"));
		l1 = new RotateLabel(JKemik.settings_t.getGridDimensionsToString());
		l2 = new RotateLabel(this.gameType);

		label1 = new JLabel();
		label2 = new JLabel();
		la = new JLabel(" " + messages.getString("capturel"));
		lb = new JLabel(" " + messages.getString("passl"));
		lc = new JLabel(" " + messages.getString("winl"));
		AutoCap = new JLabel(JKemik.settings_t.getAutoCaptureStatus());
		AutoPass = new JLabel(JKemik.settings_t.getAutoPassStatus());
		Win = new JLabel("" + JKemik.settings_t.getMaxWinVal());
		exit = new JLabel(messages.getString("exit"));
		progressB = new JProgressBar(0, PROGRESS_BAR_MAX);
		icon = new JKIcon("media/jkemik-small.png", "");
	}

	private void instantiateAllButtonss() {
		refresh = new JButton(messages.getString("refreshB"));
		startG = new JButton(messages.getString("startGameB"));
		undo = new JButton(messages.getString("undoB"));
		pass_turn = new JButton(messages.getString("passB"));

		pass_turn.setVisible(false);
		undo.setVisible(false);
	}

	private void instantiateAllCheckBoxes() {
		manual_c = new JCheckBox(messages.getString("captureMode"));
		manual = new JCheckBox(messages.getString("manualModel"));

		manual_c.setVisible(false);
	}

	private void setAllLayouts() {
		/* Main container */
		setLayout(new BorderLayout());

		/* First level container */
		top_container.setLayout(new BorderLayout());
		middle_container.setLayout(new BorderLayout());
		bottom_container.setLayout(new FlowLayout());

		/* Header layout */
		logo_panel.setLayout(new BorderLayout());

		/* Middle panels layout */
		status_panel_container.setLayout(new FlowLayout());
		playerPanel_container.setLayout(new GridLayout(1, 2, 5, 40));
		controler_panel.setLayout(new GridLayout(5, 1, 10, 10));
		config_container.setLayout(new GridLayout(3, 2));
		west_blank_panel.setLayout(new BorderLayout(1, 1));
		setupP.setLayout(new BorderLayout(10, 10));
		Holder1.setLayout(new GridLayout(2, 1));
		Holder2.setLayout(new GridLayout(3, 1));
		Holder3.setLayout(new GridLayout(2, 1));
		/* Bottom panels layout */
	}

	private void setPanelSizes() {
		top_container.setPreferredSize(new Dimension((int) this.width,
				(int) (CORNER_HEIGHT * this.height)));
		middle_container.setPreferredSize(new Dimension(
				(int) (.8 * this.width), (int) (.75 * this.height)));
		bottom_container.setPreferredSize(new Dimension((int) this.width,
				(int) (.15 * this.height)));

		logo_panel.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));
		top_middle.setPreferredSize(new Dimension((int) (P2_W * this.width),
				(int) (CORNER_HEIGHT * this.height)));
		top_right.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		status_panel_container.setPreferredSize(new Dimension(
				((int) (SIDE_WIDTH * this.width)), (int) (.75 * this.height)));
		west_blank_panel.setPreferredSize(new Dimension(
				(int) (.01 * this.width), (int) (.75 * this.height)));

		down_left.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width), (int) (.1 * this.height)));
	}

	private void addComponentsToPanels() {
		add(top_container, BorderLayout.NORTH);
		add(middle_container, BorderLayout.CENTER);
		add(bottom_container, BorderLayout.SOUTH);
		// ..........................................................//
		// ..........................................................//
		top_container.add(logo_panel, BorderLayout.WEST);
		top_container.add(top_middle, BorderLayout.CENTER);
		top_container.add(top_right, BorderLayout.EAST);
		logo_panel.add(icon.createIcon(), BorderLayout.LINE_START);
		top_middle.add(print_point);
		top_right.add(Game_status);
		top_right.add(settings);
		top_right.add(exit);
		top_right.add(help);
		// ..........................................................//
		// ..........................................................//
		middle_container.add(west_blank_panel, BorderLayout.WEST);
		middle_container.add(grid_container, BorderLayout.CENTER);
		middle_container.add(status_panel_container, BorderLayout.EAST);
		grid_container.add(grid);
		grid_container.add(settings_p);
		status_panel_container.add(playerPanel_container);
		status_panel_container.add(config_container);
		status_panel_container.add(setupP);
		status_panel_container.add(controler_panel);
		playerPanel_container.add(p1panel);
		playerPanel_container.add(p2panel);
		config_container.add(la);
		config_container.add(AutoCap);
		config_container.add(lb);
		config_container.add(AutoPass);
		config_container.add(lc);
		config_container.add(Win);
		setupP.add(Holder1, BorderLayout.NORTH);
		setupP.add(Holder3, BorderLayout.CENTER);
		setupP.add(Holder2, BorderLayout.SOUTH);
		controler_panel.add(pass_turn);
		controler_panel.add(undo);
		controler_panel.add(refresh);
		controler_panel.add(manual_c);
		controler_panel.add(manual);
		// Holder1.add(blank1);
		Holder1.add(label1);
		Holder1.add(pColor1);
		// Holder1.add(blank2);
		Holder2.add(l1);
		Holder2.add(l2);
		Holder2.add(startG);
		// Holder3.add(blank3);
		Holder3.add(label2);
		Holder3.add(pColor2);
		// Holder3.add(blank4);
		// ..........................................................//
		// ..........................................................//
		bottom_container.add(down_left);//gridstats
		bottom_container.add(gridstats);
		down_left.add(progressB, BorderLayout.SOUTH);
	}

	private void setAllBorders() {
		// grid_container.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		// status_panel_container.setBorder(BorderFactory
		// .createLineBorder(BOARD_COLOR));
		// top_container.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		// middle_container.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		//bottom_container.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		// controler_panel.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		Holder2.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(BOARD_COLOR), "Setup"));
		config_container.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(BOARD_COLOR), "Control"));
	}

	public static void decoratebuttons(Color bg, Color fg) {
		pass_turn.setBackground(bg);
		pass_turn.setForeground(fg);
		undo.setBackground(bg);
		undo.setForeground(fg);
		refresh.setBackground(bg);
		refresh.setForeground(fg);
		startG.setBackground(bg);
		startG.setForeground(fg);
	}

	public static void decorateLabelss(Color fg) {
		la.setForeground(fg);
		lb.setForeground(fg);
		lc.setForeground(fg);
	}

	public static void disableGameControlPanel() {
		Color c = Tools.boost(BoardFrame.BOARD_COLOR, Globals.FADE_VARIANT);
		l1.setForeground(Tools.fade(c));
		l2.setForeground(Tools.fade(c));
		label1.setForeground(Tools.fade(JKemik.template.getP1_c()));
		label2.setForeground(Tools.fade(JKemik.template.getP2_c()));
		pColor1.setBackground(Tools.fade(JKemik.template.getP1_c()));
		pColor2.setBackground(Tools.fade(JKemik.template.getP2_c()));
		// fadeButton(startG);
	}

	public static void enableGameControlPanel() {
		l1.setForeground(Tools.boost(Color.WHITE, Globals.FADE_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE, Globals.FADE_VARIANT));

		label1.setText(JKemik.template.getP1_name());
		label1.setForeground(Color.WHITE);
		label2.setText(JKemik.template.getP2_name());
		label2.setForeground(Color.WHITE);

		p1panel.initPanelForNewGame("", JKemik.template.getP1_c());
		p2panel.initPanelForNewGame("", JKemik.template.getP2_c());

		pColor1.setBackground(JKemik.template.getP1_c());
		pColor2.setBackground(JKemik.template.getP2_c());

	}

	public static void highlightP2() {
		label2.setForeground(new Color(250, 0, 250));
	}

	/**
	 * @return the label1
	 */
	public static JLabel getLabel1() {
		return label1;
	}

	/**
	 * @param label1
	 *            the label1 to set
	 */
	public static void setLabel1(JLabel label1) {
		BoardFrame.label1 = label1;
	}

	/**
	 * @return the label2
	 */
	public static JLabel getLabel2() {
		return label2;
	}

	/**
	 * @return the grid
	 */
	public static Grid getGrid() {
		return grid;
	}

	/**
	 * @param grid
	 *            the grid to set
	 */
	public static void setGrid(Grid grid) {
		BoardFrame.grid = grid;
	}

	/**
	 * @param label2
	 *            the label2 to set
	 */
	public static void setLabel2(JLabel label2) {
		BoardFrame.label2 = label2;
	}

	public static void repaintGrid() {
		grid.setDrawn(false);
		grid.repaint();
	}

	public static void setprogress(int max) {
		COUNTER = 0;
		MAX_VAL = max;
	}

	public static void fadeLabel(JLabel label) {
		Color fore = label.getForeground();
		Color back = label.getBackground();
		label.setForeground(Tools.fade(fore));
		label.setBackground(Tools.fade(back));
	}

	/**
	 * Boosts the back and Foreground colors of b
	 */
	public static void boostLabel(JLabel label, Color fg, Color bg) {
		label.setForeground(Tools.boost(fg));
		label.setBackground(Tools.boost(bg));
	}

	public static void fadeCheckBox(JCheckBox checkbox) {
		Color fore = checkbox.getForeground();
		Color back = checkbox.getBackground();
		checkbox.setForeground(Tools.fade(fore));
		checkbox.setBackground(Tools.fade(back));
	}

	public static void boostCheckBox(JCheckBox checkbox) {
		Color fore = checkbox.getForeground();
		Color back = checkbox.getBackground();
		checkbox.setForeground(Tools.boost(fore));
		checkbox.setBackground(Tools.boost(back));
	}

	/**
	 * Fades the back and Foreground colors of b
	 */
	public static void fadeButton(JButton b) {
		Color fore = b.getForeground();
		Color back = b.getBackground();
		b.setForeground(Tools.fade(fore));
		b.setBackground(Tools.fade(back));
	}

	/**
	 * Boosts the back and Foreground colors of b
	 */
	public static void boostButton(JButton b) {
		Color fore = b.getForeground();
		Color back = b.getBackground();
		b.setForeground(Tools.boost(fore));
		b.setBackground(Tools.boost(back));
	}

	public static void progress(int counter) {
		long num = 250;
		try {
			if (counter != MAX_VAL) {
				progressB.setValue(counter);
				Thread.sleep(num);
			} else {
				COUNTER = 0;
				progressB.setVisible(false);
			}
		} catch (Exception e) {

		}
	}

	public void setSkin(Color theme, Color cpanel, Color board) {
		BoardFrame.THEME_COLOR = theme;
		BoardFrame.CPANEL_COLOR = cpanel;
		BOARD_COLOR = board;

		top_right.setBackground(BoardFrame.THEME_COLOR);
		help.setForeground(Color.WHITE);
		exit.setForeground(Color.RED);
		settings.setForeground(new Color(150, 150, 255));
		Game_status.setForeground(Color.GREEN);
		print_point.setForeground(Color.GREEN);
		la.setForeground(BoardFrame.BOARD_COLOR);
		lb.setForeground(BoardFrame.BOARD_COLOR);
		lc.setForeground(BoardFrame.BOARD_COLOR);
		// label1.setBackground(Tools.fade(BoardFrame.BOARD_COLOR));
		// label2.setBackground(Tools.fade(BoardFrame.BOARD_COLOR));
		AutoCap.setForeground(Color.WHITE);
		AutoPass.setForeground(Color.WHITE);
		Win.setForeground(Color.WHITE);

		Grid.setGridLineCol(BoardFrame.THEME_COLOR);
		top_container.setBackground(BoardFrame.THEME_COLOR);
		logo_panel.setBackground(BoardFrame.THEME_COLOR);
		top_middle.setBackground(BoardFrame.THEME_COLOR);
		playerPanel_container.setBackground(BoardFrame.THEME_COLOR);

		middle_container.setBackground(BoardFrame.THEME_COLOR);
		status_panel_container.setBackground(BoardFrame.THEME_COLOR);
		grid_container.setBackground(BoardFrame.THEME_COLOR);
		west_blank_panel.setBackground(BoardFrame.THEME_COLOR);
		settings_p.setBackground(BoardFrame.THEME_COLOR);

		bottom_container.setBackground(BoardFrame.THEME_COLOR);
		down_left.setBackground(BoardFrame.THEME_COLOR);
		panel32.setBackground(BoardFrame.THEME_COLOR);
		controler_panel.setBackground(BoardFrame.THEME_COLOR);
		config_container.setBackground(BoardFrame.THEME_COLOR);
		gridstats.setBackground(BoardFrame.THEME_COLOR);
		progressB.setBackground(BoardFrame.THEME_COLOR);
		progressB.setForeground(BOARD_COLOR);

		// blank1.setBackground(BOARD_COLOR);
		// blank2.setBackground(BOARD_COLOR);
		setupP.setBackground(BoardFrame.CPANEL_COLOR);
		Holder1.setBackground(Tools.fade(BoardFrame.BOARD_COLOR));
		Holder2.setBackground(BoardFrame.CPANEL_COLOR);
		Holder3.setBackground(Tools.fade(BoardFrame.BOARD_COLOR));

		l1.setForeground(Tools.boost(Color.WHITE, Globals.LABEL_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE, Globals.LABEL_VARIANT));

		manual_c.setBackground(BoardFrame.THEME_COLOR);
		manual_c.setForeground(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		manual.setBackground(BoardFrame.THEME_COLOR);
		manual.setForeground(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		settings_p.setTheme(Tools.fade(BOARD_COLOR),
				Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
	}

	public void setTheme(String str) {
		if (str.equals("Jkemik")) {
			setSkin(new Color(0, 0, 0), new Color(0, 0, 0), new Color(111, 53,
					70));
			pColor1.setArrayColors(Globals.CHEMIK_COLOR);
			pColor2.setArrayColors(Globals.CHEMIK_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.CHEMIK_COLOR[1]);
			JKemik.template.setP2_c(Globals.CHEMIK_COLOR[0]);
			pColor1.setBackground(Globals.CHEMIK_COLOR[1]);
			pColor2.setBackground(Globals.CHEMIK_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(BOARD_COLOR,
					Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));

		} else if (str.equals("Origins")) {
			setSkin(new Color(0, 0, 0), new Color(0, 0, 0), new Color(110, 56,
					27));
			pColor1.setArrayColors(Globals.ORIGINE_COLOR);
			pColor2.setArrayColors(Globals.ORIGINE_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.ORIGINE_COLOR[1]);
			JKemik.template.setP2_c(Globals.ORIGINE_COLOR[0]);
			pColor1.setBackground(Globals.ORIGINE_COLOR[1]);
			pColor2.setBackground(Globals.ORIGINE_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(BOARD_COLOR,
					Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		} else if (str.equals("Geeky")) {
			setSkin(new Color(0, 0, 0), new Color(0, 0, 0), new Color(70, 70,
					20));
			pColor1.setArrayColors(Globals.GEECKY_COLOR);
			pColor2.setArrayColors(Globals.GEECKY_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.GEECKY_COLOR[1]);
			JKemik.template.setP2_c(Globals.GEECKY_COLOR[0]);
			pColor1.setBackground(Globals.GEECKY_COLOR[1]);
			pColor2.setBackground(Globals.GEECKY_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);

			decoratebuttons(BOARD_COLOR,
					Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		} else {
			setSkin(new Color(0, 0, 0), new Color(0, 0, 0), new Color(40, 60,
					40));
			pColor1.setArrayColors(Globals.CLASSIC_COLOR);
			pColor2.setArrayColors(Globals.CLASSIC_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.CLASSIC_COLOR[1]);
			JKemik.template.setP2_c(Globals.CLASSIC_COLOR[0]);
			pColor1.setBackground(Globals.CLASSIC_COLOR[1]);
			pColor2.setBackground(Globals.CLASSIC_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);

			decoratebuttons(BOARD_COLOR,
					Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		}
	}

	public static void updateSettingPanel() {
		try {
			if (JKemik.settings_t.isAutoCapture()) {
				// AutoCap.setText("AUTO");
				AutoCap.setText(messages.getString("capturela"));
			} else {
				// AutoCap.setText("MANUAL");
				AutoCap.setText(messages.getString("capturelm"));
			}

			if (JKemik.settings_t.isAutoPass()) {
				// AutoPass.setText("AUTO");
				AutoPass.setText(messages.getString("passla"));
			} else {
				// AutoPass.setText("MANUAL");
				AutoPass.setText(messages.getString("passlm"));
			}
			String str = "" + JKemik.settings_t.getMaxWinVal();
			BoardFrame.Win.setText(str);
		} catch (Exception e) {

		}
	}

	public static void translateUI() {
		// panel 13
		Game_status.setText(messages.getString("newG"));
		settings.setText(messages.getString("settings"));
		exit.setText(messages.getString("exit"));
		help.setText(messages.getString("help"));

		// panel 33
		pass_turn.setText(messages.getString("passB"));
		undo.setText(messages.getString("undoB"));
		refresh.setText(messages.getString("refreshB"));
		manual_c.setText(messages.getString("captureMode"));

		// panel 23
		manual.setText(messages.getString("manualModel"));

		// panel 32
		startG.setText(messages.getString("startGameB"));

		// panel 31
		la.setText(" " + messages.getString("capturel"));
		lb.setText(" " + messages.getString("passl"));
		lc.setText(" " + messages.getString("winl"));

	}

	public static void showControlButtons() {
		try {
			if (JKemik.settings_t.isAutoCapture()) {
				manual_c.setVisible(false);
				undo.setVisible(false);
				// manual.setVisible(true);
			} else {
				undo.setVisible(true);
				manual_c.setVisible(true);
				// manual.setVisible(false);
			}

			if (JKemik.settings_t.isAutoPass()) {
				pass_turn.setVisible(false);
			} else {
				undo.setVisible(true);
				pass_turn.setVisible(true);
				// manual.setVisible(false);
			}
			// if(!JKemik.settings_t.getMemo()[0] &&
			// !JKemik.settings_t.getMemo()[1]){
			// manual.setSelected(true);
			// }
			manual.setVisible(true);
			BoardFrame.refresh.setVisible(true);
		} catch (Exception e) {

		}
	}

	public static void displayGrid(boolean display) {
		settings_p.setVisible(!display);
		grid_container.repaint();
		grid.setVisible(display);
		if (grid.drawn) {
			System.out.println("Displaying the grid.....");
			grid.drawn = false;
			grid.repaint();
		}
	}

	public static boolean isMakingGame() {
		return makingGame;
	}

	public static void setMakingGame(boolean inOptions) {
		BoardFrame.makingGame = inOptions;
	}

	public static int getThereIsSavedGame() {
		return thereIsSavedGame;
	}

	public static void setThereIsSavedGame(int thereIsSavedGame) {
		BoardFrame.thereIsSavedGame = thereIsSavedGame;
	}

	public static ResourceBundle getMessages() {
		return messages;
	}

	public static void setMessages(ResourceBundle messages) {
		BoardFrame.messages = messages;
	}
	public static void updateBoardStatus(){
		double totPlots = JKemik.game.getCurrentP().getPloted().size() + JKemik.game.getGuest().getPloted().size();
		double totalOnBoard = Tools.numberOfPositions(JKemik.settings_t.getGridDimesion().getDimensionSquares());
		double deadBoard = (100*totPlots)/totalOnBoard;
		double free = totalOnBoard - totPlots;
		gridstats.setDeadGridInPercentV(deadBoard);
		gridstats.setFreeGridInPercentV((free * 100)/totalOnBoard);
		gridstats.setDeadCountV("" + totPlots);
	}

	public static void uiLooksUpdate(STemplate s, GTemplate t) {
		if (s.isGameSetupMode()) {
			print_point.setText("" + messages.getString("gameSetupMode"));
			boostLabel(settings, Color.WHITE, BoardFrame.THEME_COLOR);
			updateSettingPanel();
			translateUI();
			enableGameControlPanel();
			displayGrid(true);
			gridstats.init();
		}
		if (s.isPlayMode()) {
			Game_status.setText(BoardFrame.messages.getString("endG"));
			Game_status.setForeground(Color.RED);
			fadeLabel(BoardFrame.settings);
			disableGameControlPanel();
			showControlButtons();
			print_point.setText("" + (new Point(0, 0)).toString());
			String p1n = t.getP1_name();
			String p2n = t.getP2_name();
			Color p1c = t.getP1_c();
			Color p2c = t.getP2_c();
			p1panel.initPanelForNewGame(p1n, p1c);
			p2panel.initPanelForNewGame(p2n, p2c);
			Win.setText(JKemik.settings_t.getMaxWinVal() + "");
			setMakingGame(false);
		}
		if (s.isSystemSetupMode()) {
			settings_p.translateSettingsPanel(s);
			print_point.setText(""
					+ BoardFrame.messages.getString("sysSetupMode"));
			disableGameControlPanel();
			displayGrid(false);
			gridstats.init();
		}
	}

	public double height = 0.0, width = 0.0;
	public static JProgressBar progressB;
	public static JPanel status_panel_container, grid_container,
			west_blank_panel, playerPanel_container, logo_panel, top_middle,
			top_right, top_container, middle_container, bottom_container,
			down_left, setupP, panel32, controler_panel, config_container,
			Holder1, Holder2, Holder3;
	public static GridStatus gridstats;
	public static JButton refresh, startG, undo, pass_turn;
	public static JCheckBox manual_c, manual;
	public static JLabel print_point, Game_status, settings, help, label1,
			label2, la, lb, lc, AutoCap, AutoPass, Win, exit;
	public static JKIcon icon;
	public static RotateColor pColor1, pColor2;
	public static RotateLabel l1, l2;
	public static PlayerPanel p1panel, p2panel;
	private String[] gameType = { "Origins", "Jkemik", "Classic", "Geeky" };
	public static final double CORNER_WIDTH = .35, CORNER_HEIGHT = .04,
			SIDE_WIDTH = .18, SIDE_HEIGHT = .8, PLAYER_PNL_W_SCALAR = .085,
			PLAYER_PNL_H_SCALAR = .20, P2_W = .26, BOTTOM_COLOR_P_W = 80,
			BOTTOM_COLOR_P_H = 12;
	public static Grid grid;
	public static SettingsPanel settings_p;
	private static java.awt.Container container;
	private static final long serialVersionUID = 1L;
	public static Color THEME_COLOR, CPANEL_COLOR, BOARD_COLOR;
	public static boolean makingGame;
	public static int thereIsSavedGame = 1, COUNTER = 0, MAX_VAL = 0,
			PROGRESS_BAR_MAX = 100;
	public static final int THEME_JKEMIK = 0, THEME_ORIGINS = 1, THEME_OLD = 3;
}