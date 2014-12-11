/**
 * 
 */
package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import api.Point;
import api.STemplate;
import api.Wall;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author dalet
 * 
 */
public class Tools {
    /*Trancate wall at x and return the tracated part*/
    public static Wall trancate(Wall w, Point x){
        Wall wall;
        Iterator i;
        LinkedList<Point> list = w.getList();
        i = list.iterator();
        
        wall = new Wall(w.getSqrSize());
        try{
            /*Move iterator after x*/
            while(i.hasNext()){
                Point temp = (Point) i.next();
                if(temp.compareTo(x) == 0){
                    //System.out.println("Cursor is at " + i.next());
                    break;
                }
            }
             
            /*Add trancated elements to another list*/
            while(i.hasNext()){
                wall.add((Point) i.next());
            }
            /*Trancate the list*/
            list.removeAll(wall.getList());
        }catch(NoSuchElementException ex){
            System.out.println("Trancate " + ex.getMessage());
        }
         /*Return the trancated list*/
         return wall;
    }
	public static String languageValue(String key) {
		for (int i = 0; i < Globals.code.length; i++) {
			if (key.equals(Globals.code[i])) {
				return Globals.laguageNames[i];
			}
		}
		return "";
	}

	public static void printCollectionPointsStatus(
			HashMap<String, Point> collection) {
		for (Point p : collection.values()) {
			System.out.println("[" + p.toString() + "]" + " Status: "
					+ p.getStatus() + " ID: " + p.getId());
		}
	}

	// TODO may not use this
	/**
	 * @param gcontainer
	 *            : board container dimension in quare;
	 * @return Square size
	 * 
	 * */
	public static double calculateSquareSize(Dimension gcontainer, int sqrMin) {
		double w = gcontainer.getWidth();
		double h = gcontainer.getHeight();

		int counter = sqrMin;// initialize counter

		while (counter <= w) {

			if (w % counter == 0 && h % counter == 0) {
				break;
			}
			counter++;
		}
		return counter;
	}

	/**
	 * Board size in terms of number of squares
	 **/
	public static Dimension bSize(Dimension gcontainer, int sqr) {
		double cw = gcontainer.getWidth();
		double ch = gcontainer.getHeight();
		int w = (int) cw / sqr;
		int h = (int) ch / sqr;
		System.out.println("" + w + " X " + h + "----"
				+ calculateSquareSize(gcontainer, sqr));

		return new Dimension(w, h);
	}

	/**
	 * @param framew
	 *            : Frame width frameh : Frame height grid_percent : Size of the
	 *            board container in percentage on the Frame 0 < grid_percent <
	 *            1
	 * @return ArrayList<Dimension> of board dimensions.
	 */
	public static ArrayList<Dimension> boardSizeCalculator(double framew,
			double frameh, double grid_percent) {
		ArrayList<Dimension> boards = new ArrayList<Dimension>();

		// calculate board container dimension (pixels)
		int w_ini = (int) (framew * grid_percent);
		int h_ini = (int) (frameh * grid_percent);
		Dimension gcontainer = new Dimension(w_ini, h_ini);
		boards.add(gcontainer);
		System.out.println("" + gcontainer);

		// Get list of square sizes
		ArrayList<Integer> sqrSizes = sqrtSizeGCD(new Dimension(w_ini, h_ini),
				Globals.SQUARE_MIN_SIZE);

		// Generate board list of board dimensions based on square sizes
		int index = 0;
		for (int temp : sqrSizes) {
			// Skip board container dimensions
			if (index == 0) {
				index++;
				continue;
			}
			System.out.print("SQR Size:" + temp + " -> ");
			boards.add(bSize(gcontainer, temp));
		}
		System.out.println("Possible board sizes: " + boards);
		// possible grid sizes (sqrs)
		return boards;
	}

