/**
 * 
 */
package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import api.GTemplate;
import api.GridDimension;
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
	// Locale currentLocale;
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
		// Layout
		setLayout(new BorderLayout());

		createPanel123();
		designPanel1();
		designPanel3();
		designPanel2();
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

	private void createPanel123() {
		try {
			String code = Tools.languageKey(JKemik.settings_t.getLanguage());
			String properties = Tools.propertiesFilename(code);
			Locale currentLocale = new Locale(code.toLowerCase());

			messages = ResourceBundle.getBundle(properties, currentLocale);

			panel1 = new JPanel();
			panel1.setLayout(new BorderLayout());
			panel1.setPreferredSize(new Dimension((int) this.width,
					(int) (CORNER_HEIGHT * this.height)));
			// panel1.setBorder(BorderFactory.createLineBorder(BoardFrame.BOARD_COLOR));
			add(panel1, BorderLayout.NORTH);

			panel2 = new JPanel();
			panel2.setLayout(new BorderLayout());
			panel2.setPreferredSize(new Dimension((int) (.8 * this.width),
					(int) (SIDE_HEIGHT * this.height)));

			add(panel2, BorderLayout.CENTER);

			panel3 = new JPanel();
			panel3.setLayout(new BorderLayout());// 10,10
			// panel3.setPreferredSize(new Dimension((int) this.width,
			// (int) (CORNER_HEIGHT * this.height)));
			panel3.setPreferredSize(new Dimension((int) this.width,
					(int) (CORNER_HEIGHT * this.height)));
			// panel3.setBorder(BorderFactory.createLineBorder(BoardFrame.BOARD_COLOR));
			add(panel3, BorderLayout.SOUTH);
		} catch (NullPointerException e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private void designPanel1() {
		panel11 = new JPanel();
		panel11.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		panel11.setLayout(new BorderLayout());

		JKIcon icon = new JKIcon("media/jkemik-small.png", "");
		panel11.add(icon.createIcon(), BorderLayout.LINE_START);
		panel1.add(panel11, BorderLayout.WEST);

		panel12 = new JPanel();
		panel12.setPreferredSize(new Dimension((int) (P2_W * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		print_point = new JLabel("");
		print_point.setForeground(Color.GREEN);
		panel12.add(print_point);

		panel1.add(panel12, BorderLayout.CENTER);

		panel13 = new JPanel();
		panel13.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		System.out.println("Panel13: " + (CORNER_WIDTH * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));

		panel13.setBackground(BoardFrame.THEME_COLOR);

		Game_status = new JLabel(messages.getString("newG"));
		Game_status.setForeground(Color.GREEN);

		settings = new JLabel(messages.getString("settings"));
		settings.setForeground(new Color(150, 150, 255));

		exit = new JLabel(messages.getString("exit"));
		exit.setForeground(Color.RED);

		help = new JLabel(messages.getString("help"));
		help.setForeground(Color.WHITE);

		panel13.add(Game_status);
		panel13.add(settings);
		panel13.add(exit);
		panel13.add(help);

		panel1.add(panel13, BorderLayout.EAST);
	}

	private void designPanel2() {
		playerPanel_container = new JPanel();
		playerPanel_container.setLayout(new BorderLayout());

		status_panel_container = new JPanel();
		status_panel_container.setPreferredSize(new Dimension(
				((int) (SIDE_WIDTH * this.width)),
				(int) (SIDE_HEIGHT * this.height)));
		status_panel_container.setLayout(new FlowLayout());
		p1panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (PLAYER_PNL_H_SCALAR * SIDE_HEIGHT * this.height));

		grid_container = new JPanel();
		p1panel.initPanelForNewGame(JKemik.game.getPlayer1().getName(),
				JKemik.game.getPlayer1().getColor());
		grid = Grid.getInstance(JKemik.settings_t.getSizes().get(0));//
		Tools.resetMaxWin(Grid.squareCount(), JKemik.settings_t);

		System.out.println("GRID SIZE: ");
		settings_p = new SettingsPanel(500, 400);

		panel23 = new JPanel();
		panel23.setPreferredSize(new Dimension((int) (.01 * this.width),
				(int) (SIDE_HEIGHT * this.height)));
		panel23.setLayout(new BorderLayout(1, 1));

		p2panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (.25 * SIDE_HEIGHT * this.height));

		playerPanel_container.add(p1panel, BorderLayout.WEST);
		playerPanel_container.add(p2panel, BorderLayout.EAST);

		gameConfigPanel();
		// gameSettingPanel();

		status_panel_container.add(playerPanel_container);
		status_panel_container.add(config_container);// new
		status_panel_container.add(setupP);
		status_panel_container.add(controler_panel);
		// panel21.add(manual);

		// panel21.add(p1panel, BorderLayout.NORTH);
		grid_container.add(grid);
		grid_container.add(settings_p);

		// panel23.add(p2panel, BorderLayout.NORTH);
		// panel23.add(manual, BorderLayout.SOUTH);

		p2panel.initPanelForNewGame(JKemik.game.getPlayer2().getName(),
				JKemik.game.getPlayer2().getColor());

		panel2.add(panel23, BorderLayout.WEST);
		panel2.add(grid_container, BorderLayout.CENTER);
		panel2.add(status_panel_container, BorderLayout.EAST);

	}

	private void designPanel3() {
		createPanel31();
		gameSetupPanel();
		createControlerPanel();

		System.out.println("Panel33: " + (CORNER_WIDTH * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));
	}

	private void createControlerPanel() {
		controler_panel = new JPanel();
		controler_panel.setLayout(new GridLayout(5, 1, 20, 20));

		pass_turn = new JButton(messages.getString("passB"));
		// undo = new JButton("Undo");//messages.getString("captureMode")
		undo = new JButton(messages.getString("undoB"));
		// refresh = new JButton("Refresh");
		refresh = new JButton(messages.getString("refreshB"));
		// manual_c = new JCheckBox("Capture Mode");
		manual_c = new JCheckBox(messages.getString("captureMode"));
		decoratebuttons(Globals.BTN_BGD_COLOR, Globals.BTN_FRD_COLOR);
		manual = new JCheckBox(messages.getString("manualModel"));

		controler_panel.add(pass_turn);
		controler_panel.add(undo);
		controler_panel.add(refresh);
		controler_panel.add(manual_c);
		controler_panel.add(manual);

		pass_turn.setVisible(false);
		undo.setVisible(false);
		manual_c.setVisible(false);
	}

	private void gameConfigPanel() {
		config_container = new JPanel();
		config_container.setLayout(new GridLayout(3, 2));
		// panel31_container.setBorder(BorderFactory.createLineBorder(BOARD_COLOR));
		AutoCap = new JLabel(JKemik.settings_t.getAutoCaptureStatus());
		AutoCap.setForeground(Color.WHITE);
		AutoPass = new JLabel(JKemik.settings_t.getAutoPassStatus());
		AutoPass.setForeground(Color.WHITE);
		Win = new JLabel("" + JKemik.settings_t.getMaxWinVal());
		Win.setForeground(Color.WHITE);

		la = new JLabel(" " + messages.getString("capturel"));
		lb = new JLabel(" " + messages.getString("passl"));
		lc = new JLabel(" " + messages.getString("winl"));
		decorateLabelss(BoardFrame.BOARD_COLOR);

		config_container.add(la);
		config_container.add(AutoCap);
		config_container.add(lb);
		config_container.add(AutoPass);
		config_container.add(lc);
		config_container.add(Win);
	}

	private void createPanel31() {
		down_left = new JPanel();
		progressB = new JProgressBar(0, PROGRESS_BAR_MAX);
		down_left.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width), (int) (.1 * this.height)));

		// panel31.add(panel31_container, BorderLayout.NORTH);// new
		down_left.add(progressB, BorderLayout.SOUTH);
		panel3.add(down_left, BorderLayout.WEST);
		// panel21.add(panel31);
	}

	private void gameSetupPanel() {
		panel32 = new JPanel();

		blank1 = new JLabel(" ");
		blank2 = new JLabel(" ");
		blank3 = new JLabel(" ");
		blank4 = new JLabel(" ");
		blank5 = new JLabel(" ");
		blank6 = new JLabel(" ");

		setupP = new JPanel();
		setupP.setLayout(new GridLayout(3, 1));

		label1 = new JLabel(JKemik.template.getP1_name());
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		label2 = new JLabel(JKemik.template.getP2_name());
		label2.setHorizontalAlignment(SwingConstants.CENTER);

		Holder1 = new JPanel();
		Holder2 = new JPanel();
		Holder3 = new JPanel();

		pColor1 = new RotateColor((int) BOTTOM_COLOR_P_W,
				(int) BOTTOM_COLOR_P_H);
		pColor1.rotateColor(JKemik.template.getP1_c());
		pColor2 = new RotateColor((int) BOTTOM_COLOR_P_W,
				(int) BOTTOM_COLOR_P_H);
		pColor2.rotateColor(JKemik.template.getP2_c());

		setGridsize(JKemik.settings_t.getSizes());
		l1 = new RotateLabel(getGridsize());
		l1.setText(JKemik.template.gridSizeToString());
		l2 = new RotateLabel(this.gameType);
		l2.setText(JKemik.settings_t.getTheme());
		startG = new JButton(messages.getString("startGameB"));

		Holder1.setLayout(new GridLayout(4, 1));
		Holder1.add(blank1);
		Holder1.add(label1);
		Holder1.add(pColor1);
		Holder1.add(blank2);

		Holder2.setLayout(new GridLayout(3, 1));
		Holder2.add(l1);
		Holder2.add(l2);
		Holder2.add(startG);

		Holder3.setLayout(new GridLayout(4, 1));
		Holder3.add(blank3);
		Holder3.add(label2);
		Holder3.add(pColor2);
		Holder3.add(blank4);

		setupP.add(Holder1);
		setupP.add(Holder3);
		setupP.add(Holder2);

		System.out.println("Panel32: " + (P2_W * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));
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

		Grid.setGridLineCol(BoardFrame.THEME_COLOR);
		panel1.setBackground(BoardFrame.THEME_COLOR);
		panel11.setBackground(BoardFrame.THEME_COLOR);
		panel12.setBackground(BoardFrame.THEME_COLOR);
		playerPanel_container.setBackground(BoardFrame.THEME_COLOR);

		panel2.setBackground(BoardFrame.THEME_COLOR);
		status_panel_container.setBackground(BoardFrame.THEME_COLOR);
		grid_container.setBackground(BoardFrame.THEME_COLOR);
		panel23.setBackground(BoardFrame.THEME_COLOR);
		settings_p.setBackground(BoardFrame.THEME_COLOR);

		panel3.setBackground(BoardFrame.THEME_COLOR);
		down_left.setBackground(BoardFrame.THEME_COLOR);
		panel32.setBackground(BoardFrame.THEME_COLOR);
		controler_panel.setBackground(BoardFrame.THEME_COLOR);
		// panel331.setBackground(BoardFrame.THEME_COLOR);
		// panel332.setBackground(BoardFrame.THEME_COLOR);
		config_container.setBackground(BoardFrame.THEME_COLOR);
		progressB.setBackground(BoardFrame.THEME_COLOR);
		progressB.setForeground(BOARD_COLOR);

		blank1.setBackground(BOARD_COLOR);
		blank2.setBackground(BOARD_COLOR);
		setupP.setBackground(BoardFrame.CPANEL_COLOR);
		Holder1.setBackground(BoardFrame.CPANEL_COLOR);
		Holder2.setBackground(BoardFrame.CPANEL_COLOR);
		Holder3.setBackground(BoardFrame.CPANEL_COLOR);

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

	public static void uiLooksUpdate(STemplate s, GTemplate t) {
		if (s.isGameSetupMode()) {
			print_point.setText("" + messages.getString("gameSetupMode"));
			boostLabel(settings, Color.WHITE, BoardFrame.THEME_COLOR);
			updateSettingPanel();
			translateUI();
			enableGameControlPanel();
			displayGrid(true);
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
		}
	}

	public String[] getGridsize() {
		return gridsize;
	}

	public void setGridsize(ArrayList<GridDimension> sizes) {
		int len = sizes.size();
		this.gridsize = new String[len];
		int index = 0;
		for (GridDimension s : sizes) {
			this.gridsize[index] = s.toString();
			index++;
		}
	}

	public double height = 0.0, width = 0.0;
	public static JProgressBar progressB;
	public static JLabel p3_label1, p3_label2;
	public static JLabel print_point;
	public static JPanel panel1, panel2, panel3;
	public static JPanel panel11, panel12, panel13;
	public static JPanel status_panel_container, grid_container, panel23,
			playerPanel_container;
	public static JPanel down_left, setupP, panel32, controler_panel,
			config_container;
	public static JPanel panel331, panel332;
	public static JPanel Holder1, Holder2, Holder3;
	public static JLabel label1, label2;
	public static JLabel help;
	public static JButton refresh, startG, undo, pass_turn, saveSet;
	public static JCheckBox manual_c, manual;
	// public static JButton pass_turn;
	// public static JButton saveSet;
	public static JLabel blank1, blank2, blank3, blank4, blank5, blank6;
	public static JLabel la, lb, lc;
	public static RotateColor pColor1, pColor2;
	public static JLabel AutoCap, AutoPass, Win, exit;
	public static RotateLabel l1, l2;
	public static PlayerPanel p1panel, p2panel;
	public static JLabel Game_status, settings;
	private String[] gridsize = { "32x20", "64x40", "8x5", "16x10" };
	private String[] gameType = { "Origins", "Jkemik", "Classic", "Geeky" };
	public static final double CORNER_WIDTH = .35, CORNER_HEIGHT = .04,
			SIDE_WIDTH = .15, SIDE_HEIGHT = .8, PLAYER_PNL_W_SCALAR = .0688,
			PLAYER_PNL_H_SCALAR = .25, P2_W = .26, BOTTOM_COLOR_P_W = 80,
			BOTTOM_COLOR_P_H = 12;// SIDE_WIDTH = .0994, PLAYER_PNL_W_SCALAR =
									// .08
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