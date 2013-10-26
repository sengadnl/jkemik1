/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import utilities.Tools;

/**
 * @author Daniel Senga
 * 
 */
public abstract class AbstractGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractGame(Player player1, Player player2) {
		this.player1 = player1;
		this.player1.setId(-1);
		this.player2 = player2;
		this.player2.setId(1);

		currentP = player1;
		guest = player2;
		currentP.setTurn(true);
		this.status = 0;
	}

	/**
	 * @param p
	 *            , origin of the path to capture's contour
	 * @return true if p belongs to the path and false if not
	 */
	public static boolean isPath(Point p) {
		if ((p.getStatus() != 4) && (p.getId() != guest.getId())) {

			return true;
		}
		return false;
	}

	public boolean checkEndGame() {
		if ((guest.getScore()) >= this.getMaxScore()) {
			this.status = 1;
			return true;
		}
		return false;
	}

	/**
	 * This function set the current player. It manages play turns
	 * 
	 * @param none
	 * @return void
	 * */
	public void switchPlayTurns() {
		try {
			if (this.player1.compareTo(currentP) == 0) {
				currentP = this.player2;
				guest = this.player1;
			} else {
				currentP = this.player1;
				guest = this.player2;
			}
			currentP.setTurn(true);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null,
					"in switchPlayTurns: " + e.getMessage());
		}
	}

	public void setPlayFlag() {
		currentP.setPlay_flag(1);
		guest.setPlay_flag(0);
	}

	public void unSetPlayFlag() {
		currentP.setPlay_flag(0);
		guest.setPlay_flag(1);
	}

	/**
	 * This method returns the guest player
	 * 
	 * @return the guest
	 */
	public AbstractPlayer getGuest() {
		return guest;
	}

	/**
	 * This method sets the guest player
	 * 
	 * @param guest
	 *            player
	 */
	public void setGuest(Player guest) {
		AbstractGame.guest = guest;
	}

	/**
	 * @return This method returns the current player
	 */
	public AbstractPlayer getCurrentP() {
		return currentP;
	}

	/**
	 * This function set the current player
	 * 
	 * @param current
	 *            player
	 */
	public void setCurrentP(Player current) {
		currentP = current;
	}

	public boolean isAreaEmpty(ArrayList<Point> trueArea,
			ArrayList<Point> guestPlottedPoints) {
		try {
			for (Point pArea : trueArea) {
				if (Tools.containPoint(pArea, guestPlottedPoints)) {
					return false;
				}
			}
		} catch (Exception e) {
			System.err.println("Error in Game.isAreaEmpty" + e.getMessage());
		}
		return true;
	}

	/**
	 * Whenever a cell is found in this cell it is removed from the
	 * 
	 * @param a
	 *            cell object cells: ArrayList of enemy cells
	 * @return void
	 */
	public void evalCell(Cell cell) {
		for (Cell c : guest.getCells()) {
			Point p = c.getCellContour().get(0);
			if (Tools.containPoint(p, cell.getCapturedPoints())) {
				currentP.addCapturedCells(c);
				// currentP.getCapturedCells().add(c);
				cell.setValue(cell.getValue() + c.getValue());
				cell.addCellToCell(c);
				// guest.getCells().remove(c);
				guest.setScore(guest.getScore() - c.getValue());
			}
		}
	}

	/**
	 * When this function is called, the captured cell is evaluated then added
	 * the the current player captured cell count. Four major events happen in
	 * this function: 1. Scanning for inscribed cells 2. Adding inscribed cells
	 * value to c's value 3. Now that we know the true value of c, add it to the
	 * current player's captured cells count. 4 As the addCell method executes
	 * the current player's score is updated
	 * 
	 * SCORING FORMULA --------------- SCORE = CAPTURED POINTS + INSCRIBED
	 * CELL(S) VALUE
	 * 
	 * @param the
	 *            captured cell
	 * @return the number of cuptured cells by the current player
	 */
	public void calculateScore(Cell c) {
		evalCell(c);// check for captured cells in this cell
		currentP.addCell(c);// make last a prisoner
		if ((currentP.getScore()) >= this.getMaxScore()) {
			this.status = 1;// End the Game
		}
	}

	/**
	 * @return the player1
	 */
	public AbstractPlayer getPlayer1() {
		return player1;
	}

	/**
	 * @param player1
	 *            the player1 to set
	 */
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	/**
	 * @return the player2
	 */
	public AbstractPlayer getPlayer2() {
		return player2;
	}

	/**
	 * @param player2
	 *            the player2 to set
	 */
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public boolean isEmbuche_on() {
		return embuche_on;
	}

	public void setEmbuche_on(boolean embucheOn) {
		embuche_on = embucheOn;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int max) {
		this.maxScore = max;
	}

	public Cell getTempCell() {
		return tempCell;
	}

	public void setTempCell(Cell tempCell) {
		this.tempCell = tempCell;
	}

	public String toString() {
		return "GAME\n-------------" + this.player1 + "\nVS\n" + this.player2
				+ "-----------------------------------------"
				+ "\nCurrent Player: " + currentP.getName()
				+ "\nGuest Player: " + guest.getName() + "\nEmbush: "
				+ this.embuche_on + "\nMaximum score: " + this.maxScore;
	}

	public boolean isAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	}

	/**
	 * @param status
	 *            : integer which defines the status of the game
	 * @return 0: Current, 1: Ended, -1: Inactive
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param None
	 * @return 0: Current, 1: Ended, -1: Suspended
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	public HashMap<String, Point> getCollection() {
		return this.collection;
	}

	public void setCollection(HashMap<String, Point> collection) {
		this.collection = collection;
	}

	/* Connecting dots utilities */
	public boolean AI = false;
	public boolean embuche_on = false;
	private Cell tempCell = null;
	private int status = 0;
	private int maxScore = 2;

	/* keeps track of all captured points */
	private AbstractPlayer player1;
	private AbstractPlayer player2;
	private static AbstractPlayer currentP = new Player(null, "");
	private static AbstractPlayer guest = new Player(null, "");
	private HashMap<String, Point> collection = new HashMap<String, Point>();

}