/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * @author Dalet
 * 
 */
public class Load extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Where member variables are declared:
	static JProgressBar progressBar;
	static JLabel label; 
	protected static int COUNTER = 1;
	protected static int MAX_VAL = 11;

	private static java.awt.Container container;

	public Load(int w, int h) {
		container = getContentPane();
		container.setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout());
		setSize(w, h);
		setUndecorated(true);
		setLocation(500, 300);
		creadLoadingBar();
		setVisible(true);

	}

	public void kill() {
		setVisible(false);
	}

	public void creadLoadingBar() {
		label = new JLabel("  ");
		label.setForeground(new Color(0,255,0));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);
		
		JKIcon icon = new JKIcon("media/jkemik-logo.PNG", "");
		add(icon.createIcon(), BorderLayout.CENTER);

		progressBar = new JProgressBar(0, MAX_VAL);
		progressBar.setBackground(new Color(0, 40, 0));
		progressBar.setForeground(new Color(0, 150, 0));
		progressBar.setStringPainted(true);
		add(progressBar, BorderLayout.SOUTH);

	}

	public static void increment() {
		long num = 700;
		try {
			for (int i = 1; i <= MAX_VAL; i++) {
				progressBar.setValue(i);
				Thread.sleep(num);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @return the progressBar
	 * @throws InterruptedException
	 */
	public void plus(String print){
		long num = 150;
		try {
			if (COUNTER != MAX_VAL) {
				label.setText(print);
				progressBar.setValue(COUNTER++);
				Thread.sleep(num);
			}else{
				System.out.println("Set Load invisible");
				setVisible(false);
				COUNTER = 0;
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @return the progressBar
	 */
	public static JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @param progressBar
	 *            the progressBar to set
	 */
	public static void setProgressBar(JProgressBar progressBar) {
		Load.progressBar = progressBar;
	}

//	public static void main(String[] args) throws InterruptedException {
//		Load frame = new Load(360, 200);
//		increment();
//		//frame.count();
//		//System.exit(0);
//	}
}
