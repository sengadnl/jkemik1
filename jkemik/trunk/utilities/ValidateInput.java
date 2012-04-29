/**
 * 
 */
package utilities;

import java.awt.Color;

import javax.swing.JOptionPane;

/**
 * @author dalet
 * 
 */
public class ValidateInput {
	private static boolean PlayerName(String name1, String name2) {
		try {
			if (name1.equals("") || name2.equals("")) {
				JOptionPane.showMessageDialog(null, "Empty String error",
						"Wrong Input", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if (!(name1.length() <= 5) || !(name2.length() <= 5)) {
				JOptionPane.showMessageDialog(null,
						"Name must be < 5 characters",
						"Wrong Input", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "" + e.getMessage());
		}
		return true;
	}

	public static boolean names(String name1, String name2) {
		name1 = name1.toUpperCase();
		name2 = name2.toUpperCase();
		if (!PlayerName(name1, name2)) {
			return false;
		}
		if (name1.equals(name2)) {
			JOptionPane.showMessageDialog(null, "Ellegal name combination!",
					"Wrong Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateColors(Color p1, Color p2) {
		if (p1.equals(p2)) {
			JOptionPane.showMessageDialog(null, "Ellegal color combination!",
					"Wrong Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateScreenResolution(int width, int height) {
		if (!(width >= 1280) && !(height >= 800)) {
			JOptionPane.showMessageDialog(null, "Your screen resolution is "
					+ width + "X" + height + "\n"
					+ "it must be greater than or equal to (1280X800)",
					"Wrong Input", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return true;
	}
}