	/**
	 * @param gcontainer
	 *            board container dimension found in the first position of the
	 *            ArrayList returned in boardSizeCalculator sqrtMin
	 */
	public static ArrayList<Integer> sqrtSizeGCD(Dimension gcontainer,
			int sqrMin) {
		ArrayList<Integer> sqrSizes = new ArrayList<Integer>();

		double w = gcontainer.getWidth();
		double h = gcontainer.getHeight();

		int counter = sqrMin;// initialize counter

		while (counter <= w) {

			if (w % counter == 0 && h % counter == 0) {
				sqrSizes.add(counter);
			}
			counter++;
		}
		return sqrSizes;
	}

	public static String propertiesFilename(String key) {
		if (key.equals("fr")) {
			return "resrc.MessagesBundle_fr_FR";
		} else if (key.equals("en")) {
			return "resrc.MessagesBundle_en_US";
		} else if (key.equals("de")) {
			return "resrc.MessagesBundle_de_DE";
		} else {
			return "resrc.MessagesBundle";
		}
	}

	public static String languageKey(String value) {
		for (int i = 0; i < Globals.code.length; i++) {
			if (value.equals(Globals.laguageNames[i])) {
				return Globals.code[i];
			}
		}
		return "";
	}

	public static String fullPath() {
		String path = "";
		try {
			File f = new java.io.File(".");
			path = f.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}



	public static void resetMaxWin(int positionsCount, STemplate t) {
		int defaultMaxWin = (int) (positionsCount * Globals.MAX_WIN_PERCENT_BOARD);
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

		return new Color((int) r, (int) g, (int) b);
	}

	/**
	 * fades color
	 * @param f_th fade threshold: no color goes below this value
	 * f_var scaler percent variable between 0 and 1.
	 */
	public static Color fade(Color c, int f_th, double f_var) {
		
		if(f_var > 1 || f_var < 0){
			throw new NumberFormatException();
		}
			
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();
		if (r > f_th) {
			r = r - (r * f_var);
		}
		if (g > f_th) {
			g = g - (g * f_var);
		}
		if (b > f_th) {
			b = b - (b * f_var);
		}

		return new Color((int) r, (int) g, (int) b);
	}

	/**
	 * @param Color
	 *            and the percentage to which it should faded. This percentage
	 *            is a whole number
	 * @return faded color
	 */
	public static Color fade(Color c, double percent) {
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();
		try {
			r = r - (r * (percent / 100));
			g = g - (g * (percent / 100));
			b = b - (b * (percent / 100));
			if (r < 0) {
				r = 0;
			}
			if (g < 0) {
				g = 0;
			}
			if (b < 0) {
				b = 0;
			}


		} catch (Exception e) {
			System.err.println("In Tools.boost: " + e.getMessage());
		}
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

	public static Color boost(Color c, double percent) {
		
		/*Get individual colors*/
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();

		try {
			
			r = r + (r * (percent / 100));
			g = g + (g * (percent / 100));
			b = b + (b * (percent / 100));
			if (r > 255) {
				r = 255;
			}
			if (g > 255) {
				g = 255;
			}
			if (b > 255) {
				b = 255;
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
			//if (!containPoint(current, tracker)) {
                        if (!tracker.contains(current)) {    
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
				if (!area.contains(newPoint)
						&& !list.contains(newPoint)
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
				if (!area.contains(newPoint)
						&& !list.contains(newPoint)
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

//	public static boolean containPoint(Point p, ArrayList<Point> a) {
//		try {
//			if (a.isEmpty()) {
//				return false;
//			}
//
//			for (Point point : a) {
//				if (p.compareTo(point) == 0) {
//					return true;
//				}
//			}
//		} catch (NullPointerException e) {
//			System.out.println("Tool:containPoint: " + e.getMessage());
//		}
//		return false;
//	}



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
		ArrayList<Point> lesser = new ArrayList<>();
		ArrayList<Point> greater = new ArrayList<>();
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
		ArrayList<Point> sorted = new ArrayList<>();
		for (Point p : lesser) {
			sorted.add(p);
		}
		for (Point p : greater) {
			sorted.add(p);
		}
		return sorted;
	}
}