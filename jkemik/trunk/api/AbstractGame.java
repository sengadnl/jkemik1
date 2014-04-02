/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

import utilities.Globals;
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
	 * Finds a capture by following a path that starts at Point "o" location and
	 * ends at "o" as well. Recursively checks every adjacent Point to find a
	 * valid path. Reverts when a dead end has been reached. a valid capture
	 * must have at least 4 Point Objects.
	 * 
	 * @param o
	 *            Point where to start
	 * @param squareSize
	 *            integer length of the sides of a grid square
	 * @return true when a valid capture was found, and false otherwise.
	 * @throws InterruptedException
	 */
	public boolean buildPath(Point o, double squareSize) {
		if (currentP.getSuccessful()) {
			return true;
		}
		/* Get all adjacent Points */
		Point[] box = Tools.boxCoord(o, squareSize);
		/* Find the point in this box that belongs to the path */
		for (int i = 0; i < box.length; i++) {
			/* Stop recursive call here if a path was already found */
			if (currentP.getSuccessful()) {
				return true;
			}
			Point temp = collection.get(box[i].toString());
			if (temp == null) {
				continue;
			}

			if (AbstractGame.isPath(temp)) {// if this Point is path
				if (temp.compareTo(currentP.getFrom()) != 0) {
					if (!Tools.containPoint(o, currentP.getSelected())) {
						/* Add o if it hasn't been visited */
						currentP.getSelected().add(o);
						currentP.setFrom(o); /* Move to the next Point */
						if (temp.compareTo(currentP.getOrigin()) == 0
								&& currentP.getSelected().size() > 3) {
							currentP.setSuccessful(true);/*
														 * Set recursive call
														 * stop
														 */
							currentP.setOrigin(null);/* Reset the origin */
							System.out.println("\nFound cell...");
							return true;/* Capture was found */
						}
						/* This adjacent Point was a dead end */
						if (!buildPath(temp, squareSize)) {
							currentP.getSelected().remove(o);
							continue;
						}
					}
				}
			}
		}
		return false;/* No path */
	}

	/**
	 * Only plotted points can be undone, dead points can not be revived
	 */
	public boolean undo() {

		if (collection.get(currentP.getLatestP().toString()).getStatus() == Point.CONNECTED) {
			System.out.println("First if is true");
			return false;
		}

		if (collection.remove(currentP.getLatestP().toString()) != null) {// remove
																			// last
			System.out.println("Second if is true"); // point
			unSetPlayFlag();
			return true;
		}

		return false;
	}

	public Cell capture(Point o, double squareSize) {
		Cell cell = null; /* Cell to be returned */

		if (buildPath(o, squareSize)) {
			try {
				ArrayList<Point> TempArea = Tools.getArea(
						currentP.getSelected(), squareSize);

				ArrayList<Point> area = getTrueArea(TempArea);

				if (isAreaEmpty(area)) {
					currentP.setSuccessful(false);
					currentP.setSelected(new ArrayList<Point>());
					return null;
				}

				int captured_count = 0;
				int redeemed_count = 0;
				/* Go through all selected dots from recursion */
				for (Point p : TempArea) {

					/* If p exist in collection */
					if (this.collection.containsKey(p.toString())) {
						Point object = this.collection.get(p.toString());

						/* captures */
						if (object.getId() == guest.getId()
								&& object.getStatus() != Point.CAPTURED) {
							object.setStatus(Point.CAPTURED);
							captured_count += Globals.POINT_VALUE;
						}

						/* Count redeemed points */
						if (object.getId() == currentP.getId()
								&& object.getStatus() == Point.CAPTURED) {
							object.setStatus(Point.REDEEMED);
							redeemed_count += Globals.REDEEMED_POINT_VALUE;
						}

					} else {
						/* If p doesn't exist in collection */
						p.setStatus(Point.DEAD);
						this.collection.put(p.toString(), p);
					}

				}/* end of second for loop */

				setStatusForAll(currentP.getSelected(), Point.CONNECTED);
				cell = new Cell(getCurrentP().getId(), getCurrentP()
						.getSelected(), area);

				if (captured_count == 0) {
					return null;
				}
				cell.setValue(captured_count + redeemed_count);

				cell.setStatus(Globals.CELL_FREE);
				currentP.addCell(cell);
				calculateScore(cell);
			} catch (NullPointerException ex) {
				System.err.println("In capture: " + ex.getMessage());
			}
		}
		currentP.setSuccessful(false);
		currentP.setSelected(new ArrayList<Point>());

		return cell;
	}

	public Cell capture(int squareSize) {
		// Cell cell = null; /* cell to be returned */

		ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
				squareSize);

		ArrayList<Point> area = getTrueArea(TempArea);

		if (isAreaEmpty(area)) {
			currentP.setSelected(new ArrayList<Point>());
			return null;
		}

		int captured_count = 0;
		int redeemed_count = 0;
		/* Go through all selected dots from recursion */
		for (Point p : area) {
			/* If p exist in collection */
			if (this.collection.containsKey(p.toString())) {
				Point object = this.collection.get(p.toString());

				/* captures */
				if (object.getId() == guest.getId()
						&& object.getStatus() != Point.CAPTURED) {
					object.setStatus(Point.CAPTURED);
					captured_count += Globals.POINT_VALUE;
				}

				/* Count redeemed points */
				if (object.getId() == currentP.getId()
						&& object.getStatus() == Point.CAPTURED) {
					object.setStatus(Point.REDEEMED);
					redeemed_count += Globals.REDEEMED_POINT_VALUE;
				}

			} else {
				/* If p doesn't exist in collection */
				p.setStatus(Point.DEAD);
				this.collection.put(p.toString(), p);
			}
		}/* end of second for loop */

		setStatusForAll(currentP.getSelected(), Point.CONNECTED);
		Cell cell = new Cell(getCurrentP().getId(),
				getCurrentP().getSelected(), area);
		if (currentP.getCells().containsKey(cell.hashCode())) {
			return null;
		}

		cell.setValue(captured_count + redeemed_count);
		cell.setStatus(Globals.CELL_FREE);
		currentP.addCell(cell);

		// currentP.refreshRedeemed_count(redeemed_count);
		calculateScore(cell);
		return cell;
	}

	public Cell connectDots(double squareSize) {
		currentP.setSuccessful(false);
		currentP.setOrigin(currentP.getLatestP());

		Cell tempCell = capture(currentP.getLatestP(), squareSize);
		// Tools.printCollectionPointsStatus(this.collection);

		if (tempCell != null) {
			// System.out.println("Found cell");
			return tempCell;
		}
		// TODO
		// else {
		// /* Persist to bypass unwanted empty cells */
		// if (!this.box.isEmpty()) {
		// System.out.println("Persisting!!!!");
		// /* check the other point in the box */
		// for (int e = this.box.size() - 1; e >= 1; e--) {
		// Point temp = this.collection
		// .get(this.box.get(e).toString());
		// if (temp == null) {
		// continue;
		// }
		// tempCell = capture(temp, squareSize);
		// if (tempCell == null) {
		// continue;
		// } else {
		// return tempCell;
		// }
		// }
		// this.box = new ArrayList<Point>();
		// }
		// }
		return null;
	}

	public boolean select(Point p, double squareSize) {

		if (currentP.getSelected().isEmpty() && p.getId() == currentP.getId()
				&& p.getStatus() != Point.DEAD) {
			currentP.setOrigin(p);
			currentP.getSelected().add(p);
			this.lastp = p;
			// System.out.println("Selected returned true ");
			return true;
		} else {
			if (p.getId() == currentP.getId() && p.getStatus() != Point.DEAD
					&& p.getStatus() != Point.CAPTURED
					&& p.getStatus() != Point.REDEEMED
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

		for (Point p1 : area) {
			Point temp = this.collection.get(p1.toString());
			if (temp == null || temp.getStatus() == Point.DEAD
					|| temp.getId() == currentP.getId()) {
				continue;
			}
			newArea.add(temp);
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

		/* if the point belong to the current player */
		if (((p.getId() != guest.getId()))) {

			/* Wrong way, if the pt is captured */
			if (p.getStatus() == Point.CAPTURED) {
				return false;
			}
			/* Return true if this pt is played or connected */
			if ((p.getStatus() == Point.PLAYED)
					|| (p.getStatus() == Point.CONNECTED)) {
				return true;
			}
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
			currentP.setSelected(new ArrayList<Point>());
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
		// check for capture
		for (Cell c : guest.getCells().values()) {

			Point p = c.getCellContour().get(0);
			Point temp = this.collection.get(p.toString());
			if (temp.getStatus() == Point.CAPTURED
					&& c.getStatus() != Globals.CELL_CAPTURED
					&& c.getStatus() != Globals.CELL_REDEEMED) {
				cell.setValue(cell.getValue() + c.getValue());
				c.setStatus(Globals.CELL_CAPTURED);
				// Redeem cells
				if (!c.getCellsInCell().isEmpty()) {
					for (Cell r : c.getCellsInCell().values()) {
						r.setStatus(Globals.CELL_REDEEMED);
					}
				}
				// add captured cell
				cell.addCell(c);
				/* Losing a cell is equivalent to losing twice its value */
				guest.removeCell(c);
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

		evalCell(c);

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

	private void setStatusForAll(ArrayList<Point> points, int status) {
		for (Point p : points) {
			if (p.getStatus() != Point.CAPTURED) {
				p.setStatus(status);
			}
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

	// TODO implement persistent capture
	public ArrayList<Point> getBox() {
		return box;
	}

	public void setBox(ArrayList<Point> persistanceList) {
		this.box = persistanceList;
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

	/* persistance */
	// TODO implement persistent capture
	public ArrayList<Point> box = new ArrayList<Point>();

}