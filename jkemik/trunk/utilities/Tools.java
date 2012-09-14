/**
 * 
 */
package utilities;

import java.awt.Color;
import java.util.ArrayList;
import api.Point;
import api.STemplate;

/**
 * @author dalet
 * 
 */
public class Tools {
	public static boolean isMaxWinLessThanGrid(int gridSize, int maxWin) {
		if (maxWin > gridSize) {
			return false;
		}
		return true;
	}

	public static void resetMaxWin(int GridSqrCount, STemplate t) {
		int defaultMaxWin = GridSqrCount/2;
		t.setMaxWinVal(defaultMaxWin);
	}

	/**
	 * fades color
	 */
	public static Color selected(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		if (r == 0) {
			r = r + 0;
		}
		if (g == 0) {
			g = g + 0;
		}
		if (b == 0) {
			b = b + 200;
		}
		return new Color(r, g, b);
	}

	/**
	 * fades color
	 */
	public static Color deSelected(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		if (r == 0) {
			r = r + 0;
		}
		if (g == 0) {
			g = g + 0;
		}
		if (b > 0) {
			b = b - 200;
		}
		return new Color(r, g, b);
	}

	/**
	 * fades color
	 */
	public static Color fade(Color c) {
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();
		if (r > Globals.FADE_THRESHOLD) {
			r = r - (r * Globals.FADE_VARIANT);
		}
		if (g > Globals.FADE_THRESHOLD) {
			g = g - (g * Globals.FADE_VARIANT);
		}
		if (b > Globals.FADE_THRESHOLD) {
			b = b - (b * Globals.FADE_VARIANT);
		}
		// System.out.println("Faded: \n" + "\nr = " + (int) r + "\ng = "
		// + (int) g + "\nb = " + (int)b);
		return new Color((int) r, (int) g, (int) b);
	}

	/**
	 * boosts color
	 */
	public static Color boost(Color c) {
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();
		int red = (int) r, green = (int) g, blue = (int) b;
		try {
			r = r / (1 - Globals.FADE_VARIANT);
			g = g / (1 - Globals.FADE_VARIANT);
			b = b / (1 - Globals.FADE_VARIANT);
			if (r < 0 || r > 255) {
				r = red;
			}
			if (g < 0 || g > 255) {
				g = green;
			}
			if (b < 0 || b > 255) {
				b = blue;
			}

		} catch (Exception e) {
			System.err.println("In Tools.boost: " + e.getMessage());
		}
		return new Color((int) r, (int) g, (int) b);
	}

	public static double freeGridInPercent(double occupied, double gridSize) {
		return (((gridSize - occupied) * 100) / gridSize);
	}

	public static double deadGridInPercent(double occupied, double gridSize) {
		return ((occupied * 100) / gridSize);
	}

	/**
	 * This function collects all the point inscribed in a cell. Due to
	 * unpredictable shaping of captures, the found collection of points might
	 * contain points that don't belong to the area. These points are mostly
	 * located in region of the capture with external concavity.
	 * 
	 * @param 1.ArrayList of point representing a capture 2.integer size of one
	 *        the sides of the square cells which JKemik grid is made of
	 * @return none
	 */
	public static ArrayList<Point> getArea(ArrayList<Point> list,
			double squareSize) {

		/* contain point of the area inside a cell */
		ArrayList<Point> area = new ArrayList<Point>();

		/* contain points that have already been visited */
		ArrayList<Point> tracker = new ArrayList<Point>();

		/* x coordinate of the current point */
		int xCurrent = 0;

		/* farthest x coordinate of the same y as the current point */
		int xFound = 0;

		/* the point at xFound */
		Point found = null;

		for (Point current : list) {
			if (!containPoint(current, tracker)) {
				/* Find the farthest point at y = y of current point */
				found = scanX(list, current);
			} else {
				continue;
			}

			/* Remember that we already looked at this point */
			tracker.add(current);
			xCurrent = (int) current.getXC();
			xFound = (int) found.getXC();

			/* look at all the points between xCurrent and xfound */

			while (xFound >= xCurrent) {
				Point newPoint = new Point((double) (xCurrent + squareSize),
						current.getYC());
				if (xFound == xCurrent) {
					tracker.add(newPoint);
					break;
				}
				if (!containPoint(newPoint, area)
						&& !containPoint(newPoint, list)
						&& isInPolygon(list, newPoint)) {
					area.add(newPoint);// in the future we will simply be
					// counting instead of storing the point
					xCurrent = xCurrent + (int) squareSize;

				} else {
					break;
				}
			}
			/* look to the left of xCurrent */

			while (xFound <= xCurrent) {
				Point newPoint = new Point((double) (xCurrent - squareSize),
						current.getYC());
				if (xFound == xCurrent) {
					tracker.add(newPoint);
					break;
				}
				if (!containPoint(newPoint, area)
						&& !containPoint(newPoint, list)
						&& isInPolygon(list, newPoint)) {
					area.add(newPoint);
					xCurrent = xCurrent - (int) squareSize;
				} else {
					break;
				}
			}
		}

		return area;
	}

