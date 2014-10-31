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
        protected int heatLevel;


	public AbstractPoint(double x, double y, int id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
		this.status = -1;
                this.heatLevel = 0;
	}

        public int getHeatLevel() {
            return heatLevel;
        }

        public void setHeatLevel(int heatLevel) {
            this.heatLevel = heatLevel;
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
        /**
         @param squareSize
         @return array of all adjacent points*/
        public Point[] box(double squareSize) {
		Point[] box = new Point[8];
		box[0] = new Point(this.getXC() - squareSize, this.getYC() + squareSize);
		box[1] = new Point(this.getXC() + squareSize, this.getYC() - squareSize);
		
		box[2] = new Point(this.getXC() - squareSize, this.getYC());
		box[3] = new Point(this.getXC() + squareSize, this.getYC());
		
		box[4] = new Point(this.getXC() - squareSize, this.getYC() - squareSize);
		box[5] = new Point(this.getXC() + squareSize, this.getYC() + squareSize);
		
		box[6] = new Point(this.getXC(), this.getYC() - squareSize);
		box[7] = new Point(this.getXC(), this.getYC() + squareSize);
		
		return box;
	}
        
        public Point[] diagonalBox(double squareSize) {
		Point[] box = new Point[4];
                double xx;
                xx = this.getXC();
		double yy;
                yy = this.getYC();
		box[1] = new Point(xx + squareSize, yy - squareSize);
                box[0] = new Point(xx - squareSize, yy + squareSize);
		box[3] = new Point(xx + squareSize, yy + squareSize);
                box[2] = new Point(xx - squareSize, yy - squareSize);
		
		return box;
	}
        /**
         @param squareSize
         @return array of all adjacent points*/
        public Point[] axisBox(double squareSize) {
		Point[] box = new Point[4];
		double xx;
                xx = this.getXC();
		double yy;
                yy = this.getYC();

		box[0] = new Point(xx - squareSize, yy);
		box[1] = new Point(xx + squareSize, yy);
		box[2] = new Point(xx, yy - squareSize);
		box[3] = new Point(xx, yy + squareSize);
		
		return box;
	}

        @Override
	public String toString()// for debugging
	{
		return x + ":" + y;
	}

	public double distanceTo(AbstractPoint other) {
		return Point2D.distance(x, y, other.x, other.y);
	}

	public int compareTo(Point p) {
		if(p == null){
			return -1;
		}
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