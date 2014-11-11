/**
 * 
 */
package utilities;

import controler.JKemik;
import java.awt.Color;

import javax.swing.JOptionPane;

import view.BoardFrame;

/**
 * @author dalet
 * 
 */
public class ValidateInput {
        public static boolean starterPoints(int stpts){
            if(stpts >= 0 && stpts <= 2){
                return true;
            }
            JOptionPane.showMessageDialog(null,"Input must be a number in the interval [0,2]",BoardFrame.messages.getString("wrongInput"),JOptionPane.ERROR_MESSAGE);
            return false;
        }
	public static boolean maxWin(int maxWin, int highest) {
		if (maxWin < 1) {
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("maxWinValidation") + highest,
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(maxWin > highest){
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("maxWinValidation") + highest,
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	public static boolean maxPointScaler(double maxPointScaler){
		if(!(maxPointScaler >= .3 && maxPointScaler < 1)){
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("maxPointScalerValidation"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	public static boolean backtrack(int backtrack){
		if(!(backtrack > 0 && backtrack <= 10)){
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("backtrackValidation"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public static boolean nameLength(String name) {
		if ((name.length() > Globals.PLR_N_LEN)) {
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("validatePlayerName"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private static boolean PlayerName(String name1, String name2) {
		try {
                    if (name1.equals("") || name2.equals("")) {
                            JOptionPane.showMessageDialog(null,
                                            BoardFrame.messages.getString("emptyString"),
                                            BoardFrame.messages.getString("wrongInput"),
                                            JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("ilNameCombination"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateColors(Color p1, Color p2) {
		if (p1.equals(p2)) {
			JOptionPane.showMessageDialog(null,
					BoardFrame.messages.getString("ilColorCombination"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean validateScreenResolution(int width, int height) {
		if ((width < 1280) && (height < 800)) {
			JOptionPane.showMessageDialog(
					null,
					BoardFrame.messages.getString("screenResVal1") + width
							+ "X" + height
							+ BoardFrame.messages.getString("screenResVal2"),
					BoardFrame.messages.getString("wrongInput"),
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return true;
	}
}