	/**
	 * This function determines whether a point belongs to a polygon or not.
	 * It's an algorithm known as the Point-in-polygon algorithm.
	 */
	public static boolean isInPolygon(ArrayList<Point> list, Point p) {

		int j = list.size() - 1;// start from the end of the list
		boolean odd = false;// return value is there is an odd number of node on
		// both sides of p
		double y = p.getYC(); // reference y coordinate
		double x = p.getXC(); // reference x coordinate

		for (int i = 0; i < list.size(); i++) {
			Point p1 = (Point) list.get(i); // get points from beginning of list
			Point p2 = (Point) list.get(j); // get points from end of the list
			if (p1.getYC() < y && p2.getYC() >= y || p2.getYC() < y
					&& p1.getYC() >= y) {
				if (p1.getXC() + (y - p1.getYC()) / (p2.getYC() - p1.getYC())
						* (p2.getXC() - p1.getXC()) < x) {
					odd = !odd;
				}
			}
			j = i;
		}
		return odd;
	}


	/**
	 * this function filters the Area
	 */
	public static boolean isInArea(ArrayList<Point> list, Point p) {
		if (hasXMax(list, p) && hasXMin(list, p) && hasYMax(list, p)
				&& hasYMin(list, p)) {
			System.out.println("Found a good one...");
			return true;
		}
		return false;
	}

