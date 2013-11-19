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
	 * Only plotted points can be undone, dead points can not be revived
	 */
	public boolean undo() {
		if (collection.get(currentP.getLatestP()).getStatus() == Point.CONNECTED) {
			return false;
		}
		if (collection.remove(currentP.getLatestP()) != null) {// remove last
																// point
			unSetPlayFlag();
			return true;
		}
		return false;
	}

	public Cell capture(Point o, double squareSize) {
		Cell cell = null; /* cell to be returned */

		if (currentP.buildPath(o, squareSize)) {

			ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
					squareSize);

			ArrayList<Point> area = getTrueArea(TempArea);

			if (isAreaEmpty(area)) {
				currentP.setSelected(new ArrayList<Point>());
				return null;
			}

			/* Go through all selected dots from recursion */
			for (Point p : area) {
				if (p.getId() == guest.getId()
						&& p.getStatus() != Point.CAPTURED) {

					/* keep track of captures for this cell */
					p.setStatus(3);

				} else if (p.getStatus() != Point.DEAD
						&& p.getStatus() != Point.PLAYED) {//
					// getDeadDots().add(p);//
					p.setStatus(Point.DEAD);
					this.collection.put(p.toString(), p);

				}/* end first if else */
			}/* end of second for loop */
			setStatusForAll(currentP.getSelected(), Point.CONNECTED);
			cell = new Cell(getCurrentP().getId(), getCurrentP().getSelected(),
					area, null);// Engine.getGame().

			calculateScore(cell);

		} else {
			currentP.setSuccessful(false);
			currentP.setSelected(new ArrayList<Point>());
		}

		return cell;
	}

	public Cell capture(int squareSize) {
		Cell cell = null; /* cell to be returned */

		ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
				squareSize);

		ArrayList<Point> area = getTrueArea(TempArea);

		if (isAreaEmpty(area)) {
			currentP.setSelected(new ArrayList<Point>());
			return null;
		}

		/* Go through all selected dots from recursion */
		for (Point p : area) {
			if (p.getId() == guest.getId() && p.getStatus() != Point.CAPTURED) {

				/* keep track of captures for this cell */
				p.setStatus(3);

			} else if (p.getStatus() != Point.DEAD
					&& p.getStatus() != Point.PLAYED) {//
				// getDeadDots().add(p);//
				p.setStatus(Point.DEAD);
				this.collection.put(p.toString(), p);

			}/* end first if else */
		}/* end of second for loop */
		setStatusForAll(currentP.getSelected(), Point.CONNECTED);
		cell = new Cell(getCurrentP().getId(), getCurrentP().getSelected(),
				area, null);// Engine.getGame().

		calculateScore(cell);
		return cell;
	}

	/**
	 * @return collection of objects that corresponding to the refs set in grid
	 */
	// private ArrayList<Point> getReferences(ArrayList<Point> refs) {
	// ArrayList<Point> set = new ArrayList<Point>();
	// for (Point p : refs) {
	// set.add(collection.get(p.toString()));
	// }
	// return set;
	// }

	// TODO
	public Cell connectDots(double squareSize) {
		currentP.setSuccessful(false);
		currentP.setOrigin(currentP.getLatestP());

		Cell tempCell = capture(currentP.getLatestP(), squareSize);
		if (tempCell != null) {
			System.out.println("Cell was not NULL");
			return tempCell;
		}
		return null;

	}

	public boolean select(Point p, double squareSize) {

		if (currentP.getSelected().isEmpty() && p.getId() == currentP.getId()
				&& p.getStatus() != Point.DEAD) {
			currentP.setOrigin(p);
			currentP.getSelected().add(p);
			this.lastp = p;
			System.out.println("Selected returned true ");
			return true;
		} else {
			if (p.getId() == currentP.getId() && p.getStatus() != Point.DEAD
					&& getLastp().adjacentTo(p, squareSize)
					&& !Tools.containPoint(p, currentP.getSelected())) {
				currentP.getSelected().add(p);
				System.out.println("Selected returned true ");
				return true;
			}
		}
		System.out.println("Selected returned false ");
		return false;
	}

	public ArrayList<Point> getTrueArea(ArrayList<Point> area) {
		ArrayList<Point> newArea = new ArrayList<Point>();

		// add all point that are not dead or belong to guest to a new Arraylist
		// these point will be part of the a cell.
		// TODO
		for (Point p1 : area) {
			if (p1.getStatus() == 4 || p1.getId() == currentP.getId()) {
				continue;
			}
			newArea.add(p1);
		}
		// System.out.println("TrueArea is equal " + newArea.size());
		return newArea;
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

	public boolean isAreaEmpty(ArrayList<Point> trueArea) {
		try {
			for (Point pArea : trueArea) {
				if (collection.containsKey(pArea.toString())) {
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
			if (p.getStatus() == Point.CAPTURED) {
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

	// TODO
	private void setStatusForAll(ArrayList<Point> points, int status) {
		for (Point p : points) {
			p.setStatus(status);
		}
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

	public Point getLastp() {
		return lastp;
	}

	public void setLastp(Point lastp) {
		this.lastp = lastp;
	}

	/* Connecting dots utilities */
	public boolean AI = false;
	public boolean embuche_on = false;
	// private Cell tempCell = null;
	private int status = 0;
	private int maxScore = 2;
	public Point lastp = new Point(553355, 7798979);

	/* keeps track of all captured points */
	private AbstractPlayer player1;
	private AbstractPlayer player2;
	private static AbstractPlayer currentP = new Player(null, "");
	private static AbstractPlayer guest = new Player(null, "");
	private HashMap<String, Point> collection = new HashMap<String, Point>();

}