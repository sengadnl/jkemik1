/**
 * 
 */
package api;

import java.io.Serializable;


/**
 * @author dalet
 * 
 */
public class Point extends AbstractPoint implements Serializable{
	public static int DEAD = 0, PLAYED = 1, CONNECTED = 2,
			CAPTURED = 3, UNKNOWN = -1;
	public Point(double x, double y, int id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}
	public Point(double x, double y) {
		super(x, y, 0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param x
	 * @param y
	 */
	
//	public Point(double x, double y) {
//		super(x, y);
//	}
	public double distanceTo(Point p) {
		return Math.sqrt(Math.abs((super.getXC() - p.getXC())
				* ((super.getXC() - p.getXC()) + Math.abs((super.getYC() - p.getYC())
						* (super.getYC() - p.getYC())))));
	}
	/**
	 * checks if this point is adjacent to the point passed in a parameter*/	
	public int adjacentTo(int offSet, Point o2) {
		int diagonal = (int) Math.sqrt((offSet * offSet) + (offSet * offSet));
		if (super.getXC() == o2.getXC() || super.getYC() == o2.getYC()
				|| diagonal >= distanceTo(o2)) {
			return 0;
		}else{
			return -1;
		}
	}
	
//	public boolean adjacentTo(Point p, int squareSize) {
//		Point box[] = Tools.boxCoord(this, squareSize);
//		for(int i = 0; i < box.length; i++){
//			if(p.compareTo(box[i]) == 0){
//				return true;
//			}
//		}
//		return false;
//	}
	
}
