package api;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.Globals;

/**
 * COPYRIGHT(c)2010 Daniel Senga. All Right Reserved. This class is a parent to
 * all Player Objects and their subclasses.
 * 
 * @author Daniel Senga Version 0.02010
 * @version 10.0
 */
public abstract class AbstractPlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractPlayer(Color color, String name) {
		super();
		this.color = color;
		this.name = name;
		this.score = 0.0;
		// this.CapturedCells = new HashMap<Integer, Cell>();
		this.selected = new ArrayList<Point>();
		this.Cells = new HashMap<Integer, Cell>();
	}

	public int countRedeemedPoints() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			/*Look through this player's cells*/
			for (Cell c1 : this.Cells.values()) {
				/*Find any captured cells*/
				for (Cell c2 : c1.getCellsInCell().values()) {
					/*Make sure we are looking into a captured cell*/
					if (c2.getStatus() == Globals.CELL_CAPTURED) {
						/*Count any redeemed points*/
						for (Point p : c2.getAreaIncell()) {
							if (p.getId() == this.getId()
									&& p.getStatus() == Point.REDEEMED) {
								count++;
							}
						}
					}
				}
			}
		}
		return count;
	}

	public int countCapturedPoints() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			for (Cell c1 : this.Cells.values()) {
				if (c1.getStatus() == Globals.CELL_FREE) {
					for (Point p : c1.getAreaIncell()) {
						if (p.getStatus() == Point.CAPTURED) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	public int countFreeCells() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			for (Cell c1 : this.Cells.values()) {
				if (c1.getStatus() == Globals.CELL_FREE) {
					count++;
				}
			}
		}
		return count;
	}

	public int countCapturedCells() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			for (Cell c1 : this.Cells.values()) {
				for (Cell c2 : c1.getCellsInCell().values()) {
					if (c2.getStatus() == Globals.CELL_CAPTURED) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * @return the successful
	 */
	public boolean isSuccessful() {
		return this.successful;
	}

	/**
	 * @param successful
	 *            the successful to set
	 */
	public boolean getSuccessful() {
		return this.successful;
	}

	/**
	 * @param successful
	 *            the successful to set
	 */
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	/** Adds a captured cell to this player */
	public void addCell(Cell c) {

		// Add what this cell is worth to this player'score

		if (c != null && c.getStatus() == Globals.CELL_FREE) {
			this.score += c.getValue();
			this.Cells.put(c.hashCode(), c);
		}
	}

	/** Adds a captured cell to this player */
	public void removeCell(Cell c) {

		// Add what this cell is worth to this player'score
		this.Cells.remove(c);
		this.score -= c.getValue() * 2;
	}

	// public void addCapturedCells(Cell c) {
	// this.CapturedCells.put(c.getPIN(), c);
	// }

	/**
	 * @return the fadedColor
	 */
	public Color getFadedColor() {
		int red = this.color.getRed(), green = this.color.getGreen(), blue = this.color
				.getBlue();

		if (red > this.FADE_VARIANT) {
			red = Math.abs(red - this.FADE_VARIANT);
		}

		if (blue > this.FADE_VARIANT) {
			blue = Math.abs(blue - this.FADE_VARIANT);
		}

		if (green > this.FADE_VARIANT) {
			green = Math.abs(green - this.FADE_VARIANT);
		}
		return new Color(red, green, blue);
	}

	/**
	 * @return the capturedCells
	 */
	// public HashMap<Integer, Cell> getCapturedCells() {
	// return this.CapturedCells;
	// }

	/**
	 * @param capturedCells
	 *            the capturedCells to set
	 */
	// public void setCapturedCells(HashMap<Integer, Cell> capturedCells) {
	// this.CapturedCells = capturedCells;
	// }

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(HashMap<Integer, Cell> cells) {
		this.Cells = cells;
	}

	/**
	 * @return the cells
	 */
	public HashMap<Integer, Cell> getCells() {
		return this.Cells;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return " " + this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		System.out.println("setting color" + color);
		this.color = color;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getPlay_flag() {
		return play_flag;
	}

	public void setPlay_flag(int playFlag) {
		play_flag = playFlag;
	}

	public int compareTo(AbstractPlayer o) {
		if (this.getColor().equals(o.getColor())) {
			return 0;
		}
		return 1;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */

	public String toString() {
		return "\nID: " + this.id + "\nName: " + this.name + "\nColor: "
				+ this.color.toString() + "\nFaded Color: "
				+ this.getFadedColor() + "\nScore: " + this.score
				+ "\nMy turn: " + this.turn + "\nCells: " + this.Cells
				+ "\nCapturedCells: " + this.countCapturedCells()
				+ "\nPlay Flag: " + this.play_flag + "\nCapture successfull: "
				+ this.successful + "\nFade variant: " + this.FADE_VARIANT
				+ "\n";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Point> getSelected() {
		return selected;
	}

	public void setSelected(ArrayList<Point> selected) {
		this.selected = selected;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Point getFrom() {
		return from;
	}

	public void setFrom(Point from) {
		this.from = from;
	}

	public Point getLatestP() {
		return latestP;
	}

	public void setLatestP(Point latestP) {
		this.latestP = latestP;
	}

	// public int getCapture_count() {
	// return capture_count;
	// }
	//
	// public int getRedeemed_count() {
	// return redeemed_count;
	// }
	//
	// public int getCaptured_cell_count() {
	// return captured_cell_count;
	// }
	public Cell getLastCapture() {
		return lastCapture;
	}

	public void setLastCapture(Cell lastCapture) {
		this.lastCapture = lastCapture;
	}//dsenga8n26f8 - jarme3000
	
	public void rememberPoint(Point p) {
		if (this.lastpoints.size() < 1) {
			this.lastpoints.add(p);
		} else {
			this.lastpoints.remove(0);
			this.lastpoints.add(p);
		}
	}
	
	public ArrayList<Point> getLastpoints() {
		return lastpoints;
	}

	public void setLastpoints(ArrayList<Point> lastpoints) {
		this.lastpoints = lastpoints;
	}

	private String name = "player";
	private int id = 0;// player1 = -1 and player2 = 1
	private boolean turn = false;
	private boolean ai = false;
	private double score = 0.0;
	// private int capture_count = 0;
	// private int redeemed_count = 0;
	// private int captured_cell_count = 0;
	private Color color;

	private Point origin = new Point(444444, 7798979);
	private Point from = new Point(553355, 7798979);
	private Point latestP = new Point(550055, 7798979);
	
	public ArrayList<Point> lastpoints = new ArrayList<Point>();

	private HashMap<Integer, Cell> Cells = null;
	private Cell lastCapture = null;
	// TODO
	// private HashMap<Integer, Cell> CapturedCells = null;
	private ArrayList<Point> selected = null;

	private boolean successful = false;
	private int FADE_VARIANT = 70;
	private int play_flag = 0;
}