	public static boolean hasXMax(ArrayList<Point> list, Point p) {
		boolean ret = false;
		for (Point point : list) {
			if (point.getXC() > p.getXC() && point.getYC() == p.getYC()) {
				System.out.println("X max true...");
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static boolean hasXMin(ArrayList<Point> list, Point p) {
		boolean ret = false;
		for (Point point : list) {
			if (point.getXC() < p.getXC() && point.getYC() == p.getYC()) {
				System.out.println("X min true...");
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static boolean hasYMax(ArrayList<Point> list, Point p) {
		boolean ret = false;
		for (Point point : list) {
			if (point.getYC() > p.getYC() && point.getXC() == p.getXC()) {
				System.out.println("y max true...");
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static boolean hasYMin(ArrayList<Point> list, Point p) {
		boolean ret = false;
		for (Point point : list) {
			if (point.getYC() < p.getYC() && point.getXC() == p.getXC()) {
				System.out.println("y min true...");
				ret = true;
				break;
			}
		}
		return ret;
	}

	/** scan the x axis for another point */
	public static Point scanX(ArrayList<Point> list, Point p) {

		Point ret = p;
		for (Point point : list) {
			if (point.compareTo(p) != 0 && point.getYC() == p.getYC()) {
				ret = point;
			}
		}
		return ret;
	}

	/** scan the y axis for another point */
	public static Point scanY(ArrayList<Point> list, Point p) {
		Point ret = new Point(0, 0);
		for (Point point : list) {
			if (point.compareTo(p) != 0 && point.getXC() == p.getXC()) {
				ret = point;
			}
		}
		return ret;
	}

	/**
	 * @author dalet
	 * @param a
	 *            Point object, an arrayList of points, and the length of one
	 *            side of squares that make the chemik's grid
	 * @return an array of points forming the box which contains the point
	 *         parameter
	 */
	public static Point[] boxCoord(Point p, ArrayList<Point> list,
			double squareSize) {
		ArrayList<Point> b = new ArrayList<Point>();
		Point[] box = new Point[9];
		// Point[] box = new Point[8];

		double x = p.getXC();
		double y = p.getYC();

		box[0] = new Point(x - squareSize, y + squareSize);
		if (containPoint(box[0], list)) {
			b.add(box[0]);
		}
		box[1] = new Point(x - squareSize, y);
		if (containPoint(box[1], list)) {
			b.add(box[1]);
		}
		box[2] = new Point(x - squareSize, y - squareSize);
		if (containPoint(box[2], list)) {
			b.add(box[2]);
		}

		box[3] = new Point(x, y + squareSize);
		if (containPoint(box[3], list)) {
			b.add(box[3]);
		}
		box[4] = new Point(x, y - squareSize);
		if (containPoint(box[4], list)) {
			b.add(box[4]);
		}

		box[5] = new Point(x + squareSize, y + squareSize);
		if (containPoint(box[5], list)) {
			b.add(box[5]);
		}
		box[6] = new Point(x + squareSize, y);
		if (containPoint(box[6], list)) {
			b.add(box[6]);
		}

		box[7] = new Point(x + squareSize, y - squareSize);
		if (containPoint(box[7], list)) {
			b.add(box[7]);
		}

		box[8] = new Point(x, y);
		if (containPoint(box[8], list)) {
			b.add(box[8]);
		}

		box = null;
		box = b.toArray(new Point[b.size()]);
		return box;
	}

	public static boolean containPoint(Point p, ArrayList<Point> a) {
		try {
			if (a.isEmpty()) {
				return false;
			}

			for (Point point : a) {
				if (p.compareTo(point) == 0) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Tool:containPoint: " + e.getMessage());
		}
		return false;
	}

	public static Point[] boxCoord(Point p, double squareSize) {
		Point[] box = new Point[9];
		double x = p.getXC();
		double y = p.getYC();

		box[0] = new Point(x - squareSize, y + squareSize);
		box[1] = new Point(x - squareSize, y);
		box[2] = new Point(x - squareSize, y - squareSize);

		box[3] = new Point(x, y + squareSize);
		box[4] = new Point(x, y - squareSize);

		box[5] = new Point(x + squareSize, y + squareSize);
		box[6] = new Point(x + squareSize, y);
		box[7] = new Point(x + squareSize, y - squareSize);
		box[8] = p;

		return box;
	}

	/**
	 * @author dalet
	 * @param an
	 *            ArrayList of points, integer representing the y coordinate of
	 *            one of the most left or most right point that shares this y
	 *            coordinate
	 * @return an integer array containing x coordinates of the left and right
	 *         bounding points
	 */
	public static int[] findMinMaxX(ArrayList<Point> selected, int yMin) {
		int xMin = (int) selected.get(selected.size() - 1).getXC();
		int xMax = (int) selected.get(selected.size() - 1).getXC();
		for (int j = 0; j < selected.size() - 1; j++) {
			if ((int) (selected.get(j).getYC()) == yMin) {
				int cur = (int) selected.get(j).getXC();
				if (xMin >= cur) {
					xMin = cur;
				}
				if (xMax < cur) {
					xMax = cur;
				}
			}
		}
		int[] minMax = { xMin, xMax };
		return minMax;
	}

	/**
	 * @author dalet
	 * @param an
	 *            ArrayList of points, integer representing the x coordinate of
	 *            one of the lower or upper point that shares this x coordinate
	 * @return an integer array containing y coordinates of the lower and upper
	 *         bounding points
	 */
	public static int[] findMinMaxY(ArrayList<Point> selected, int xCur) {
		int yMin = (int) selected.get(0).getYC(), yMax = (int) selected.get(0)
				.getYC();
		for (int j = 0; j < selected.size() - 1; j++) {
			if ((int) (selected.get(j).getXC()) == xCur) {
				int cur = (int) selected.get(j).getYC();
				if (yMin >= cur) {
					yMin = cur;
				}
				if (yMax < cur) {
					yMax = cur;
				}
			}
		}
		int[] minMay = { yMin, yMax };
		return minMay;
	}

	public static boolean isIntoArrayList(ArrayList<Point> list, Point point) {
		for (Point p : list) {
			if (point.compareTo(p) == 0) {
				return true;
			}
		}
		// System.err.println("isIntoArrayList success");
		return false;
	}

	public static boolean isIntoArray(Point[] array, Point p) {
		for (Point a : array) {
			if (p.compareTo(a) == 0) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Point> quickSort(ArrayList<Point> selectPoints) {
		if (selectPoints.size() <= 1) {
			return selectPoints;
		}
		int pivot = selectPoints.size() / 2;
		ArrayList<Point> lesser = new ArrayList<Point>();
		ArrayList<Point> greater = new ArrayList<Point>();
		int sameAsPivot = 0;
		for (Point p : selectPoints) {
			if (p.getXC() > selectPoints.get(pivot).getXC())
				greater.add(p);
			else if (p.getXC() < selectPoints.get(pivot).getXC())
				lesser.add(p);
			else
				sameAsPivot++;
		}
		lesser = quickSort(lesser);
		for (int i = 0; i < sameAsPivot; i++)
			lesser.add(selectPoints.get(pivot));
		greater = quickSort(greater);
		ArrayList<Point> sorted = new ArrayList<Point>();
		for (Point p : lesser) {
			sorted.add(p);
		}
		for (Point p : greater) {
			sorted.add(p);
		}
		return sorted;
	}
}