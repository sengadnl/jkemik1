package utilities;

//import java.lang.reflect.Method;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controler.JKemik;
import java.lang.reflect.Method;

public class Manual extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textarea;
	JScrollPane scroll;

	public Manual() {
		setSize(400, 400);
		setLayout(new BorderLayout());
		textarea = new JTextArea(40, 100);
		textarea.setBackground(Color.BLACK);
		textarea.setForeground(Color.WHITE);
		textarea.setFont(new Font("Serif", Font.PLAIN, 16));
		textarea.setLineWrap(true);
		// textarea.setWrapStyleWord(true);
		scroll = new JScrollPane(textarea);
		scroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll);
		textRules();
	}

	public void textRules() {

		textarea.append("JKEMIK RULES\n\n");
		textarea
				.append("1. The game consists of two players who alternate placing pieces on a grid.\n\n");
		textarea
				.append("2. Pieces are placed where the grid lines intersect with each other, and the goal is to form a loop of adjacent pieces around the opponent's pieces.\n\n");
		textarea
				.append("3. A complete loop of adjacent pieces that contains the other player's pieces is considered a capture, and the number of captured pieces are counted as points for the capturing player or the player who own's the loop.\n\n");
		textarea
				.append("4. To differentiate which pieces belong to which player, the piece have to be of different color.\n\n");
		textarea
				.append("5. A capture that belongs to a player can also be captured. When this happens the player who owns the captured cell (s) loses it or them in game points and these points are given to the capturing player.\n\n");
	}

	public static void rules() {
		JOptionPane.showMessageDialog(JKemik.view,
				"The game consists of two players who \n"
						+ "alternate placing pieces on a grid.\n"
						+ "Pieces are placed where the grid lines \n"
						+ "intersect with each other, and the goal \n"
						+ "is to form a loop of adjacent pieces \n"
						+ "around the opponent's pieces. \n\n"
						+ "A complete loop of adjacent pieces that\n "
						+ "contains the other player's pieces is \n"
						+ "considered a capture, and the number of \n"
						+ "captured pieces are counted as points for \n"
						+ "the capturing player or the player who \n"
						+ "own's the loop.To differentiate which \n"
						+ "pieces belong to which player, the piece \n"
						+ "have to be of different color. \n\n"
						+ "A capture that belongs to a player can also\n "
						+ "be captured. However when this happens the\n "
						+ "player who owns the captured cell (s) loses\n "
						+ "it or them in game points and these points \n"
						+ "are given to the capturing player.");

	}

	public void manualReader() throws IOException {
		FileReader reader = new FileReader("manual.txt");
		Scanner in = new Scanner(reader);

		while (in.hasNextLine()) {
			textarea.append(in.nextLine() + "\n");
		}
		in.close();
		setVisible(true);
	}

	public static void openUrl(String url) {
		String os = System.getProperty("os.name");
		Runtime runtime = Runtime.getRuntime();
		try {
			// Block for Windows Platform
			if (os.startsWith("Windows")) {
				String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
				//Process p = 
				runtime.exec(cmd);
			}
			// Block for Mac OS
			else if (os.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			}
			// Block for UNIX Platform
			else {
				String[] browsers = { "firefox", "opera", "konqueror",
						"epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (runtime.exec(new String[] { "which", browsers[count] })
							.waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					runtime.exec(new String[] { browser, url });
			}
		} catch (Exception x) {
			System.err.println("Exception occurd while invoking Browser!");
			x.printStackTrace();
		}
	}

	public static void main(String arg[]) {
		Manual m = new Manual();
		m.setVisible(true);
	}
}
