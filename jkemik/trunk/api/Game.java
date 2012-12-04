/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import controler.JKemik;
import utilities.Tools;

/**
 * @author dalet
 * 
 */
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentP = player1;
		guest = player2;
		currentP.setTurn(true);
		deadDots = new ArrayList<Point>();
		// this.selectedPoints = new ArrayList<Point>();
	}

	/**
	 * Only plotted points can be undone, dead points can not be revived
	 */
	public boolean undo() {
		System.out.println("entering undo...");
		int index = currentP.getPloted().size();
		lastp = currentP.getPloted().get(index - 1);
		if (Tools.containPoint(lastp, currentP.getConnectedPoints())) {
			return false;
		}
		if (currentP.getPloted().remove(lastp)) {// remove last point
			unSetPlayFlag();
			return true;
		}
		return false;
	}

	public Cell capture(Point o, double squareSize) {
		Cell cell = null; /* cell to be returned */

		if (currentP.buildPath(o, squareSize)) {
			ArrayList<Point> guestP = guest.getPloted();
			ArrayList<Point> current = currentP.getPloted();
			ArrayList<Point> currPlayerCaptures = currentP.getCapturedDots();
			ArrayList<Point> captured = new ArrayList<Point>();
			ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
					squareSize);

			ArrayList<Point> area = getTrueArea(current, TempArea);

			if (isAreaEmpty(area, guestP)) {
				currentP.setSelected(new ArrayList<Point>());
				return null;
			}

			/* Go through all selected dots from recursion */
			for (Point p : area) {
				if (Tools.containPoint(p, guestP)
						&& !Tools.containPoint(p, currPlayerCaptures)) {

					/* keep track of captures for this cell */
					captured.add(p);

					/* keep track of total captures for this player */
					currPlayerCaptures.add(p);
					getDeadDots().add(p);//
					guestP.remove(p);

				} else if (!Tools.containPoint(p, getDeadDots())
						&& !Tools.containPoint(p, current)
						&& !Tools.containPoint(p, guestP)) {//
					// getDeadDots().add(p);//
					deadDots.add(p);

				}/* end first if else */
			}/* end of second for loop */
			getCurrentP().getConnectedPoints().addAll(currentP.getSelected());
			cell = new Cell(getCurrentP().getSelected(), area, captured, null);// Engine.getGame().

			calculateScore(cell);

		} else {
			currentP.setSuccessful(false);
			currentP.setSelected(new ArrayList<Point>());
		}

		return cell;
	}

	public Cell capture(int squareSize) {
		Cell cell = null; /* cell to be returned */

		ArrayList<Point> guestP = guest.getPloted();
		ArrayList<Point> current = currentP.getPloted();
		ArrayList<Point> currPlayerCaptures = currentP.getCapturedDots();
		ArrayList<Point> captured = new ArrayList<Point>();
		ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
				squareSize);

		ArrayList<Point> area = getTrueArea(current, TempArea);

		if (isAreaEmpty(area, guestP)) {
			// currentP.setSelected(new ArrayList<Point>());
			return new Cell(currentP.getSelected(), area, captured, null);
		}

		/* Go through all selected dots from recursion */
		for (Point p : area) {
			if (Tools.containPoint(p, guestP)
					&& !Tools.containPoint(p, currPlayerCaptures)) {

				/* keep track of captures for this cell */
				captured.add(p);

				/* keep track of total captures for this player */
				currPlayerCaptures.add(p);
				getDeadDots().add(p);//
				guestP.remove(p);

			} else if (!Tools.containPoint(p, getDeadDots())
					&& !Tools.containPoint(p, current)
					&& !Tools.containPoint(p, guestP)) {//
				getDeadDots().add(p);//

			}/* end first if else */
		}/* end of second for loop */
		currentP.getConnectedPoints().addAll(currentP.getSelected());
		cell = new Cell(currentP.getSelected(), area, captured, null);// Engine.getGame().

		calculateScore(cell);
		return cell;
	}

	public boolean connectDots(double squareSize) {
		currentP.setSuccessful(false);

		ArrayList<Point> currentPPoints = currentP.getPloted();

		int start = currentPPoints.size() - 1;
		for (int i = start; i >= 0; i--) {
			Point currentPP = currentPPoints.get(i);
			currentP.setOrigin(currentPP);
			try {

				tempCell = capture(currentPP, squareSize);
				if (tempCell != null) {
					System.out.println("Cell was not NULL");
					return true;
				} else {
					continue;
				}
			} catch (IndexOutOfBoundsException e) {
				currentP.setSelected(new ArrayList<Point>());
				System.out.println("In connectDots(): Area out of bounds"
						+ e.getMessage());
				continue;
			} catch (NullPointerException e) {
				System.out.println("In connectDots(): " + e.getMessage());
				continue;
			}
		}
		return false;
	}

	public Cell embush(double squareSize) {
		if (JKemik.settings_t.isAutoCapture()) {
			try {
				if (connectDots(squareSize)) {
					return tempCell;
				} else {
					embuche_on = false;
				}
			} catch (Exception e) {
				System.out.println("Error in PaintComponent: capture "
						+ e.getMessage());
			}
		} else {
			if (JKemik.settings_t.isManualCapture()) {
				try {
					if (connectDots(squareSize)) {
						embuche_on = false;
						return tempCell;
					} else {
						embuche_on = false;
					}
				} catch (Exception e) {
					System.out.println("Error in PaintComponent: capture "
							+ e.getMessage());
				}
				JKemik.settings_t.setManualCapture(false);
			}
		}
		return null;
	}

	public boolean select(Point p, double squareSize) {

		if (currentP.getSelected().isEmpty()
				&& Tools.containPoint(p, getCurrentP().getPloted())
				&& !Tools.containPoint(p, getDeadDots())) {
			currentP.setOrigin(p);
			currentP.getSelected().add(p);
			this.lastp = p;
			return true;
		} else {
			if (Tools.containPoint(p, getCurrentP().getPloted())
					&& !Tools.containPoint(p, getDeadDots())
					&& getLastp().adjacentTo(p, squareSize)
					&& !Tools.containPoint(p, currentP.getSelected())) {
				currentP.getSelected().add(p);
				return true;
			}
		}
		return false;
	}

	public boolean isPlottable(Point p) {
		if (!Tools.containPoint(p, currentP.getPloted())
				&& !Tools.containPoint(p, guest.getPloted())
				&& !Tools.containPoint(p, deadDots)) {
			return true;
		}
		return false;
	}

	public boolean isSelectable(Point p) {
		if (Tools.containPoint(p, currentP.getPloted())
				&& !Tools.containPoint(p, guest.getCapturedDots())
				&& !Tools.containPoint(p, deadDots)) {
			return true;
		}
		return false;
	}

	public boolean checkEndGame() {
		if ((guest.getScore()) >= this.getMaxScore()) {

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
				this.player1.setFadedColor(this.player1.getColor());
				currentP = this.player2;
				guest = this.player1;
			} else {
				this.player2.setFadedColor(this.player2.getColor());
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
	public Player getGuest() {
		return guest;
	}

	/**
	 * This method sets the guest player
	 * 
	 * @param guest
	 *            player
	 */
	public void setGuest(Player guest) {
		Game.guest = guest;
	}

	/**
	 * @return This method returns the current player
	 */
	public Player getCurrentP() {
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

	public ArrayList<Point> getTrueArea(ArrayList<Point> home,
			ArrayList<Point> area) {
		ArrayList<Point> newArea = new ArrayList<Point>();

		// add all point that are not dead or belong to guest to a new Arraylist
		// these point will be part of the a cell.
		for (Point p1 : area) {
			if (Tools.containPoint(p1, deadDots)
					|| Tools.containPoint(p1, home)) {
				continue;
			}
			newArea.add(p1);
		}
		// System.out.println("TrueArea is equal " + newArea.size());
		return newArea;
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
				cell.setValue(cell.getValue() + c.getValue());
				cell.addCellToCell(c);
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
	}

	/**
	 * @param p
	 *            , origin of the path to capture's contour
	 * @return true if p belongs to the path and false if not
	 */
	public static boolean isPath(Point p) {

		if (Tools.containPoint(p, currentP.getPloted())) {
			if (!Tools.containPoint(p, deadDots)
					&& !Tools.containPoint(p, guest.getCapturedDots())) {

				return true;
			}
		}
		return false;
	}

	/**
	 * @return the deadDots
	 */
	public ArrayList<Point> getDeadDots() {
		return deadDots;
	}

	/**
	 * @param deadDots
	 *            the deadDots to set
	 */
	public void setDeadDots(ArrayList<Point> deadDots) {
		Game.deadDots = deadDots;
	}

	/**
	 * @return the player1
	 */
	public Player getPlayer1() {
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
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * @param player2
	 *            the player2 to set
	 */
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	/**
	 * @return the lastp
	 */
	public Point getLastp() {
		return this.lastp;
	}

	/**
	 * @param lastp
	 *            the lastp to set
	 */
	public void setLastp(Point lastp) {
		this.lastp = lastp;
	}

	public void initSelected() {
		this.player1.initSelected();
		this.player2.initSelected();
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
				+ this.embuche_on + "\nLast Point: " + this.lastp
				+ "\nMaximum score: " + this.maxScore;
	}

	/* Connecting dots utilities */

	public boolean embuche_on = false;
	private Cell tempCell = null;

	public Point lastp = new Point(553355, 7798979);
	private int maxScore = 2;

	/* keeps track of all captured points */
	public static ArrayList<Point> deadDots;
	private Player player1;
	private Player player2;
	private static Player currentP = new Player(null, "");
	private static Player guest = new Player(null, "");

}