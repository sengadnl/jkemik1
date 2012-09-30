package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.JKemik;


import utilities.Globals;

public class SettingsPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Where member variables are declared:

	static JLabel label;
	protected static int COUNTER = 1;
	protected static int MAX_VAL = 33;
	public static JButton save;
	private static JPanel l1;
	private static JPanel l2;
	private static JPanel l3;

	public static RotateLabel auto_capture;
	public static RotateLabel auto_turn_pass;
	public static RotateColor left_color;
	public static RotateColor right_color;
	public static JTextField max_win;
	private int maxWinVal;

	private String[] auto_cap = { "ON", "OFF" };
	private String[] auto_t_p = { "ON", "OFF" };
	private static java.awt.Container container;

	public SettingsPanel(int w, int h) {
		container = getContentPane();
		container.setBackground(new Color(0, 0, 0));
		maxWinVal = 2;
		setLayout(new BorderLayout());
		setSize(w, h);
		//setUndecorated(true);
		setLocation(500, 300);
		buildPane();
		setVisible(false);
	}

	public static JTextField getMax_win() {
		return max_win;
	}

	public static void setMax_win(String maxWin) {
		max_win.setText(maxWin);
	}

	public int getMaxWinVal() {
		return maxWinVal;
	}

	public void setMaxWinVal(int maxWinVal) {
		this.maxWinVal = maxWinVal;
	}

	public void setAutoCap(String ac) {
		ac = ac.toUpperCase();
		if (!auto_capture.getText().equals(ac)) {
			auto_capture.rotateLabel();
		}
	}

	public void setAutoPass(String ap) {
		ap = ap.toUpperCase();
		if (!auto_turn_pass.getText().equals(ap)) {
			auto_turn_pass.rotateLabel();
		}
	}
	public void buildPane() {
		JKIcon icon = new JKIcon("media/jkemik-logo-small.gif", "");
		save = new JButton("Done");
		save.setBackground(Globals.BTN_BGD_COLOR);
		save.setForeground(Globals.BTN_FRD_COLOR);
		l1 = new JPanel();
		l1.setBackground(BoardFrame.BOARD_COLOR);
		l2 = new JPanel();
		l2.setBackground(BoardFrame.BOARD_COLOR);
		l2.setLayout(new GridLayout(3, 2));
		l3 = new JPanel();
		l3.setBackground(BoardFrame.BOARD_COLOR);

		auto_capture = new RotateLabel(auto_cap);
		auto_turn_pass = new RotateLabel(auto_t_p);
		max_win = new JTextField("" + JKemik.settings_t.getMaxWinVal());
		max_win.setBackground(Color.GRAY);
		max_win.setForeground(Color.WHITE);
		max_win.setCaretColor(Color.GREEN);

		JLabel label1 = new JLabel("  Auto capture:");
		label1.setForeground(Color.YELLOW);
		
		JLabel label2 = new JLabel("  Auto turn pass:");
		label2.setForeground(Color.YELLOW);
		
		JLabel label3 = new JLabel("  Manual Capture:");
		label3.setForeground(Color.YELLOW);
		
		JLabel label4 = new JLabel("  Max Win:");
		label4.setForeground(Color.YELLOW);

		add(l1, BorderLayout.NORTH);
		add(l2, BorderLayout.CENTER);
		add(l3, BorderLayout.SOUTH);

		l1.add(icon.createIcon());
		l2.add(label1);
		l2.add(auto_capture);
		l2.add(label2);
		l2.add(auto_turn_pass);
		l2.add(label4);
		l2.add(max_win);
		l3.add(save);

	}

	public static RotateLabel getAuto_capture() {
		return auto_capture;
	}

	public static void setAuto_capture(RotateLabel autoCapture) {
		auto_capture = autoCapture;
	}

	public static RotateLabel getAuto_turn_pass() {
		return auto_turn_pass;
	}

	public static void setAuto_turn_pass(RotateLabel autoTurnPass) {
		auto_turn_pass = autoTurnPass;
	}

	public static void main(String[] args) {
		SettingsPanel settings = new SettingsPanel(300, 300);
		settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settings.setVisible(true);
	}
}
