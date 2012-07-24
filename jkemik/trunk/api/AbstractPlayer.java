package api;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import utilities.Tools;

/**
 * COPYRIGHT(c)2010 Daniel Senga. All Right Reserved. This class is a parent to
 * all Player Objects and their subclasses.
 * @author Daniel Senga Version 0.02010
 * @version 10.0
 */
abstract class AbstractPlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AbstractPlayer(Color color, String name) {
		super();
		this.color = color;
		this.name = name;
		this.score = 0.0;
		this.CapturedCells = new ArrayList<Cell>();
		this.plotted = new ArrayList<Point>();
		this.Cells = new ArrayList<Cell>();
		this.capturedDots = new ArrayList<Point>();
		this.connectedPoints = new ArrayList<Point>();
		this.selected = new ArrayList<Point>();
	}

	/**
	 * Finds a capture by following a path that starts at Point "o" location and
	 * ends at "o" as well. Recursively checks every adjacent Point to find a
	 * valid path. Reverts when a dead end has been reached. a valid capture
	 * must have at least 4 Point Objects.
	 * 
	 * @param o Point where to start
	 * @param squareSize integer length of the sides of a grid square
	 * @return true when a valid capture was found, and false otherwise.
	 * @throws InterruptedException
	 */
	public boolean buildPath(Point o, double squareSize) {
		if (this.successful) {
			return true;
		}
		/* Get all adjacent Points */
		//Point[] box = Tools.boxCoord(o, this.plotted, squareSize);
		Point[] box = Tools.boxCoord(o, squareSize);
		/* Find the point in this box that belongs to the path */
		for (int i = 0; i < box.length; i++) {
			//System.out.println("box[" + i + "]\n");
			/* Stop recursive call here if a path was already found */
			if (this.successful) {
				return true;
			}
			if (Game.isPath(box[i])) {// if this Point is path
				if (box[i].compareTo(this.from) != 0) {// is it == to previous
					if (!Tools.containPoint(o, this.selected)) {
						/* Add o if it hasn't been visited */
						this.selected.add(o);
						this.from = o; /* Move to the next Point */
						if (box[i].compareTo(this.origin) == 0
								&& this.selected.size() > 3) {
							this.successful = true;/* Set recursive call stop */
							this.origin = null;/* Reset the origin */
							System.out.println("A cell was found: \n" + "box[" + i + "]\n");
							return true;/* Capture was found */
						}
						/* This adjacent Point was a dead end */
						if (!buildPath(box[i], squareSize)) {
							this.selected.remove(o);
							continue;
						}
					}
				}
			}
		}
		return false;/* No path */
	}
	
	/**
	 * @return the selected
	 */
	public ArrayList<Point> getSelected() {
		return this.selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(ArrayList<Point> selected) {
		this.selected = selected;
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
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	/** Adds a captured cell to this player */
	public void addCell(Cell c) {

		// Add what this cell is worth to this player'score
		this.Cells.add(c);
		this.score += c.getValue();
		
	}

	/** Adds a captured cell to this player */
	public void removeCell(Cell c) {

		// Add what this cell is worth to this player'score
		this.Cells.remove(c);
		this.score -= c.getValue();
		
	}

	public void addCapturedCells(Cell c) {
		this.CapturedCells.add(c);
	}

	/**
	 * @return the fadedColor
	 */
	public Color getFadedColor() {
		return this.fadedColor;
	}

	/**
	 * @param currentColor
	 *            the fadedColor to set
	 */
	public void setFadedColor(Color currentColor) {
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
		currentColor = new Color(red, green, blue);
		this.fadedColor = currentColor;
	}

	/**
	 * @return the capturedCells
	 */
	public ArrayList<Cell> getCapturedCells() {
		return this.CapturedCells;
	}

	/**
	 * @param capturedCells
	 *            the capturedCells to set
	 */
	public void setCapturedCells(ArrayList<Cell> capturedCells) {
		this.CapturedCells = capturedCells;
	}

	/**
	 * @return the capturedDots
	 */
	public ArrayList<Point> getCapturedDots() {
		return this.capturedDots;
	}

	/**
	 * @param capturedDots
	 *            the capturedDots to set
	 */
	public void setCapturedDots(ArrayList<Point> capturedDots) {
		this.capturedDots = capturedDots;
	}

	/**
	 * @return the connectedPoints
	 */
	public ArrayList<Point> getConnectedPoints() {
		return this.connectedPoints;
	}

	/**
	 * @param connectedPoints
	 *            the connectedPoints to set
	 */
	public void setConnectedPoints(ArrayList<Point> connectedPoints) {
		this.connectedPoints = connectedPoints;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(ArrayList<Cell> cells) {
		this.Cells = cells;
	}

	/**
	 * @return the ploted
	 */
	public ArrayList<Point> getPloted() {
		return this.plotted;
	}

	/**
	 * @param ploted
	 *            the ploted to set
	 */
	public void setPloted(ArrayList<Point> ploted) {
		this.plotted = ploted;
	}

	/**
	 * @return the cells
	 */
	public ArrayList<Cell> getCells() {
		return this.Cells;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */

	public String toString() {
		return "" + this.score + "" + this.plotted + "";
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
	 * @return the origin
	 */
	public Point getOrigin() {
		return this.origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(Point origin) {
		this.origin = origin;
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
		this.color = color;
	}
	/**
	 * @return void
	 * @param none
	 * Initializes selected*/
	public void initSelected(){
		this.selected = new ArrayList<Point>();
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
	private String name = "player";
	private boolean turn = false;
	private double score = 0.0;
	private Color color;
	private Color fadedColor;
	private Point origin = null;
	private Point from = new Point(553355, 7798979);
	private ArrayList<Cell> Cells = null;
	private ArrayList<Cell> CapturedCells = null;
	private ArrayList<Point> capturedDots = null;
	private ArrayList<Point> connectedPoints = null;
	private ArrayList<Point> plotted = null;
	private ArrayList<Point> selected = null;
	private boolean successful = false;
	private int FADE_VARIANT = 70;
	private int play_flag = 0;
	
}
