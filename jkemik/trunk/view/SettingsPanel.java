package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import api.STemplate;

import controler.JKemik;
import utilities.Globals;
import utilities.Tools;

public class SettingsPanel extends JPanel{
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
	public static JComboBox languageList;
	public static JLabel label1,label2,label3,label4,label5;	
	private int maxWinVal;
	static ResourceBundle messages;

	private String[] auto_cap = { "ON", "OFF" };
	private String[] auto_t_p = { "ON", "OFF" };
	//private static java.awt.Container container;

	public SettingsPanel(int w, int h) {
		maxWinVal = 2;
		setLayout(new BorderLayout());
		setSize(w,h);
		buildPane();
	}

	public static JComboBox getLanguageList() {
		return languageList;
	}

	public static void setLanguageList(JComboBox languageList) {
		SettingsPanel.languageList = languageList;
	}

	public static JTextField getMax_win() {
		return max_win;
	}

	public static void setMax_win(int maxWin) {
		max_win.setText(maxWin + "");
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

	public static void setAutoCap(boolean value) {
		if (value) {
			auto_capture.rotateLabel("ON");
		} else {
			auto_capture.rotateLabel("OFF");
		}
	}

	public static void setAutoCap(String ac) {
		ac = ac.toUpperCase();
		if (!auto_capture.getText().equals(ac)) {
			auto_capture.rotateLabel();
		}
	}
	public static void setAutoPass(boolean value) {
		if (value) {
			auto_turn_pass.rotateLabel("ON");
		} else {
			auto_turn_pass.rotateLabel("OFF");
		}
	}
	public static void setAutoPass(String ap) {
		ap = ap.toUpperCase();
		if (!auto_turn_pass.getText().equals(ap)) {
			auto_turn_pass.rotateLabel();
		}
	}

	public void buildPane() {
		String code = Tools.languageKey(JKemik.settings_t.getLanguage());
		String properties = Tools.propertiesFilename(code);
		Locale currentLocale = new Locale(code.toLowerCase());

	//	ResourceBundle 
		messages = ResourceBundle.getBundle(properties, currentLocale);
		
		//JKIcon icon = new JKIcon("media/jkemik-small.png", "");
		save = new JButton("Done");
		save.setBackground(Globals.BTN_BGD_COLOR);
		save.setForeground(Globals.BTN_FRD_COLOR);
		l1 = new JPanel();
		l1.setBackground(BoardFrame.BOARD_COLOR);
		l2 = new JPanel();
		l2.setBackground(BoardFrame.BOARD_COLOR);
		l2.setLayout(new GridLayout(4, 2,20,20));
		l3 = new JPanel();
		l3.setBackground(BoardFrame.BOARD_COLOR);

		auto_capture = new RotateLabel(auto_cap);
		auto_turn_pass = new RotateLabel(auto_t_p);
		
		max_win = new JTextField("" + JKemik.settings_t.getMaxWinVal());
		max_win.setBackground(Color.GRAY);
		max_win.setForeground(Color.WHITE);
		max_win.setCaretColor(Color.GREEN);
		
		languageList = new JComboBox(Globals.laguageNames);//

		label1 = new JLabel("  " + messages.getString("autoCaptureL") + " : ");
		label1.setForeground(Color.YELLOW);

		label2 = new JLabel("  " + messages.getString("autoPassL") + " : ");
		label2.setForeground(Color.YELLOW);

		label3 = new JLabel("  " + messages.getString("manualCapt") + " : ");
		label3.setForeground(Color.YELLOW);

		label4 = new JLabel("  " + messages.getString("maxWinl") + " : ");
		label4.setForeground(Color.YELLOW);
		
		label5 = new JLabel("  " + messages.getString("language") + " : ");
		label5.setForeground(Color.YELLOW);
		
		save.setText(messages.getString("saveB"));

		add(l1, BorderLayout.NORTH);
		add(l2, BorderLayout.CENTER);
		add(l3, BorderLayout.SOUTH);

		//l1.add(icon.createIcon());
		l2.add(label1);
		l2.add(auto_capture);
		l2.add(label2);
		l2.add(auto_turn_pass);
		l2.add(label4);
		l2.add(max_win);
		l2.add(label5);
		l2.add(languageList);
		
		l3.add(save);

	}
	public void translateSettingsPanel(STemplate t){
		getLanguageList().setSelectedItem(t.getLanguage());
		setAutoCap(t.isAutoCapture());
		setAutoPass(t.isAutoPass());
		SettingsPanel.setMax_win(t.getMaxWinVal());
		//JKemik.settings.setVisible(true);
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
	public static void translateUI(){
		label1.setText("  " + messages.getString("autoCaptureL") + " : ");
		label2.setText("  " + messages.getString("autoPassL") + " : ");
		label3.setText("  " + messages.getString("manualCapt") + " : ");
		label4.setText("  " + messages.getString("maxWinl") + " : ");
		label5.setText("  " + messages.getString("language") + " : ");
		save.setText(messages.getString("saveB"));
	}

//	public static void main(String[] args) {
//		SettingsPanel settings = new SettingsPanel(300, 300);
//		settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		settings.setVisible(true);
//	}
}
