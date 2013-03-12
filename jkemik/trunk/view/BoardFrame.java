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

//		 if (BoardFrame.getThereIsSavedGame() == 0) {
//		 } else {
//		 }
		
		progressB.setVisible(false);
		
		//System Preferences Events
		ViewEvents.saveSettingsAction();
		ViewEvents.onAutoCaptureAction();
		ViewEvents.onAutoPassTurnAction();
		ViewEvents.networkGameListener();
		ViewEvents.hvsHListener();
		ViewEvents.hvsAIListener();
		
		//ViewEvents.refreshListener();
		ViewEvents.modeToggleActionListener();
		uiLooksUpdate(JKemik.settings_t, JKemik.template);
		ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
		print_point.setText("" + messages.getString("gameSetupMode"));
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
			add(panel1, BorderLayout.NORTH);

			panel2 = new JPanel();
			panel2.setLayout(new BorderLayout());
			panel2.setPreferredSize(new Dimension((int) (.8 * this.width),
					(int) (SIDE_HEIGHT * this.height)));

			add(panel2, BorderLayout.CENTER);

			panel3 = new JPanel();
			panel3.setLayout(new BorderLayout());// 10,10
			panel3.setPreferredSize(new Dimension((int) this.width,
					(int) (CORNER_HEIGHT * this.height)));
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

		panel21 = new JPanel();
		panel21.setPreferredSize(new Dimension((int) (SIDE_WIDTH * this.width),
				(int) (SIDE_HEIGHT * this.height)));
		panel21.setLayout(new BorderLayout(10, 10));
		p1panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (PLAYER_PNL_H_SCALAR * SIDE_HEIGHT * this.height));
		panel21.add(p1panel, BorderLayout.NORTH);

		panel22 = new JPanel();
		//panel22.setOpaque(false);
		p1panel.initPanelForNewGame(JKemik.game.getPlayer1().getName(),
				JKemik.game.getPlayer1().getColor());

		grid = Grid.getInstance((int) JKemik.template.getG_size());//
		Tools.resetMaxWin(Grid.squareCount(), JKemik.settings_t);
		settings_p = new SettingsPanel(500, 400);
		
		panel23 = new JPanel();
		panel23.setPreferredSize(new Dimension((int) (SIDE_WIDTH * this.width),
				(int) (SIDE_HEIGHT * this.height)));
		panel23.setLayout(new BorderLayout(10, 10));

		manual = new JCheckBox(messages.getString("manualModel"));
		p2panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (.25 * SIDE_HEIGHT * this.height));

		panel22.add(grid);
		panel22.add(settings_p);

		panel23.add(p2panel, BorderLayout.NORTH);
		panel23.add(manual, BorderLayout.SOUTH);

		p2panel.initPanelForNewGame(JKemik.game.getPlayer2().getName(),
				JKemik.game.getPlayer2().getColor());

		panel2.add(panel21, BorderLayout.WEST);
		panel2.add(panel22, BorderLayout.CENTER);
		panel2.add(panel23, BorderLayout.EAST);
	}

	private void designPanel3() {
		createPanel31();
		createPanel32();
		createPanel33();

		System.out.println("Panel33: " + (CORNER_WIDTH * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));
	}

	private void createPanel33() {
		panel33 = new JPanel();
		panel33.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		panel331 = new JPanel();
		panel332 = new JPanel();

		// pass_turn = new JButton("Pass");
		pass_turn = new JButton(messages.getString("passB"));
		// undo = new JButton("Undo");//messages.getString("captureMode")
		undo = new JButton(messages.getString("undoB"));
		// refresh = new JButton("Refresh");
		refresh = new JButton(messages.getString("refreshB"));
		// manual_c = new JCheckBox("Capture Mode");
		manual_c = new JCheckBox(messages.getString("captureMode"));
		decoratebuttons(Globals.BTN_BGD_COLOR, Globals.BTN_FRD_COLOR);

		panel331.add(pass_turn);
		panel331.add(undo);
		// panel331.add(capture);

		panel332.add(refresh);
		panel332.add(manual_c);

		pass_turn.setVisible(false);
		undo.setVisible(false);
		manual_c.setVisible(false);

		panel33.add(panel331);
		panel33.add(panel332);

		panel3.add(panel33, BorderLayout.EAST);
		// ---------------------------
	}

	private void createPanel31() {
		panel31 = new JPanel();
		panel31.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));
		panel31_container = new JPanel();
		panel31_container.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) ((CORNER_HEIGHT * this.height)) / 2));
		progressB = new JProgressBar(0, PROGRESS_BAR_MAX);
		// progressB.setIndeterminate(true);

		AutoCap = new JLabel(JKemik.settings_t.getAutoCaptureStatus());
		AutoCap.setForeground(Color.WHITE);
		AutoPass = new JLabel(JKemik.settings_t.getAutoPassStatus());
		AutoPass.setForeground(Color.WHITE);
		Win = new JLabel("" + JKemik.settings_t.getMaxWinVal());
		Win.setForeground(Color.WHITE);

		la = new JLabel(" " + messages.getString("capturel"));
		lb = new JLabel(" " + messages.getString("passl"));
		lc = new JLabel(" " + messages.getString("winl"));
		decorateLabelss(Color.ORANGE);

		panel31_container.add(la);
		panel31_container.add(AutoCap);
		panel31_container.add(lb);
		panel31_container.add(AutoPass);
		panel31_container.add(lc);
		panel31_container.add(Win);
		// panel31_container.add(panel31);//new

		panel31.add(panel31_container, BorderLayout.NORTH);// new
		panel31.add(progressB, BorderLayout.SOUTH);
		panel3.add(panel31, BorderLayout.WEST);
	}

	private void createPanel32() {
		panel32 = new JPanel();

		blank1 = new JLabel(" ");
		blank2 = new JLabel(" ");
		blank3 = new JLabel(" ");
		blank4 = new JLabel(" ");
		blank5 = new JLabel(" ");
		blank6 = new JLabel(" ");

		controlP = new JPanel();
		controlP.setLayout(new BorderLayout(12, 12));
		// controlP.setBorder(BorderFactory.createLineBorder(Color.yellow));

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

		l1 = new RotateLabel(this.gridsize);
		l1.setText(JKemik.template.gridSizeToString());
		l2 = new RotateLabel(this.gameType);
		l2.setText(JKemik.settings_t.getTheme());

		// save = new JButton("START GAME");
		// System.out.println("Jbutton: " + );
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

		controlP.setPreferredSize(new Dimension((int) (P2_W * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		controlP.add(Holder1, BorderLayout.WEST);
		controlP.add(Holder2, BorderLayout.CENTER);
		controlP.add(Holder3, BorderLayout.EAST);

		// panel3.add(controlP, BorderLayout.CENTER);
		// panel32.add(controlP);
		panel3.add(controlP, BorderLayout.CENTER);
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
		Color c  = Tools.boost(BoardFrame.BOARD_COLOR, Globals.FADE_VARIANT);
		l1.setForeground(Tools.fade(c));
		l2.setForeground(Tools.fade(c));
		label1.setForeground(Tools.fade(JKemik.template.getP1_c()));
		label2.setForeground(Tools.fade(JKemik.template.getP2_c()));
		pColor1.setBackground(Tools.fade(JKemik.template.getP1_c()));
		pColor2.setBackground(Tools.fade(JKemik.template.getP2_c()));
		//fadeButton(startG);
	}

	public static void enableGameControlPanel() {
		l1.setForeground(Tools.boost(Color.WHITE, Globals.FADE_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE,  Globals.FADE_VARIANT));

		label1.setText(JKemik.template.getP1_name());
		label1.setForeground(Color.WHITE);
		label2.setText(JKemik.template.getP2_name());
		label2.setForeground(Color.WHITE);

		p1panel.initPanelForNewGame("", JKemik.template.getP1_c());
		p2panel.initPanelForNewGame("", JKemik.template.getP2_c());

		pColor1.setBackground(JKemik.template.getP1_c());
		pColor2.setBackground(JKemik.template.getP2_c());
		//startG.setBackground(new Color(200,0, 0));
		//boostButton(startG);
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

		panel2.setBackground(BOARD_COLOR);
		panel21.setBackground(BoardFrame.THEME_COLOR);
		panel22.setBackground(BoardFrame.THEME_COLOR);
		panel23.setBackground(BoardFrame.THEME_COLOR);
		settings_p.setBackground(BoardFrame.THEME_COLOR);
		
		panel3.setBackground(BoardFrame.THEME_COLOR);
		panel31.setBackground(BoardFrame.THEME_COLOR);
		panel32.setBackground(BoardFrame.THEME_COLOR);
		panel33.setBackground(BoardFrame.THEME_COLOR);
		panel331.setBackground(BoardFrame.THEME_COLOR);
		panel332.setBackground(BoardFrame.THEME_COLOR);
		panel31_container.setBackground(BoardFrame.THEME_COLOR);
		progressB.setBackground(BoardFrame.THEME_COLOR);
		progressB.setForeground(BOARD_COLOR);

		blank1.setBackground(BoardFrame.THEME_COLOR);
		blank3.setBackground(BoardFrame.THEME_COLOR);
		controlP.setBackground(BoardFrame.CPANEL_COLOR);
		Holder1.setBackground(BoardFrame.CPANEL_COLOR);
		Holder2.setBackground(BoardFrame.CPANEL_COLOR);
		Holder3.setBackground(BoardFrame.CPANEL_COLOR);
		
		l1.setForeground(Tools.boost(Color.WHITE,Globals.LABEL_VARIANT));
		l2.setForeground(Tools.boost(Color.WHITE,Globals.LABEL_VARIANT));
		
		manual_c.setBackground(BoardFrame.THEME_COLOR);
		manual_c.setForeground(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		manual.setBackground(BoardFrame.THEME_COLOR);
		manual.setForeground(Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
		settings_p.setTheme(Tools.fade(BOARD_COLOR), Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
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
			decoratebuttons(BOARD_COLOR, Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR,
					Globals.LABEL_VARIANT));

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
			decoratebuttons(BOARD_COLOR, Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR,
					Globals.LABEL_VARIANT));
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
			
			decoratebuttons(BOARD_COLOR, Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR,
					Globals.LABEL_VARIANT));
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
			
			decoratebuttons(BOARD_COLOR, Tools.boost(BOARD_COLOR, Globals.LABEL_VARIANT));
			decorateLabelss(Tools.boost(BOARD_COLOR,
					Globals.LABEL_VARIANT));
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
			} else {
				undo.setVisible(true);
				manual_c.setVisible(true);
			}

			if (JKemik.settings_t.isAutoPass()) {
				pass_turn.setVisible(false);
			} else {
				undo.setVisible(true);
				pass_turn.setVisible(true);
			}

			BoardFrame.refresh.setVisible(true);
		} catch (Exception e) {

		}
	}

	public static void displayGrid(boolean display) {
		settings_p.setVisible(!display);
		panel22.repaint();
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
			print_point.setText(""
					+ messages.getString("gameSetupMode"));
			boostLabel(settings, Color.WHITE, BoardFrame.THEME_COLOR);
			displayGrid(true);
			updateSettingPanel();
			translateUI();
			enableGameControlPanel();
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
			manual.setVisible(true);
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
	
	public double height = 0.0, width = 0.0;
	public static JProgressBar progressB;
	public static JLabel p3_label1, p3_label2;
	public static JLabel print_point;
	public static JPanel panel1, panel2, panel3;
	public static JPanel panel11, panel12, panel13;
	public static JPanel panel21, panel22, panel23;
	public static JPanel panel31, controlP, panel32, panel33,
			panel31_container;
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
	public static final double CORNER_WIDTH = .35, CORNER_HEIGHT = .064,
			SIDE_WIDTH = .0994, SIDE_HEIGHT = .8, PLAYER_PNL_W_SCALAR = .08,
			PLAYER_PNL_H_SCALAR = .25, P2_W = .26, BOTTOM_COLOR_P_W = 80,
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