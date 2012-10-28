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
abstract class AbstractPoint implements Comparable<Point>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;
	
	protected boolean flag_dead;

	public AbstractPoint(double x, double y)// constructor
	{
		this.x = x;
		this.y = y;
		this.flag_dead = false;
	}
	public boolean isFlag_dead() {
		return flag_dead;
	}

	public void setFlag_dead(boolean flagDead) {
		flag_dead = flagDead;
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
		Line2D.Double l = new Line2D.Double((int) x, (int) y, (int) other
				.getXC(), (int) other.getYC());
		g.draw(l);
		g.setColor(new Color(153, 102, 0));
	}

	public String toString()// for debugging
	{
		return "(" + x + "," + y + ")";
	}

	public double distanceTo(AbstractPoint other) 
	{
		return Point2D.distance(x, y, other.x, other.y);
	}

	public int compareTo(Point p) {
		if (x == p.getXC() && y == p.getYC()) {
			return 0;
		}
		return -1;
	}
	/**
	 * @param a point object and the grid square size
	 * @return true is the distance between this point and the other is equal to 
	 * the square size or if it is equal to the Hypotenuse */
	public boolean adjacentTo(AbstractPoint other, double squareSize) {
	 double distance = this.distanceTo(other); 
	 double hypothenus = Math.hypot(squareSize, squareSize);
	 if(distance == squareSize || distance == hypothenus){
		 return true;
	 }
	 return false;
	}
}