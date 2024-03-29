/**
 * 
 */
package utilities;

import java.awt.Color;

import javax.swing.JOptionPane;

import view.BoardFrame;

/**
 * @author dalet
 * 
 */
public class ValidateInput {
	public static boolean nameLength(String name){
		if (!(name.length() <= Globals.PLR_N_LEN)) {
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("validatePlayerName"),
					BoardFrame.messages.getString("wrongInput"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	private static boolean PlayerName(String name1, String name2) {
		try {
			
			if (name1.equals("") || name2.equals("")) {
				JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("emptyString"),
						BoardFrame.messages.getString("wrongInput"), JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("ilNameCombination"),
					BoardFrame.messages.getString("wrongInput"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateColors(Color p1, Color p2) {
		if (p1.equals(p2)) {
			JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("ilColorCombination"),
					BoardFrame.messages.getString("wrongInput"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateScreenResolution(int width, int height) {
		if ((width < 1280) && (height < 800)) {
			JOptionPane.showMessageDialog(null, BoardFrame.messages.getString("screenResVal1")
					+ width + "X" + height + BoardFrame.messages.getString("screenResVal2"),
					BoardFrame.messages.getString("wrongInput"), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return true;
	}
}
