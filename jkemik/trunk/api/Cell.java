/**
 * 
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.Globals;


/**
 * @author dalet
 * 
 */
public class Cell implements Comparable<Cell>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cell(int id, ArrayList<Point> cellContour, ArrayList<Point> areaIncell) {
		super();
		this.id = id;
		this.cellContour = cellContour;
		this.areaIncell = areaIncell;
		this.status = Globals.CELL_FREE;
		this.capturedcell_Count = 0;
		this.capturesCount = 0;
		this.cellsInCell = new HashMap<Integer, Cell>();
	}
	public String toString() {
		if(this.status == Globals.CELL_FREE){
			return "ID:" + id + " - Countour:" + cellContour.size() 
					+ " - Status:FREE" + " - Value:" + value;
		}else if(this.status == Globals.CELL_CAPTURED){
			return "ID:" + id + " - Countour:" + cellContour.size() 
					+ " - Status:CAPTURED" + " - Value:" + value;
		}else if(this.status == Globals.CELL_REDEEMED){
			return "ID:" + id + " - Countour:" + cellContour.size() 
					+ " - Status:CAPTURED" + " - Value:" + value;
		}else{
			return "ID:" + id + " - Countour:" + cellContour.size() 
					+ " - Status:EMPTY" + " - Value:" + value;
		}
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
	 * Number of points making the contour of the cell, plus the number of empty
	 * intersections in the cell
	 */
	public int getCellSize() {
		return this.areaIncell.size();
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
	public void addCell(Cell c) {
		try {
			this.cellsInCell.put(c.hashCode(),c);
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

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCapturesCount() {
		return capturesCount;
	}
	public void setCapturesCount(int capturesCount) {
		this.capturesCount = capturesCount;
	}

	public int getRedeemedCount() {
		return redeemedCount;
	}
	
	public void setRedeemedCount(int redeemedCount) {
		this.redeemedCount = redeemedCount;
	}
	
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	private ArrayList<Point> cellContour;
	private ArrayList<Point> areaIncell;
	private HashMap<Integer, Cell> cellsInCell;
	private int capturedcell_Count;
	private int capturesCount;
	private int redeemedCount;
	private int bonus;
	private double value = 0.0;
	private int id = 0;
	private int status = 0;
	
}
