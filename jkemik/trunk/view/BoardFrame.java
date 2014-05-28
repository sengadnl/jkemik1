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
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println("Frame: " + width + " X " + height);

		String code = Tools.languageKey(JKemik.settings_t.getLanguage());
		String properties = Tools.propertiesFilename(code);
		Locale currentLocale = new Locale(code.toLowerCase());

		messages = ResourceBundle.getBundle(properties, currentLocale);
		instantiateAllPanels();
		setPanelSizes();
		instantiateAllLabels();
		instantiateAllButtonss();
		instantiateAllCheckBoxes();
		setAllLayouts();
		addComponentsToPanels();
		setTheme(JKemik.settings_t.getTheme());

		makingGame = true;
		init();
		//BoardFrame.mode.setVisible(false);
		// setTitle("J-Kemik " + Globals.VERSION);
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

		/* Events */
		progressB.setVisible(false);
		l1.rotateLabel(JKemik.settings_t.getGridDimension().toString());
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
		ViewEvents.sysPrefsListener();
		ViewEvents.newGameEvent();

		ViewEvents.modeToggleActionListener();
		ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		print_point.setText("" + messages.getString("gameSetupMode"));
		BoardFrame.feedback(messages.getString("feedback1") + " "
				+ messages.getString("startGameB") + " "
				+ messages.getString("feedback2"));
		uiLooksUpdate(JKemik.settings_t, JKemik.template);
		// System.out.println("Playmode: " + JKemik.settings_t.getPlayMode());

	}

	/**
	 * Defines container's attributes
	 * */
	// private void setContainerAttributs() {
	// container = getContentPane();
	// container.setBackground(new Color(0, 0, 0));
	// }

	private void setFrameSize(int w, int h) {
		if (ValidateInput.validateScreenResolution(w, h)) {
			setPreferredSize(new Dimension(w, h));
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
		grid = Grid.getInstance(JKemik.settings_t.getGridDimension());//
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
		feedbackarea = new JTextArea("");
		setupP = new JPanel();
		panel32 = new JPanel();
		controler_panel = new JPanel();
		config_container = new JPanel();
		pname1 = new JPanel();
		pname2 = new JPanel();
		p1p2NameHolder = new JPanel();
		gSizeAndTheme = new JPanel();

		gridstats = new GridStatus((int) (.4 * this.width),
				(int) (.1 * this.height));
	}

	private void instantiateAllLabels() {
		print_point = new JLabel("");
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
		// feedback = new JLabel("");
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

		settings = new JButton(messages.getString("settings"));
		help = new JButton(messages.getString("help"));
		exit = new JButton(messages.getString("exit"));
		Game_status = new JButton(messages.getString("newG"));
	}

	private void instantiateAllCheckBoxes() {
		mouseSelection = new JCheckBox(messages.getString("captureMode"));
		mode = new JCheckBox(messages.getString("switchModel"));

		mouseSelection.setVisible(false);
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
		playerPanel_container.setLayout(new BorderLayout(5, 10));
		controler_panel.setLayout(new GridLayout(4, 1, 10, 10));
		config_container.setLayout(new GridLayout(3, 2));
		setupP.setLayout(new BorderLayout(5, 10));
		p1p2NameHolder.setLayout(new BorderLayout(5, 5));
		pname1.setLayout(new GridLayout(2, 1));
		pname2.setLayout(new GridLayout(2, 1));
		gSizeAndTheme.setLayout(new GridLayout(3, 1));

		/* Bottom panels layout */

	}

	private void setPanelSizes() {
		top_container.setPreferredSize(new Dimension((int) this.width,
				(int) (CORNER_HEIGHT * this.height)));
		middle_container.setPreferredSize(new Dimension(
				(int) (.8 * this.width), (int) (.75 * this.height)));
		bottom_container.setPreferredSize(new Dimension((int) this.width,
				(int) (.098 * this.height)));

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
		pname1.setPreferredSize(new Dimension(((int) (.1 * this.width)),
				(int) (.04 * this.height)));
		pname2.setPreferredSize(new Dimension(((int) (.1 * this.width)),
				(int) (.04 * this.height)));
		down_left.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width), (int) (.1 * this.height)));
		feedbackarea.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width), (int) (.05 * this.height)));
		feedbackarea.setLineWrap(true);
		// feedbackarea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		feedbackarea.setFont(new Font("Arial", Font.BOLD, 11));
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
		top_right.add(refresh);
		top_right.add(exit);
		top_right.add(help);

		status_panel_container.add(playerPanel_container, BorderLayout.NORTH);
		status_panel_container.add(setupP);
		status_panel_container.add(controler_panel, BorderLayout.SOUTH);

		playerPanel_container.add(p1panel, BorderLayout.WEST);
		playerPanel_container.add(p2panel, BorderLayout.EAST);
		playerPanel_container.add(config_container, BorderLayout.SOUTH);
		config_container.add(la);
		config_container.add(AutoCap);
		config_container.add(lb);
		config_container.add(AutoPass);
		config_container.add(lc);
		config_container.add(Win);

		p1p2NameHolder.add(pname1, BorderLayout.NORTH);
		p1p2NameHolder.add(pname2, BorderLayout.SOUTH);

		setupP.add(p1p2NameHolder, BorderLayout.NORTH);
		setupP.add(gSizeAndTheme, BorderLayout.SOUTH);

		controler_panel.add(pass_turn);
		controler_panel.add(undo);
		controler_panel.add(mouseSelection);
		controler_panel.add(mode);

		pname1.add(label1);
		pname1.add(pColor1);

		gSizeAndTheme.add(l1);
		gSizeAndTheme.add(l2);
		gSizeAndTheme.add(startG);

		pname2.add(label2);
		pname2.add(pColor2);

		// ..........................................................//
		// ..........................................................//
		bottom_container.add(down_left);// gridstats
		bottom_container.add(gridstats);
		// gridstats.add(feedbackarea,BorderLayout.NORTH);
		// feedbackarea.add(feedback);
		down_left.add(progressB, BorderLayout.NORTH);
		down_left.add(feedbackarea, BorderLayout.SOUTH);

		// ..........................................................//
		// ..........................................................//
		middle_container.add(west_blank_panel, BorderLayout.WEST);
		middle_container.add(grid_container, BorderLayout.CENTER);
		middle_container.add(status_panel_container, BorderLayout.EAST);
		grid_container.add(settings_p);
		grid_container.add(grid);

	}

	private void setAllBorders() {
		grid_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		status_panel_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		top_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		middle_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		bottom_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		// controler_panel.setBorder(BorderFactory
		// .createLineBorder(BoardFrame.BORDER_COLOR));

		// TitledBorder SetupBorder = new TitledBorder("Setup");
		// SetupBorder.setTitleColor(BORDER_COLOR);
		// gSizeAndTheme.setBorder(SetupBorder);
		gSizeAndTheme.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		// TitledBorder ControlBorder = new TitledBorder("Control");
		// ControlBorder.setTitleColor(BORDER_COLOR);
		// config_container.setBorder(ControlBorder);
		config_container.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
		feedbackarea.setBorder(BorderFactory
				.createLineBorder(BoardFrame.BORDER_COLOR));
	}

	public static void decoratebuttons(Color bg, Color fg) {

		pass_turn.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));
		pass_turn.setForeground(fg);

		undo.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));
		undo.setForeground(fg);

		refresh.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));
		refresh.setForeground(fg);

		startG.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));
		startG.setForeground(fg);

		settings.setForeground(fg);
		settings.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));

		help.setForeground(fg);
		help.setBackground(Tools.boost(bg, Globals.LABEL_VARIANT / 2));

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

		la.setForeground(Tools.fade(BoardFrame.BOARD_COLOR));
		lb.setForeground(Tools.fade(BoardFrame.BOARD_COLOR));
		lc.setForeground(Tools.fade(BoardFrame.BOARD_COLOR));
		AutoCap.setForeground(Tools.fade(Color.WHITE));
		AutoPass.setForeground(Tools.fade(Color.WHITE));
		Win.setForeground(Tools.fade(Color.WHITE));

		// fadeButton(startG);
	}

	public static void enableGameControlPanel() {
		l1.setForeground(Tools.boost(Color.WHITE, Globals.FADE_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE, Globals.FADE_VARIANT));

		label1.setText(JKemik.template.getP1_name().toUpperCase());
		label1.setForeground(Color.WHITE);
		label2.setText(JKemik.template.getP2_name().toUpperCase());
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
		BoardFrame.THEME_COLOR = Tools.fade(theme, 10);
		BoardFrame.CPANEL_COLOR = cpanel;
		BoardFrame.BOARD_COLOR = board;
		BoardFrame.BORDER_COLOR = new Color(
				board.getRed() + BORDER_COL_VARIANT, board.getGreen()
						+ BORDER_COL_VARIANT, board.getBlue()
						+ BORDER_COL_VARIANT);
		setAllBorders();
		top_right.setBackground(BoardFrame.THEME_COLOR);

		exit.setForeground(Globals.EXIT_BUTTON_FGCOLOR);
		exit.setBackground(Tools.boost(Globals.EXIT_BUTTON_BGCOLOR,
				Globals.LABEL_VARIANT));

		Game_status.setForeground(Globals.NEWG_BUTTON_FRCOLOR);
		Game_status.setBackground(Tools.boost(Globals.NEWG_BUTTON_BGCOLOR,
				Globals.LABEL_VARIANT));

		print_point.setForeground(Color.GREEN);
		feedbackarea.setForeground(Color.GREEN);
		feedbackarea.setBackground(BoardFrame.CPANEL_COLOR);
		la.setForeground(BoardFrame.BOARD_COLOR);
		lb.setForeground(BoardFrame.BOARD_COLOR);
		lc.setForeground(BoardFrame.BOARD_COLOR);
		AutoCap.setForeground(Color.WHITE);
		AutoPass.setForeground(Color.WHITE);
		Win.setForeground(Color.WHITE);

		Grid.setGridLineCol(Tools.fade(BoardFrame.BOARD_COLOR, 35));
		top_container.setBackground(BoardFrame.THEME_COLOR);
		logo_panel.setBackground(BoardFrame.THEME_COLOR);
		top_middle.setBackground(BoardFrame.THEME_COLOR);
		playerPanel_container.setBackground(BoardFrame.THEME_COLOR);

		middle_container.setBackground(BoardFrame.THEME_COLOR);
		status_panel_container.setBackground(BoardFrame.THEME_COLOR);
		grid_container.setBackground(Tools.fade(BoardFrame.BOARD_COLOR, 20));

		west_blank_panel.setBackground(BoardFrame.THEME_COLOR);
		settings_p.setBackground(BoardFrame.THEME_COLOR);

		bottom_container.setBackground(BoardFrame.THEME_COLOR);
		down_left.setBackground(BoardFrame.THEME_COLOR);
		// down_left.setForeground(BoardFrame.BOARD_COLOR);
		panel32.setBackground(BoardFrame.THEME_COLOR);
		controler_panel.setBackground(BoardFrame.THEME_COLOR);
		config_container.setBackground(BoardFrame.THEME_COLOR);
		gridstats.setBackground(BoardFrame.THEME_COLOR);
		progressB.setBackground(BoardFrame.THEME_COLOR);
		progressB.setForeground(BOARD_COLOR);

		p1p2NameHolder.setBackground(BoardFrame.THEME_COLOR);
		setupP.setBackground(BoardFrame.THEME_COLOR);
		pname1.setBackground(BoardFrame.CPANEL_COLOR);
		gSizeAndTheme.setBackground(BoardFrame.CPANEL_COLOR);
		pname2.setBackground(BoardFrame.CPANEL_COLOR);

		l1.setForeground(Tools.boost(Color.WHITE, Globals.LABEL_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE, Globals.LABEL_VARIANT));

		mouseSelection.setBackground(BoardFrame.THEME_COLOR);
		mouseSelection.setForeground(Tools.boost(BOARD_COLOR,
				Globals.LABEL_VARIANT));
		mode.setBackground(BoardFrame.THEME_COLOR);
		mode.setForeground(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		settings_p.setTheme(Tools.fade(BOARD_COLOR),
				Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
	}

	public void setTheme(String str) {
		if (str.equals("Jkemik")) {
			setSkin(Tools.fade(Tools.fade(new Color(111, 53, 70))), new Color(
					0, 0, 0), new Color(111, 53, 70));
			pColor1.setArrayColors(Globals.CHEMIK_COLOR);
			pColor2.setArrayColors(Globals.CHEMIK_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.CHEMIK_COLOR[1]);
			JKemik.template.setP2_c(Globals.CHEMIK_COLOR[0]);
			pColor1.setBackground(Globals.CHEMIK_COLOR[1]);
			pColor2.setBackground(Globals.CHEMIK_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT),
					BOARD_COLOR);
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));

		} else if (str.equals("Origins")) {
			setSkin(Tools.fade(Tools.fade(new Color(110, 56, 27))), new Color(
					0, 0, 0), new Color(110, 56, 27));
			pColor1.setArrayColors(Globals.ORIGINE_COLOR);
			pColor2.setArrayColors(Globals.ORIGINE_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.ORIGINE_COLOR[1]);
			JKemik.template.setP2_c(Globals.ORIGINE_COLOR[0]);
			pColor1.setBackground(Globals.ORIGINE_COLOR[1]);
			pColor2.setBackground(Globals.ORIGINE_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT),
					BOARD_COLOR);
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		} else if (str.equals("Geeky")) {
			setSkin(Tools.fade(Tools.fade(new Color(70, 70, 20))), new Color(0,
					0, 0), new Color(70, 70, 20));
			pColor1.setArrayColors(Globals.GEECKY_COLOR);
			pColor2.setArrayColors(Globals.GEECKY_COLOR);
			pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.GEECKY_COLOR[1]);
			JKemik.template.setP2_c(Globals.GEECKY_COLOR[0]);
			pColor1.setBackground(Globals.GEECKY_COLOR[1]);
			pColor2.setBackground(Globals.GEECKY_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT),
					BOARD_COLOR);
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		} else {
			setSkin(Tools.fade(Tools.fade(new Color(40, 60, 40))), new Color(0,
					0, 0), new Color(40, 60, 40));
			pColor1.setArrayColors(Globals.CLASSIC_COLOR);
			pColor2.setArrayColors(Globals.CLASSIC_COLOR);
			// pColor1.rotateColor(1);
			JKemik.template.setP1_c(Globals.CLASSIC_COLOR[1]);
			JKemik.template.setP2_c(Globals.CLASSIC_COLOR[0]);
			pColor1.setBackground(Globals.CLASSIC_COLOR[1]);
			pColor2.setBackground(Globals.CLASSIC_COLOR[0]);
			label1.setForeground(Color.WHITE);
			label2.setForeground(Color.WHITE);
			decoratebuttons(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT),
					BOARD_COLOR);
			decorateLabelss(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		}
	}

	public static void updateSettingPanel() {
		try {
			if (JKemik.settings_t.isAutoCapture()) {
				AutoCap.setText(messages.getString("capturela"));
			} else {
				AutoCap.setText(messages.getString("capturelm"));
			}

			if (JKemik.settings_t.isAutoPass()) {
				AutoPass.setText(messages.getString("passla"));
			} else {
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
		BoardFrame.feedback(messages.getString("feedback1") + " "
				+ messages.getString("startGameB") + " "
				+ messages.getString("feedback2"));

		// panel 33
		pass_turn.setText(messages.getString("passB"));
		undo.setText(messages.getString("undoB"));
		refresh.setText(messages.getString("refreshB"));
		mouseSelection.setText(messages.getString("captureMode"));

		// panel 23
		mode.setText(messages.getString("switchModel"));

		// panel 32
		startG.setText(messages.getString("startGameB"));

		// panel 31
		la.setText(" " + messages.getString("capturel"));
		lb.setText(" " + messages.getString("passl"));
		lc.setText(" " + messages.getString("winl"));
	}

	public static void showControlButtons() {
		try {
			System.out.println("autocapture: "
					+ JKemik.settings_t.isAutoCapture() + "\nautopass: "
					+ JKemik.settings_t.isAutoPass());
			if (JKemik.settings_t.isAutoCapture()) {
				mouseSelection.setVisible(false);
				undo.setVisible(false);
			} else {
				System.out.println("show undo and selection checkbox");
				undo.setVisible(true);
				mouseSelection.setVisible(true);
			}

			if (JKemik.settings_t.isAutoPass()) {
				pass_turn.setVisible(false);
			} else {
				System.out.println("show undo and pass_turn button");
				undo.setVisible(true);
				pass_turn.setVisible(true);
			
			}

			initMouseSelection();
			System.err.println("Displaying mode!!!!");
			mode.setVisible(true);
			BoardFrame.refresh.setVisible(true);
		} catch (Exception e) {

		}
	}

	protected static void initMouseSelection() {
		BoardFrame.mouseSelection.setSelected(false);
		JKemik.view.repaint();
		BoardFrame.grid.drawn = false;
		Grid.manualc = false;
		Grid.refresh = true;
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

//	public static int getThereIsSavedGame() {
//		return thereIsSavedGame;
//	}
//
//	/**
//	 * @param integer
//	 *            , 1 if not game was saved 0 if a game was saved
//	 * */
//	public static void setThereIsSavedGame(int thereIsSavedGame) {
//		BoardFrame.thereIsSavedGame = thereIsSavedGame;
//	}

	public static ResourceBundle getMessages() {
		return messages;
	}

	public static void setMessages(ResourceBundle messages) {
		BoardFrame.messages = messages;
	}

	public static void updateBoardStatus() {
		double totPlots = JKemik.game.getCollection().size();
		double totalOnBoard = JKemik.settings_t.getGridDimension().positions();
		double deadBoard = (100 * totPlots) / totalOnBoard;
		double free = totalOnBoard - totPlots;
		gridstats.setDeadGridInPercentV(deadBoard);
		gridstats.setFreeGridInPercentV((free * 100) / totalOnBoard);
		gridstats.setDeadCountV("" + totPlots);
	}

	public static void uiLooksUpdate(STemplate s, GTemplate t) {
		if (s.isGameSetupMode()) {
			print_point.setText("" + messages.getString("gameSetupMode"));
			updateSettingPanel();
			translateUI();
			enableGameControlPanel();
			p1panel.disablePanelDecor();
			p2panel.disablePanelDecor();

			la.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			lb.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			lc.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			AutoCap.setForeground(Color.WHITE);
			AutoPass.setForeground(Color.WHITE);
			Win.setForeground(Color.WHITE);

			mouseSelection.setVisible(false);
			BoardFrame.startG.setVisible(true);
			BoardFrame.Game_status.setVisible(false);
			undo.setVisible(false);
			pass_turn.setVisible(false);
			mode.setVisible(false);
			displayGrid(true);
		}
		if (s.isPlayMode()) {
			System.out.println("setting playmode");
			Game_status.setText(BoardFrame.messages.getString("endG"));
			// Game_status.setForeground(Color.RED);
			disableGameControlPanel();
			p1panel.enablePanelDecor();
			p2panel.enablePanelDecor();

			la.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			lb.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			lc.setForeground(Tools.boost(BoardFrame.BOARD_COLOR));
			AutoCap.setForeground(Color.WHITE);
			AutoPass.setForeground(Color.WHITE);
			Win.setForeground(Color.WHITE);

			showControlButtons();
			BoardFrame.Game_status.setVisible(true);
			BoardFrame.startG.setVisible(false);
			print_point.setText("" + (new Point(0, 0)).toString());
			String p1n = t.getP1_name();
			String p2n = t.getP2_name();
			Color p1c = t.getP1_c();
			Color p2c = t.getP2_c();
			p1panel.initPanelForNewGame(p1n, p1c);
			p2panel.initPanelForNewGame(p2n, p2c);
			Win.setText(JKemik.settings_t.getMaxWinVal() + "");
			setMakingGame(false);
			displayGrid(true);
		}
		if (s.isSystemSetupMode()) {
			settings_p.translateSettingsPanel(s);
			print_point.setText(""
					+ BoardFrame.messages.getString("sysSetupMode"));
			p1panel.disablePanelDecor();
			p2panel.disablePanelDecor();
			BoardFrame.Game_status.setVisible(false);
			BoardFrame.startG.setVisible(false);
			disableGameControlPanel();
			undo.setVisible(false);
			pass_turn.setVisible(false);
			mode.setVisible(false);
			displayGrid(false);
			gridstats.init();
		}
	}

	public static void feedback(String message) {
		feedbackarea.setForeground(Color.GREEN);
		feedbackarea.setText(message);
	}

	public static void errorFeedback(String error) {
		feedbackarea.setForeground(Color.RED);
		feedbackarea.setText(error);
	}

	public double height = 0.0, width = 0.0;
	public static JProgressBar progressB;
	public static JPanel status_panel_container, grid_container,
			west_blank_panel, playerPanel_container, logo_panel, top_middle,
			top_right, top_container, middle_container, bottom_container,
			down_left, setupP, panel32, controler_panel, config_container,
			pname1, gSizeAndTheme, pname2, p1p2NameHolder;
	public static GridStatus gridstats;
	public static JButton refresh, startG, undo, pass_turn, exit, settings,
			help, Game_status;
	public static JCheckBox mouseSelection, mode;
	public static JLabel print_point, label1, label2, la, lb, lc, AutoCap,
			AutoPass, Win;// feedback;// exit, settings, help;
	public static JTextArea feedbackarea;
	public static JKIcon icon;
	public static RotateColor pColor1, pColor2;
	public static RotateLabel l1, l2;
	public static PlayerPanel p1panel, p2panel;
	private String[] gameType = { "Origins", "Jkemik", "Classic", "Geeky" };
	public static final double CORNER_WIDTH = .35, CORNER_HEIGHT = .04,
			SIDE_WIDTH = .18, SIDE_HEIGHT = .8, PLAYER_PNL_W_SCALAR = .080,
			PLAYER_PNL_H_SCALAR = .20, P2_W = .26, BOTTOM_COLOR_P_W = 80,
			BOTTOM_COLOR_P_H = 5;
	public static Grid grid;
	public static SettingsPanel settings_p;
	private static final long serialVersionUID = 1L;
	public static Color THEME_COLOR, CPANEL_COLOR, BOARD_COLOR, BORDER_COLOR;
	public static boolean makingGame;
	public static int thereIsSavedGame = 1, COUNTER = 0, MAX_VAL = 0,
			PROGRESS_BAR_MAX = 100, BORDER_COL_VARIANT = 30;
	public static final int THEME_JKEMIK = 0, THEME_ORIGINS = 1, THEME_OLD = 3;
}