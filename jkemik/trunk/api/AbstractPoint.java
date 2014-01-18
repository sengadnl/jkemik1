/**
 * 
 */
package api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * @author Junes
 * 
 */
abstract class AbstractPoint implements Comparable<Point>, Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;

	/* id of the player this point belongs to */
	protected int id;

	/*
	 * Current status of this point -1 = unknown, 1 = played, 2 = connected, 3 =
	 * captured, 0 = dead
	 */
	protected int status;
	protected boolean north;
	protected boolean south;
	protected boolean east;
	protected boolean west;
	protected boolean north_east;
	protected boolean north_west;
	protected boolean south_east;
	protected boolean south_west;

	public AbstractPoint(double x, double y, int id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
		this.status = 0;
		this.north = false;
		this.south = false;
		this.east = false;
		this.west = false;
		this.north_west = false;
		this.south_east = false;
		this.south_west = false;
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

	/**
	 * @param int status: 0 = unknown, 1 = plotted, 2 = connected, 3 = captured,
	 *        4 = dead
	 * @return void
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isNorth() {
		return north;
	}

	public void setNorth(boolean north) {
		this.north = north;
	}

	public boolean isSouth() {
		return south;
	}

	public void setSouth(boolean south) {
		this.south = south;
	}

	public boolean isEast() {
		return east;
	}

	public void setEast(boolean east) {
		this.east = east;
	}

	public boolean isWest() {
		return west;
	}

	public void setWest(boolean west) {
		this.west = west;
	}

	public boolean isNorth_east() {
		return north_east;
	}

	public void setNorth_east(boolean north_east) {
		this.north_east = north_east;
	}

	public boolean isNorth_west() {
		return north_west;
	}

	public void setNorth_west(boolean north_west) {
		this.north_west = north_west;
	}

	public boolean isSouth_east() {
		return south_east;
	}

	public void setSouth_east(boolean south_east) {
		this.south_east = south_east;
	}

	public boolean isSouth_west() {
		return south_west;
	}

	public void setSouth_west(boolean south_west) {
		this.south_west = south_west;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the x
	 */
	public double getXC() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setXC(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getYC() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setYC(double y) {
		this.y = y;
	}

	public void drawTo(AbstractPoint other, Graphics2D g) {
		Line2D.Double l = new Line2D.Double((int) x, (int) y,
				(int) other.getXC(), (int) other.getYC());
		g.draw(l);
		g.setColor(new Color(153, 102, 0));
	}

	public String toString()// for debugging
	{
		return x + ":" + y;
	}

	public double distanceTo(AbstractPoint other) {
		return Point2D.distance(x, y, other.x, other.y);
	}

	public int compareTo(Point p) {
		if (x == p.getXC() && y == p.getYC()) {
			return 0;
		}
		return -1;
	}

	/**
	 * @param a
	 *            point object and the grid square size
	 * @return true is the distance between this point and the other is equal to
	 *         the square size or if it is equal to the Hypotenuse
	 */
	public boolean adjacentTo(AbstractPoint other, double squareSize) {
		double distance = this.distanceTo(other);
		double hypothenus = Math.hypot(squareSize, squareSize);
		if (distance == squareSize || distance == hypothenus) {
			return true;
		}
		return false;
	}
}