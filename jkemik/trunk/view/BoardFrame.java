/**
 * 
 */
package view;

import java.awt.*;

import javax.swing.*;

import api.Point;

import controler.JKemik;
import controler.ViewEvents;
import utilities.Globals;
import utilities.Tools;
import utilities.ValidateInput;

/**
 * @author Daniel Senga
 * 
 */
public class BoardFrame extends JFrame {
	/**
	 * @author Daniel Senga
	 * @throws InterruptedException
	 * @since August 2011
	 **/
	public BoardFrame(double width, double height) {

		this.width = width;
		this.height = height;
		height = 800.0;
		width = 1280.0;

		setFrameSize((int) width, (int) height);
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
		upDateSetting();

		initializeEvents();
		setTitle("J-Kemik " + Globals.VERSION);
		setVisible(true);
		pack();
	}

	private void initializeEvents() {
		JKemik.load.plus("Adding color picker 1 event...");
		ViewEvents.changeColorPanel1Action(pColor1);
		JKemik.load.plus("Adding color picker 2 event...");
		ViewEvents.changeColorPanel2Action(pColor2);
		JKemik.load.plus("Adding board size event...");
		ViewEvents.setBoardSizeAction(l1);
		JKemik.load.plus("Setting type event...");
		ViewEvents.setGameThemeAction(l2);
		JKemik.load.plus("Adding player 1 name prompt ...");
		ViewEvents.addPlayer1NameAction(label1);
		JKemik.load.plus("Adding player 2 name prompt ...");
		ViewEvents.addPlayer2NameAction(label2);
		ViewEvents.saveAction(save);
		JKemik.load.plus("Adding undo event listener...");
		ViewEvents.undoAction(undo);
		JKemik.load.plus("Adding capture event listener..."); // 21
		ViewEvents.captureAction(capture);
		JKemik.load.plus("Setting Grid initial state...");
		grid.removeMouseListener(ViewEvents.gridListener);
		JKemik.load.plus("Setting cursor initial state...");// 24
		grid.removeMouseMotionListener(ViewEvents.gridListener);
		JKemik.load.plus("ViewEvents.ExitGameEvent();...");// 25
		ViewEvents.ExitGameEvent();
		JKemik.load.plus("ViewEvents.ExitGameEvent();...");// 25
		ViewEvents.settingsLabelAction();

		ViewEvents.passTurnAction(pass_turn);
		ViewEvents.debugListener();
		ViewEvents.saveSettingsAction();
		ViewEvents.onAutoCaptureAction();
		ViewEvents.onAutoPassTurnAction();
		ViewEvents.exitListener();
		ViewEvents.helpListener();

		fadeButton(pass_turn);
		fadeButton(capture);
		fadeButton(undo);

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
	}

	private void designPanel1() {
		panel11 = new JPanel();
		panel11.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		panel11.setLayout(new BorderLayout());

		JKIcon icon = new JKIcon("media/jkemik-logo-small.gif", "");
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

		Game_status = new JLabel("NEW");
		Game_status.setForeground(Color.GREEN);

		settings = new JLabel("OPTIONS");
		settings.setForeground(new Color(150, 150, 255));

		exit = new JLabel("EXIT");
		exit.setForeground(Color.RED);

		help = new JLabel("HELP");
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

		p1panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (PLAYER_PNL_H_SCALAR * SIDE_HEIGHT * this.height));
		panel21.add(p1panel);
		p1panel.initPanelForNewGame(JKemik.template.getP2_name(), Color.WHITE);

		grid = new Grid((int) JKemik.template.getG_size());
		ViewEvents.gridMouseAction(grid);

		panel23 = new JPanel();
		panel23.setPreferredSize(new Dimension((int) (SIDE_WIDTH * this.width),
				(int) (SIDE_HEIGHT * this.height)));

		p2panel = new PlayerPanel((int) (PLAYER_PNL_W_SCALAR * this.width),
				(int) (.25 * SIDE_HEIGHT * this.height));
		panel23.add(p2panel);
		p2panel.initPanelForNewGame(JKemik.template.getP2_name(), Color.WHITE);

