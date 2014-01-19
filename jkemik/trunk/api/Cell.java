/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.Tools;

/**
 * @author dalet
 * 
 */
public class Cell implements Comparable<Cell>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cell(int id, ArrayList<Point> cellContour, ArrayList<Point> areaIncell,
			HashMap<Integer, Cell> cellsInCell) {
		super();
		this.id = id;
		this.cellContour = cellContour;
		this.areaIncell = areaIncell;
		//this.capturedPoints = capturedPoints;
		this.capturedcell_Count = 0;
		this.cellsInCell = cellsInCell;
		this.pin = Tools.pinGenerator(areaIncell);
	}
	public String toString() {
		return "" + this.cellContour;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the capturedcell_Count
	 */
	public int getCapturedcell_Count() {
		return this.capturedcell_Count;
	}

	/**
	 * @param capturedcellCount
	 *            the capturedcell_Count to set
	 */
	public void setCapturedcell_Count(int capturedcellCount) {
		this.capturedcell_Count = capturedcellCount;
	}

	public int getNumberPrisonner() {
		//return this.capturedPoints.size();
		return 0;
	}

	/**
	 * Checks if the point passed in as a parameter is in this cell or not.
	 * 
	 * @param a
	 *            point object
	 * @return true or false depending on whether the point object passed in was
	 *         found or not
	 */
	public boolean isInCell(Point point) {
		boolean ret = false;
		for (Point p : this.cellContour) {
			if (point.compareTo(p) == 0) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @return the capturedPoints
	 */
//	public ArrayList<Point> getCapturedPoints() {
//		if (this.capturedPoints == null) {
//			throw new NullPointerException();
//		}
//		return this.capturedPoints;
//	}

	/**
	 * @param capturedPoints
	 *            the capturedPoints to set
	 */
//	public void setCapturedPoints(ArrayList<Point> capturedPoints) {
//		this.capturedPoints = capturedPoints;
//	}

	/**
	 * Number of points making the contour of the cell, plus the number of empty
	 * intersections in the cell
	 */
	public int getCellSize() {
		// return cellContour.size();
		return this.areaIncell.size();
	}

	/**
	 * @return the areaIncell
	 */
	public ArrayList<Point> getAreaInCell() {
		return this.areaIncell;
	}

	/**
	 * @param areaIncell
	 *            the areaIncell to set
	 */
	public void setAreaInCell(ArrayList<Point> areaIncell) {
		this.areaIncell = areaIncell;
	}

	/**
	 * @return the cell
	 */
	public ArrayList<Point> getCellContour() {
		if (this.cellContour == null) {
			throw new NullPointerException();
		}
		return this.cellContour;
	}

	/**
	 * @param cell
	 *            the cell to set
	 */
	public void setCellContour(ArrayList<Point> cell) {
		this.cellContour = cell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Cell c) {
		if (this.equals(c)) {
			return 0;
		}
		return 1;
	}

	/**
	 * @return the areaIncell
	 */
	public ArrayList<Point> getAreaIncell() {
		return this.areaIncell;
	}

	/**
	 * @param areaIncell
	 *            the areaIncell to set
	 */
	public void setAreaIncell(ArrayList<Point> areaIncell) {
		this.areaIncell = areaIncell;
	}

	/**
	 * Add the value of the inscribed cell to this cell
	 * 
	 * @param Cell
	 * @return void
	 */
	public void addCellToCell(Cell c) {
		try {
			this.cellsInCell.put(c.getPIN(),c);
			this.value = this.value + c.getValue();
		} catch (Exception e) {
			System.out.println("in Cell:addCellToCell " + e.getMessage());
		}
	}

	/**
	 * @return the cellsInCell
	 */
	public HashMap<Integer, Cell> getCellsInCell() {
		return this.cellsInCell;
	}

	/**
	 * @param cellsInCell
	 *            the cellsInCell to set
	 */
	public void setCellsInCell(HashMap<Integer, Cell> cellsInCell) {
		this.cellsInCell = cellsInCell;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	public int getPIN() {
		return pin;
	}
	public void setPIN(int pin) {
		this.pin = pin;
	}


	private ArrayList<Point> cellContour;
	private ArrayList<Point> areaIncell;
	private HashMap<Integer, Cell> cellsInCell;
	private int capturedcell_Count;
	private double value = 0.0;
	private int id = 0;
	private int pin = 0;
}
