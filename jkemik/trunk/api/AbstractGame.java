/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

import controler.JKemik;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
		this.play_count = JKemik.settings_t.getMaxPointPerPlayer();
                this.collectionAccessLock = new ReentrantLock();
		this.status = 0;
	}

	public void init() {
		if (this.player1.isTurn()) {
			currentP = player1;
			guest = player2;
		} else {
			currentP = player2;
			guest = player1;
		}
		currentP.setTurn(true);
		this.play_count = JKemik.settings_t.getMaxPointPerPlayer() * 2;
		this.status = 0;
	}

	/**
	 * Finds a capture by following a path that starts at Point "o" location and
	 * ends at Point "o" as well. Recursively checks every adjacent Point to
	 * find a valid path. Reverts when a dead end has been reached. a valid
	 * capture must have at least 4 Point Objects.
	 * 
	 * @param o
	 *            Point where to start
	 * @param squareSize
	 *            integer length of the sides of a grid square
	 * @return true when a valid capture was found, and false otherwise.
	 * @throws InterruptedException
	 */
	public boolean buildPath(Point o, double squareSize) {
            this.collectionAccessLock.lock();
            try{
		if (currentP.getSuccessful()) {
			return true;
		}
		/* Get all adjacent Points */
		//Point[] box = Tools.boxCoord(o, squareSize);
                Point[] box = o.box(squareSize);
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
					if (!currentP.getSelected().contains(o)) {
						/* Add o if it hasn't been visited */
						currentP.getSelected().add(o);
						// System.out.println("Adding " +
						// currentP.getSelected());
						currentP.setFrom(o); /* Move to the next Point */
						if (temp.compareTo(currentP.getOrigin()) == 0
								&& currentP.getSelected().size() > 3) {
							currentP.setSuccessful(true);
							currentP.setOrigin(null);/* Reset the origin */
//							 System.err.println("\nbuildPath cell: "
//							 + currentP.getSelected());
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
            }finally{
                this.collectionAccessLock.unlock();
            }
	}

	/**
	 * Only plotted points can be undone, dead points can not be revived
	 */
	public boolean undo() {

		if (collection.get(currentP.getLatestP().toString()).getStatus() == Globals.POINT_CONNECTED) {
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

	public Cell capture(double squareSize) {
		System.out.println("---------------------------------------------");
		Cell cell = null; /* Cell to be returned */
		ArrayList<Point> lastps = currentP.getLastpoints();
		int start = lastps.size() - 1;
		for (int i = start; i >= 0; i--) {
			cell = null;
			currentP.setSelected(new ArrayList<Point>());
			//System.out.println(i + "- backtracking at " + lastps.get(i));
			currentP.setOrigin(lastps.get(i));/* Reset the origin */
			if (buildPath(lastps.get(i), squareSize)) {
				try {
					ArrayList<Point> TempArea = Tools.getArea(
							currentP.getSelected(), squareSize);

					ArrayList<Point> activePoints = getTrueArea(TempArea);
					ArrayList<Point> area = new ArrayList<>();

					/*Cell is invalid if there are no active points*/
					if (isAreaEmpty(activePoints)) {
						currentP.setSuccessful(false);
						currentP.setSelected(new ArrayList<>());
						continue;
					}

					this.captured_count = 0;
					this.redeemed_count = 0;
					//this.bonus = 0;

					/* Go through all points in this cell */
					for (Point p : TempArea) {
						/* If p exist in collection */
						if (this.collection.containsKey(p.toString())) {
							Point object = this.collection.get(p.toString());

							/* captures */
							if (object.getId() == guest.getId()
									&& object.getStatus() != Globals.POINT_CAPTURED) {
								object.setStatus(Globals.POINT_CAPTURED);
								captured_count += Globals.POINT_VALUE;
							}

							/* Count redeemed points */
							if (object.getId() == currentP.getId()
									&& object.getStatus() == Globals.POINT_CAPTURED) {
								object.setStatus(Globals.POINT_REDEEMED);
								redeemed_count += Globals.REDEEMED_POINT_VALUE;
							}
							/*Copy reference to area*/
							area.add(object);
							
						} else {
							/* If p doesn't exist in collection */
							p.setStatus(Globals.POINT_DEAD);
							this.collection.put(p.toString(), p);
							area.add(p);
						}

					}/* end of second for loop */

					setStatusForAll(currentP.getSelected(), Globals.POINT_CONNECTED);
					cell = new Cell(getCurrentP().getId(), getCurrentP()
							.getSelected(), area);

					if (captured_count == 0) {
						cell.setStatus(Globals.CELL_EMPTY);
						continue;
					}
					cell.setValue(captured_count + redeemed_count);
					cell.setCapturesCount(captured_count);
					cell.setRedeemedCount(redeemed_count);
					//cell.setBonus(bonus);
					cell.setStatus(Globals.CELL_FREE);
					currentP.addCell(cell);
                                        this.setLastCell(cell);//Remember this cell
					evalCell(cell);
					return cell;
				} catch (NullPointerException ex) {
					System.err.println("In capture: " + ex.getMessage());
				}
			}
		}
		currentP.setSuccessful(false);
		currentP.setSelected(new ArrayList<>());
		return cell;
	}

	public Cell capture(int squareSize) {
		Cell cell = null; /* cell to be returned */

		ArrayList<Point> TempArea = Tools.getArea(currentP.getSelected(),
				squareSize);

		ArrayList<Point> activePoints = getTrueArea(TempArea);
		ArrayList<Point> area = new ArrayList<>();

		/*Cell is invalid if there are no active points*/
		if (isAreaEmpty(activePoints)) {
			currentP.setSelected(new ArrayList<>());
			return null;
		}

		this.captured_count = 0;
		this.redeemed_count = 0;
		/* Go through all selected dots from recursion */
		for (Point p : TempArea) {
			/* If p exist in collection */
			if (this.collection.containsKey(p.toString())) {
				Point object = this.collection.get(p.toString());

				/* captures */
				if (object.getId() == guest.getId()
						&& object.getStatus() != Globals.POINT_CAPTURED) {
					object.setStatus(Globals.POINT_CAPTURED);
					captured_count += Globals.POINT_VALUE;
				}

				/* Count redeemed points */
				if (object.getId() == currentP.getId()
						&& object.getStatus() == Globals.POINT_CAPTURED) {
					object.setStatus(Globals.POINT_REDEEMED);
					redeemed_count += Globals.REDEEMED_POINT_VALUE;
				}
				area.add(object);
			} else {
				/* If p doesn't exist in collection */
				p.setStatus(Globals.POINT_DEAD);
				this.collection.put(p.toString(), p);
				area.add(p);
			}
		}/* end of second for loop */

		setStatusForAll(currentP.getSelected(), Globals.POINT_CONNECTED);
		cell = new Cell(getCurrentP().getId(), getCurrentP().getSelected(),
				area);
		if (currentP.getCells().containsKey(cell.hashCode())) {
			return null;
		}

		if (captured_count == 0) {
			cell.setStatus(Globals.CELL_EMPTY);
			return cell;
		}

		cell.setValue(captured_count + redeemed_count);
		cell.setCapturesCount(captured_count);
		cell.setRedeemedCount(redeemed_count);
		cell.setStatus(Globals.CELL_FREE);
		currentP.addCell(cell);
                this.setLastCell(cell);//Remember this cell
		evalCell(cell);
		return cell;
	}

	public Cell connectDots(double squareSize) {
		currentP.setSuccessful(false);
		currentP.setOrigin(currentP.getLatestP());

		Cell tempCell = capture(squareSize);

		if (tempCell != null) {
			return tempCell;
		}
		return null;
	}

	public boolean select(Point p, double squareSize) {

		if (currentP.getSelected().isEmpty() && p.getId() == currentP.getId()
				&& p.getStatus() != Globals.POINT_DEAD) {
			currentP.setOrigin(p);
			currentP.getSelected().add(p);
			this.lastp = p;
			// System.out.println("Selected returned true ");
			return true;
		} else {
			if (p.getId() == currentP.getId() && p.getStatus() != Globals.POINT_DEAD
					&& p.getStatus() != Globals.POINT_CAPTURED
					&& p.getStatus() != Globals.POINT_CAPTURED
					&& getLastp().adjacentTo(p, squareSize)
					&& !currentP.getSelected().contains(p)) {
				currentP.getSelected().add(p);
				// System.out.println("Selected returned true ");
				return true;
			}
		}
		// System.out.println("Selected returned false ");
		return false;
	}

	public ArrayList<Point> getTrueArea(ArrayList<Point> area) {
		ArrayList<Point> newArea = new ArrayList<Point>();

		// add all point that are not dead or belong to guest to a new Arraylist
		// these point will be part of the a cell.

		for (Point p1 : area) {
			Point temp = this.collection.get(p1.toString());
			if (temp == null || temp.getStatus() == Globals.POINT_DEAD
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
			if (p.getStatus() == Globals.POINT_CAPTURED) {
				return false;
			}
			/* Return true if this pt is played or connected */
			if ((p.getStatus() == Globals.POINT_PLAYED)
					|| (p.getStatus() == Globals.POINT_CONNECTED)) {
				return true;
			}
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
			// play_count--;
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
	 * @return the number of captured cells by the current player
	 */
	public void evalCell(Cell cell) {
		/* Look at all guest cells */
		for (Cell c : guest.getCells().values()) {

			/* Pick one point on each guest cell */
			Point p = c.getCellContour().get(0);

			/* Get the associated point in the board */
			Point temp = this.collection.get(p.toString());

			/*
			 * If the point is captured, and its associated cell is neither
			 * captured nor is it redeemed, a new captured cell is found.
			 */
			if (temp.getStatus() == Globals.POINT_CAPTURED
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

				/*
				 * Losing a cell will also subtract its value to the owner's
				 * score
				 */
				guest.removeCell(c);
			}
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
			if (p.getStatus() != Globals.POINT_CAPTURED) {
				p.setStatus(status);
			}
		}
	}

	public String toString() {
		return "GAME\n-------------" + this.player1 + "\nVS\n" + this.player2
				+ "-----------------------------------------"
				+ "\nCurrent Player: " + currentP.getName() + " Color: "
				+ currentP.getColor() + "\nGuest Player: " + guest.getName()
				+ " Color: " + guest.getColor() + "\nEmbush: "
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
            collectionAccessLock.lock();
            try{
		return this.collection;
            }finally{
                collectionAccessLock.unlock();
            }
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


	public int getCaptured_count() {
		return captured_count;
	}

	public void setCaptured_count(int captured_count) {
		this.captured_count = captured_count;
	}

	public int getRedeemed_count() {
		return redeemed_count;
	}

	public void setRedeemed_count(int redeemed_count) {
		this.redeemed_count = redeemed_count;
	}

	public int getPlay_count() {
		return play_count;
	}

	public void setPlay_count(int play_count) {
		this.play_count = play_count;
	}

        public Cell getLastCell() {
            return lastCell;
        }

        public void setLastCell(Cell lastCell) {
            this.lastCell = lastCell;
        }
        

	/* Connecting dots utilities */
	private boolean AI = false;
	private boolean embuche_on = false;
	// private Cell tempCell = null;
	private int status = 0;
	private int maxScore = 2;
	private int captured_count = 0;
	private int redeemed_count = 0;
	private int play_count = 0;
	private Point lastp = new Point(553355, 7798979);

	/* keeps track of all captured points */
	private AbstractPlayer player1;
	private AbstractPlayer player2;
	private static AbstractPlayer currentP;
	private static AbstractPlayer guest;
	private HashMap<String, Point> collection = new HashMap<String, Point>();
        private Lock collectionAccessLock;

	/* AI tools */
        private Cell lastCell;

}