		panel2.add(panel21, BorderLayout.WEST);
		panel2.add(grid, BorderLayout.CENTER);
		panel2.add(panel23, BorderLayout.EAST);
	}

	private void designPanel3() {
		panel31 = new JPanel();
		panel33 = new JPanel();
		panel31.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));
		createPanel31();
		panel3.add(panel31, BorderLayout.WEST);

		System.out.println("Panel31: " + (CORNER_WIDTH * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));

		createPanel32();
		createPanel33();

		System.out.println("Panel33: " + (CORNER_WIDTH * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));
	}

	private void createPanel33() {
		panel33.setPreferredSize(new Dimension(
				(int) (CORNER_WIDTH * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		pass_turn = new JButton("PASS");
		pass_turn.setBackground(new Color(255, 150, 0));
		pass_turn.setForeground(new Color(255, 255, 200));
		panel33.add(pass_turn);
		pass_turn.setVisible(false);

		undo = new JButton("UNDO");
		undo.setBackground(new Color(0, 0, 200));
		undo.setForeground(new Color(255, 255, 255));
		panel33.add(undo);
		undo.setVisible(false);

		capture = new JButton("AMBUSH");
		capture.setBackground(new Color(0, 200, 0));
		capture.setForeground(new Color(255, 255, 255));
		panel33.add(capture);
		capture.setVisible(false);
		
		debug = new JButton("DEBUG");
		debug.setBackground(new Color(0, 0, 200));
		debug.setForeground(new Color(255, 200, 255));
		panel33.add(debug);
		undo.setVisible(false);
		
		panel3.add(panel33, BorderLayout.EAST);
	}

	private void createPanel31() {

		AutoCap = new JLabel(JKemik.settings_t.getAutoCaptureStatus());
		AutoCap.setForeground(Color.WHITE);
		AutoPass = new JLabel(JKemik.settings_t.getAutoPassStatus());
		AutoPass.setForeground(Color.WHITE);
		// System.out.println("....... win--" +
		// JKemik.settings_t.getMaxWinVal());
		Win = new JLabel("" + JKemik.settings_t.getMaxWinVal());
		Win.setForeground(Color.WHITE);

		JLabel la = new JLabel("Auto Capture");
		la.setForeground(Color.ORANGE);

		JLabel lb = new JLabel("Auto Pass");
		lb.setForeground(Color.ORANGE);

		JLabel lc = new JLabel("Win");
		lc.setForeground(Color.ORANGE);

		panel31.add(la);
		panel31.add(AutoCap);
		panel31.add(lb);
		panel31.add(AutoPass);
		panel31.add(lc);
		panel31.add(Win);
	}

	public void createPanel32() {
		blank1 = new JLabel(" ");
		blank2 = new JLabel(" ");
		blank3 = new JLabel(" ");
		blank4 = new JLabel(" ");
		blank5 = new JLabel(" ");
		blank6 = new JLabel(" ");

		panel32 = new JPanel();
		panel32.setLayout(new BorderLayout(5, 5));

		label1 = new JLabel(JKemik.template.getP1_name());
		label1.setForeground(Color.WHITE);
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		label2 = new JLabel(JKemik.template.getP2_name());
		label2.setForeground(Color.WHITE);
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
		l2 = new RotateLabel(this.gameType);

		save = new JButton("SAVE");
		save.setBackground(new Color(200, 0, 0));
		save.setForeground(new Color(255, 255, 255));

		Holder1.setLayout(new GridLayout(4, 1));
		Holder1.add(blank1);
		Holder1.add(label1);
		Holder1.add(pColor1);
		Holder1.add(blank2);

		Holder2.setLayout(new GridLayout(3, 1));
		Holder2.add(l1);
		Holder2.add(l2);
		Holder2.add(save);

		Holder3.setLayout(new GridLayout(4, 1));
		Holder3.add(blank3);
		Holder3.add(label2);
		Holder3.add(pColor2);
		Holder3.add(blank4);

		panel32.setPreferredSize(new Dimension((int) (P2_W * this.width),
				(int) (CORNER_HEIGHT * this.height)));

		panel32.add(Holder1, BorderLayout.WEST);
		panel32.add(Holder2, BorderLayout.CENTER);
		panel32.add(Holder3, BorderLayout.EAST);

		panel3.add(panel32, BorderLayout.CENTER);
		System.out.println("Panel32: " + (P2_W * this.width) + " X "
				+ (CORNER_HEIGHT * this.height));
	}

	public static void desableGameControlPanel() {
		l1.setForeground(Tools.fade(Color.WHITE));
		l2.setForeground(Tools.fade(Color.WHITE));
		label1.setForeground(Tools.fade(Color.WHITE));
		label2.setForeground(Tools.fade(Color.WHITE));
		pColor1.setBackground(Tools.fade(pColor1.getBackground()));
		pColor2.setBackground(Tools.fade(pColor2.getBackground()));
	}

	public static void enableGameControlPanel() {
		l1.setForeground(Tools.boost(l1.getBackground()));
		l2.setForeground(Tools.boost(l2.getBackground()));

		label1.setForeground(Tools.boost(l1.getBackground()));
		label2.setForeground(Tools.boost(l1.getBackground()));

		p1panel.initPanelForNewGame("", pColor1.getBackground());
		p2panel.initPanelForNewGame("", pColor2.getBackground());

		pColor1.setBackground(JKemik.template.getP1_c());
		pColor2.setBackground(JKemik.template.getP2_c());

		boostButton(save);
	}

	public static void highlightP1() {
		label1.setForeground(new Color(250, 0, 250));
	}

	public static void highlightP2() {
		label2.setForeground(new Color(250, 0, 250));
	}

	public static void resetBGCP() {
		label1.setForeground(Color.WHITE);
		label2.setForeground(Color.WHITE);
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
	public static void boostLabel(JLabel label) {
		Color fore = label.getForeground();
		Color back = label.getBackground();
		label.setForeground(Tools.boost(fore));
		label.setBackground(Tools.boost(back));
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
		panel23.setBackground(BoardFrame.THEME_COLOR);

		panel3.setBackground(BoardFrame.THEME_COLOR);
		panel31.setBackground(BoardFrame.THEME_COLOR);
		panel33.setBackground(BoardFrame.THEME_COLOR);

		blank1.setBackground(BoardFrame.THEME_COLOR);
		blank3.setBackground(BoardFrame.THEME_COLOR);
		panel32.setBackground(BoardFrame.CPANEL_COLOR);
		Holder1.setBackground(BoardFrame.CPANEL_COLOR);
		Holder2.setBackground(BoardFrame.CPANEL_COLOR);
		Holder3.setBackground(BoardFrame.CPANEL_COLOR);
	}

	public void setTheme(String str) {
		if (str.equals("Jkemik")) {
			setSkin(new Color(70, 70, 0), new Color(90, 90, 0), new Color(150,
					150, 0));
			pColor1.setArrayColors(Globals.CHEMIK_COLOR);
			pColor2.setArrayColors(Globals.CHEMIK_COLOR);
			pColor1.rotateColor(1);
			pColor1.setBackground(Globals.CHEMIK_COLOR[1]);
			pColor2.setBackground(Globals.CHEMIK_COLOR[0]);
//			p1panel.getHolder().setBackground(BOARD_COLOR);
//			p2panel.getHolder().setBackground(BOARD_COLOR);
			
		} else if (str.equals("Origins")) {
			setSkin(new Color(0, 30, 0), new Color(10, 30, 0), new Color(60,
					90, 60));
			pColor1.setArrayColors(Globals.ORIGINE_COLOR);
			pColor2.setArrayColors(Globals.ORIGINE_COLOR);
			pColor1.rotateColor(1);
			pColor1.setBackground(Globals.ORIGINE_COLOR[1]);
			pColor2.setBackground(Globals.ORIGINE_COLOR[0]);
//			p1panel.getHolder().setBackground(BOARD_COLOR);
//			p2panel.getHolder().setBackground(BOARD_COLOR);
		} else if (str.equals("Geeky")) {
			setSkin(new Color(0, 0, 0), new Color(0, 0, 0), new Color(40,
					60, 40));
			pColor1.setArrayColors(Globals.CLASSIC_COLOR);
			pColor2.setArrayColors(Globals.CLASSIC_COLOR);
			pColor1.rotateColor(1);
			pColor1.setBackground(Globals.CLASSIC_COLOR[1]);
			pColor2.setBackground(Globals.CLASSIC_COLOR[0]);
//			p1panel.getHolder().setBackground(BOARD_COLOR);
//			p2panel.getHolder().setBackground(BOARD_COLOR);
		} else {
			setSkin(new Color(50, 50, 50), new Color(90, 90, 90), new Color(
					100, 100, 100));
			pColor1.setArrayColors(Globals.GEECKY_COLOR);
			pColor2.setArrayColors(Globals.GEECKY_COLOR);
			pColor1.rotateColor(1);
			pColor1.setBackground(Globals.GEECKY_COLOR[1]);
			pColor2.setBackground(Globals.GEECKY_COLOR[0]);
//			p1panel.getHolder().setBackground(BOARD_COLOR);
//			p2panel.getHolder().setBackground(BOARD_COLOR);
		}
	}

	public static void upDateSetting() {
		try {
			if (JKemik.settings_t.isAutoCapture()) {
				BoardFrame.AutoCap.setText("ON");
			} else {
				BoardFrame.AutoCap.setText("OFF");
			}

			if (JKemik.settings_t.isAutoPass()) {
				BoardFrame.AutoPass.setText("ON");
			} else {
				BoardFrame.AutoPass.setText("OFF");
			}
			String str = "" + JKemik.settings_t.getMaxWinVal();
			BoardFrame.Win.setText(str);
		} catch (Exception e) {

		}
	}

	public static boolean isMakingGame() {
		return makingGame;
	}

	public static void setMakingGame(boolean inOptions) {
		BoardFrame.makingGame = inOptions;
	}
	public static void newGameGuiUpdate(){
		BoardFrame.desableGameControlPanel();
		BoardFrame.pColor1.removeMouseListener(ViewEvents.p1Listener);
		BoardFrame.pColor2.removeMouseListener(ViewEvents.p2Listener);
		BoardFrame.label1.removeMouseListener(ViewEvents.n1Listener);
		BoardFrame.label2.removeMouseListener(ViewEvents.n2Listener);
		BoardFrame.l1.removeMouseListener(ViewEvents.gridSizeListener);
		BoardFrame.l2.removeMouseListener(ViewEvents.gameThemeListener);
		BoardFrame.settings.removeMouseListener(ViewEvents.saveSettings);
		BoardFrame.Game_status.setText("END");
		BoardFrame.Game_status.setForeground(Color.RED);

		BoardFrame.fadeLabel(BoardFrame.settings);
		BoardFrame.fadeButton(BoardFrame.save);
		BoardFrame.boostButton(BoardFrame.pass_turn);
		BoardFrame.boostButton(BoardFrame.capture);
		BoardFrame.boostButton(BoardFrame.undo);
		BoardFrame.print_point.setText("" + (new Point(0, 0)).toString());
//		BoardFrame.p1panel.initPanelForNewGame(p1n, p1c);
//		BoardFrame.p2panel.initPanelForNewGame(p2n, p2c);
		BoardFrame.save.removeMouseListener(ViewEvents.saveListener);
	}

	public double height = 0.0;
	public double width = 0.0;

	public static JProgressBar progressB;
	public static JLabel p3_label1;
	public static JLabel p3_label2;

	public static JLabel print_point;

	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;

	public static JPanel panel11;
	public static JPanel panel12;
	public static JPanel panel13;

	public static JPanel panel21;
	public static JPanel panel22;
	public static JPanel panel23;

	public static JPanel panel31;
	public static JPanel panel32;
	public static JPanel panel33;

	public static JPanel Holder1;
	public static JPanel Holder2;
	public static JPanel Holder3;

	public static JLabel label1;
	public static JLabel label2;
	public static JLabel help;

	public static JButton debug;
	public static JButton save;
	public static JButton undo;
	public static JButton capture;
	public static JButton pass_turn;

	public static JLabel blank1;
	public static JLabel blank2;
	public static JLabel blank3;
	public static JLabel blank4;
	public static JLabel blank5;
	public static JLabel blank6;

	public static RotateColor pColor1;
	public static RotateColor pColor2;

	public static JLabel AutoCap;
	public static JLabel AutoPass;
	public static JLabel Win;
	public static JLabel exit;

	public static RotateLabel l1;
	public static RotateLabel l2;
	// public static RotateLabel l3;

	public static PlayerPanel p1panel;
	public static PlayerPanel p2panel;

	public static JLabel Game_status;
	public static JLabel settings;

	private String[] gridsize = { "32x20", "64x40", "8x5", "16x10" };
	private String[] gameType = { "Origins", "Jkemik", "Google", "Geeky" };

	public static final double CORNER_WIDTH = .33;
	public static final double CORNER_HEIGHT = .064;
	public static final double SIDE_WIDTH = .0994;
	public static final double SIDE_HEIGHT = .8;
	public static final double PLAYER_PNL_W_SCALAR = .08;
	public static final double PLAYER_PNL_H_SCALAR = .25;
	public static final double P2_W = .34;

	public static final double BOTTOM_COLOR_P_W = 120;
	public static final double BOTTOM_COLOR_P_H = 14;

	public static Grid grid;

	private static java.awt.Container container;
	private static final long serialVersionUID = 1L;

	public static Color THEME_COLOR;
	public static Color CPANEL_COLOR;
	public static Color BOARD_COLOR;

	public static boolean makingGame;
	public static int COUNTER = 0;
	public static int MAX_VAL = 0;
	public static final int THEME_JKEMIK = 0;
	public static final int THEME_ORIGINS = 1;
	public static final int THEME_OLD = 3;
}