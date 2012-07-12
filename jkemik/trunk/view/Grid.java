/**
 * 
 */
package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;

import controler.JKemik;
import utilities.Tools;
import api.Cell;
import api.Game;
import api.Player;
import api.Point;

/**
 * @author Dalet
 * 
 */
public class Grid extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Graphics2D g2;
	private static Color gridLineCol = new Color(10,60,30);
	private static int gridLineStroke = 2;
	private static int CURSOR_VARIANT_STROKE = 6;
	public static double CIRCLE_DIAMETER = 10.0;
	public static double HALF_DIAMETER = 5.0;
	public static double squareSize = 64;
	public static double half_squareSize = 32;
	public boolean mouseclicked = false;
	public static boolean debug = false;
	public static boolean mouseMove = false;
	public static boolean undo = false;
	public static boolean saveSettings = false;
	public static boolean ON = false;
	public boolean drawn = false;

	private Ellipse2D.Double circle;
	private static Color pcolor = new Color(255, 255, 255);
	private static Color ccolor = new Color(255, 255, 255);// Cursor color

	public static double Height = 640;
	public static double Width = 1024;

	public static double Columns = 0.0, rows = 0.0;
	public static double x = 0, y = 0, hl_x = 0, hl_y = 0;
	public static int position_count = 0;

	public Grid(int squareSize) {
		setPreferredSize(new Dimension((int) Width, (int) Height));
		setSquareSize(squareSize);
		calColAndRows((int) squareSize);
	}

	public static int squareCount() {
		return (int) ((Grid.Width / Grid.squareSize) * (Grid.Height / Grid.squareSize));
	}

	public static void calColAndRows(int squareSize) {
		try {
			Grid.Columns = Grid.Width / squareSize;
			Grid.rows = Grid.Height / squareSize;
		} catch (Exception e) {
			System.out.println("Grid: calColumns: " + e.getMessage());
		}
	}

	public Point makeDrawable(double x, double y) {
		return new Point(Grid.x - HALF_DIAMETER, Grid.y - HALF_DIAMETER);
	}

	public Point makeDrawable(Point p) {
		return new Point(p.getXC() - HALF_DIAMETER, p.getYC() - HALF_DIAMETER);
	}

	public Point undoMakeDrawable(Point drawable) {
		return new Point(drawable.getXC() + HALF_DIAMETER, drawable.getYC()
				+ HALF_DIAMETER);
	}

	public void paintComponent(Graphics g) {
		Grid.g2 = (Graphics2D) g;
		//super.paintComponent(g2);
		try {
			drawCursor(hl_x, hl_y, gridLineCol);

			/* Find the closest point to the cursor */
			closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
			Point temp = makeDrawable(Grid.x, Grid.y);
			BoardFrame.print_point.setText(""
					+ (new Point(hl_x, hl_y)).toString());
			highLightDot(ccolor);
			Game game = JKemik.game;
			if (this.mouseclicked && game.getCurrentP().isTurn()) {
				if (!Tools.containPoint(temp, game.getCurrentP().getPloted())
						&& !Tools.containPoint(temp, game.getGuest()
								.getPloted())
						&& !Tools.containPoint(temp, game.getDeadDots())) {

					drawCircle(temp, game.getCurrentP().getColor());
					game.getCurrentP().getPloted().add(temp);
					game.setEmbuche_on(true);
					game.setPlayFlag();
					game.getCurrentP().setTurn(false);

					BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
					BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
					mouseMove = false;
				}
				this.mouseclicked = false;
			}

			if (undo) {
				if (game.undo()) {
					unDraw(game.getLastp());
					BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
					BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
				}
				undo = false;
			}
			if (game.isEmbuche_on()) {
				embush();
			}
			
			if (JKemik.settings_t.isAutoPass()
					&& game.getCurrentP().getPlay_flag() == 1) {
				game.switchPlayTurns();
				Grid.setCcolor(game.getCurrentP().getColor());
			}
			
			if(Grid.debug){
				drawGame(game);
				Grid.debug = false;
			}
		} catch (Exception e) {
			System.out.println("Error in paint: " + e.getMessage());
		}

		if (!this.drawn) {
			drawGrid();
			this.drawn = true;
		}

	}

	public void highLightDot(Color c) {
		Game game = JKemik.game;

		if (mouseMove) {
			g2.setColor(c);
			Point temp = makeDrawable(x, y);
			if (Tools.containPoint(temp, game.getCurrentP().getPloted())
					|| Tools.containPoint(temp, game.getGuest().getPloted())
					|| Tools.containPoint(temp, game.getDeadDots())) {

			} else {
				drawCursor(x, y);
			}
			hl_x = x;
			hl_y = y;
			mouseMove = false;
		}
	}

	public boolean connectDots() {

		Game game = JKemik.game;
		game.getCurrentP().setSuccessful(false);

		ArrayList<Point> currentPPoints = game.getCurrentP().getPloted();

		int start = currentPPoints.size() - 1;
		for (int i = start; i >= 0; i--) {
			Point currentPP = currentPPoints.get(i);
			game.getCurrentP().setOrigin(currentPP);
			try {

				Cell cell = game.capture(currentPP, squareSize);
				if (cell != null) {
					System.out.println("Cell was not NULL");
					if (drawCell(cell)) {
						game.getCurrentP().setSelected(new ArrayList<Point>());
						//i = -1;

						return true;
					}
				} else {
					continue;
				}
			} catch (IndexOutOfBoundsException e) {
				game.getCurrentP().setSelected(new ArrayList<Point>());
				System.out.println("In connectDots(): Area out of bounds"
						+ e.getMessage());
				continue;
			} catch (NullPointerException e) {
				System.out.println("In connectDots(): " + e.getMessage());
				continue;
			}
		}
		return false;
	}

	public void embush() {
		Game game = JKemik.game;
		if (JKemik.settings_t.isAutoCapture()) {
			try {
				if (connectDots()) {
					BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
					BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());

				} else {
					game.setEmbuche_on(false);
				}
			} catch (Exception e) {
				System.out.println("Error in PaintComponent: capture "
						+ e.getMessage());
			}
		} else {
			if (JKemik.settings_t.isManualCapture()) {
				try {
					if (connectDots()) {
						BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
						BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());

						game.setEmbuche_on(false);
					} else {
						game.setEmbuche_on(false);
					}
				} catch (Exception e) {
					System.out.println("Error in PaintComponent: capture "
							+ e.getMessage());
				}
				JKemik.settings_t.setManualCapture(false);

			}
		}
	}

	private boolean drawCell(Cell cell) {
		Game game = JKemik.game;
		try {
			ArrayList<Point> contour = cell.getCellContour();
			ArrayList<Point> captured = cell.getCapturedPoints();
			ArrayList<Point> area = cell.getAreaIncell();
			if (area.isEmpty()) {
				return false;
			}

			game.getCurrentP().getConnectedPoints().addAll(contour);
			/* set color */
			g2.setColor(game.getCurrentP().getColor());
			g2.setStroke(new BasicStroke(gridLineStroke
					+ CURSOR_VARIANT_STROKE));

			/* draw cell contour */
			drawLine(contour.get(0), contour.get(contour.size() - 1));
			for (int i = 0; i < contour.size() - 1; i++) {
				drawLine(contour.get(i), contour.get(i + 1));

				Point p1 = undoMakeDrawable(contour.get(i));
				Point p2 = undoMakeDrawable(contour.get(i + 1));

				// draw intersection
				drawCursor(p1, gridLineCol);
				drawCursor(p2, gridLineCol);
				g2.setColor(game.getCurrentP().getColor());
				g2.setStroke(new BasicStroke(gridLineStroke
						+ CURSOR_VARIANT_STROKE));
			}

			/* Mark empty dots */
			ArrayList<Point> free_dots = new ArrayList<Point>();

			free_dots.addAll(area);
			free_dots.removeAll(captured);

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}
	/**
	 * @param Arraylist of pl1 cells, pl1, pl2
	 * @return void
	 * Draws a cell with all its content.
 	* */
	private boolean drawCell(ArrayList<Cell> cells, Player pl1, Player pl2) {

		try {
			for (Cell c : cells) {
				ArrayList<Point> contour = c.getCellContour();
				
				/* set color */
				g2.setColor(pl1.getColor());
				g2.setStroke(new BasicStroke(gridLineStroke
						+ CURSOR_VARIANT_STROKE));

				/* draw cell contour */
				drawLine(contour.get(0), contour.get(contour.size() - 1));
				for (int i = 0; i < contour.size() - 1; i++) {
					drawLine(contour.get(i), contour.get(i + 1));

					Point p1 = undoMakeDrawable(contour.get(i));
					Point p2 = undoMakeDrawable(contour.get(i + 1));

					// draw intersection
					drawCursor(p1, gridLineCol);
					drawCursor(p2, gridLineCol);
					g2.setColor(pl1.getColor());
					g2.setStroke(new BasicStroke(gridLineStroke
							+ CURSOR_VARIANT_STROKE));
				}
				if (drawCell(c.getCellsInCell(), pl2, pl1)) {}
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	private void drawGame(Game g) {
		ArrayList<Cell> p1c = g.getPlayer1().getCells();
		ArrayList<Cell> p2c = g.getPlayer2().getCells();
		Player p1 = g.getPlayer1();
		Player p2 = g.getPlayer2();
		// draw p1 points
		for (Point p : p1.getPloted()) {drawCircle(p, p1.getColor());}
		// draw p1 points
		for (Point p : p2.getPloted()) {drawCircle(p, p2.getColor());}
		// draw p1 cells
		if (drawCell(p1c, p1, p2)) {}
		// draw p1 cells
		if (drawCell(p2c, p2, p1)) {}
	}

	private void drawLine(Point from, Point to) {
		g2.drawLine((int) (from.getXC() + HALF_DIAMETER),
				(int) (from.getYC() + HALF_DIAMETER),
				(int) (to.getXC() + HALF_DIAMETER),
				(int) (to.getYC() + HALF_DIAMETER));

	}

	public void switchTurn() {
		Game game = JKemik.game;
		if (game.getCurrentP().compareTo(game.getPlayer1()) == 0) {
			BoardFrame.p2panel.setLabelColor(game.getPlayer2().getColor());
			BoardFrame.p1panel.setLabelColor(game.getPlayer1().getFadedColor());
		} else {
			BoardFrame.p1panel.setLabelColor(game.getPlayer1().getColor());
			BoardFrame.p2panel.setLabelColor(game.getPlayer2().getFadedColor());
		}
	}

	private void unDraw(Point p) {

		try {
			double px = p.getXC();
			double py = p.getYC();
			this.circle = new Ellipse2D.Double(px, py, Grid.CIRCLE_DIAMETER,
					Grid.CIRCLE_DIAMETER);
			g2.setColor(BoardFrame.BOARD_COLOR);
			g2.fill(this.circle);
			g2.draw(this.circle);
			g2.setColor(gridLineCol);

			px = px + HALF_DIAMETER;
			py = py + HALF_DIAMETER;

			g2.draw(new Line2D.Double(px, py + half_squareSize, px, py
					- half_squareSize));
			g2.draw(new Line2D.Double(px - half_squareSize, py, px
					+ half_squareSize, py));
		} catch (Exception e) {
			System.err.println("Error in unDraw: " + e.getMessage());
		}
	}

	public void drawCursor(double x, double y) {

		g2.draw(new Line2D.Double(x, y + half_squareSize, x, y
				- half_squareSize));
		g2.draw(new Line2D.Double(x - half_squareSize, y, x + half_squareSize,
				y));

	}

	public void drawCursor(double x, double y, Color c) {

		g2.setColor(c);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(x, y + half_squareSize, x, y
				- half_squareSize));
		g2.draw(new Line2D.Double(x - half_squareSize, y, x + half_squareSize,
				y));
		g2.setColor(pcolor);

	}

	public void drawCursor(Point p, Color c) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + half_squareSize, p
				.getXC(), p.getYC() - half_squareSize));
		g2.draw(new Line2D.Double(p.getXC() - half_squareSize, p.getYC(), p
				.getXC()
				+ half_squareSize, p.getYC()));
		g2.setColor(pcolor);
	}

	public void drawCursor(Point p, Color c, Color set_back) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + half_squareSize, p
				.getXC(), p.getYC() - half_squareSize));
		g2.draw(new Line2D.Double(p.getXC() - half_squareSize, p.getYC(), p
				.getXC()
				+ half_squareSize, p.getYC()));
		g2.setColor(set_back);
	}

	/**
	 * @return the pcolor
	 */
	public static Color getPcolor() {
		return pcolor;
	}

	/**
	 * @param pcolor
	 *            the pcolor to set
	 */
	public static void setPcolor(Color pcolor) {
		Grid.pcolor = pcolor;
	}

	/**
	 * @return the squareSize
	 */
	public static double getSquareSize() {
		return squareSize;
	}

	/**
	 * @param squareSize
	 *            the squareSize to set
	 */
	public static void setSquareSize(double squareSize) {
		Grid.squareSize = squareSize;
		Grid.half_squareSize = squareSize / 2;
		if (Grid.squareSize == 16.0) {
			CIRCLE_DIAMETER = 12;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 2;
		}

		if (Grid.squareSize == 32.0) {
			CIRCLE_DIAMETER = 18;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 4;
		}

		if (Grid.squareSize == 64.0) {
			CIRCLE_DIAMETER = 24;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 6;
		}

		if (Grid.squareSize == 128.0) {
			CIRCLE_DIAMETER = 32;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 8;
		}
	}

	public static void drawGrid() {
		calColAndRows((int) Grid.squareSize);
		g2.setColor(Grid.gridLineCol);
		g2.setStroke(new BasicStroke(gridLineStroke));
		int currentposition = 0;
		int index = 0;
		while (index < Columns + 1) {
			// draw columns
			if (currentposition <= Width) {
				g2.draw(new Line2D.Double(squareSize * index, 0,
						Grid.squareSize * index, Grid.Height));
				position_count++;
			}
			// draw rows
			if (currentposition <= Grid.Height) {
				g2.draw(new Line2D.Double(0, Grid.squareSize * index,
						Grid.Width, Grid.squareSize * index));
				position_count++;
			}
			currentposition += Grid.squareSize;
			index++;
		}
	}

	/**
	 * @return the poistions_count
	 */
	public static int getPoistions_count() {
		return position_count;
	}

	/**
	 * @param poisitions_count
	 *            the poisitions_count to set
	 */
	public static void setPoistions_count(int poistions_count) {
		Grid.position_count = poistions_count;
	}

	/**
	 * @return the ccolor
	 */
	public static Color getCcolor() {
		return ccolor;
	}

	/**
	 * @param ccolor
	 *            the ccolor to set
	 */
	public static void setCcolor(Color ccolor) {
		Grid.ccolor = ccolor;
	}

	/**
	 * @param str
	 * @return
	 */
	public static double getSqrSize(String str) {
		double sqrSize = 32;
		if (str.equals("64x40")) {
			sqrSize = 16.0;
		}

		if (str.equals("32x20")) {
			sqrSize = 32.0;
		}

		if (str.equals("16x10")) {
			sqrSize = 64.0;
		}

		if (str.equals("8x5")) {
			sqrSize = 128.0;
		}
		return sqrSize;
	}

	public void closestTo(double xcoor, double ycoor, int square) {
		int deltax = 0, deltay = 0;
		int xc = 0, yc = 0;
		deltax = (int) (xcoor % square);
		xc = square - deltax;

		deltay = (int) (ycoor % square);
		yc = square - deltay;

		if (deltax > xc) {
			x = xcoor + xc;
		} else {
			x = xcoor - deltax;
		}

		if (deltay > yc) {
			y = ycoor + yc;
		} else {
			y = ycoor - deltay;
		}
	}

	/**
	 * @return the mouse clicked
	 */
	public boolean isMouseclicked() {
		return this.mouseclicked;
	}

	/**
	 * @param mouseclicked
	 *            the mouse clicked to set
	 */
	public void setMouseclicked(boolean mouseclicked) {
		this.mouseclicked = mouseclicked;
	}

	public void drawCircle(Point p, Color c) {
		this.circle = new Ellipse2D.Double(p.getXC(), p.getYC(),
				Grid.CIRCLE_DIAMETER, Grid.CIRCLE_DIAMETER);
		g2.setColor(c);
		g2.fill(this.circle);
		g2.draw(this.circle);
	}

	public void initCursorLocation() {
		hl_x = 0;
		hl_y = 0;
	}
	public static Color getGridLineCol() {
		return gridLineCol;
	}

	public static void setGridLineCol(Color gridLineCol) {
		Grid.gridLineCol = gridLineCol;
	}
	public static int getGridLineStroke() {
		return gridLineStroke;
	}

	public static void setGridLineStroke(int gridLineStroke) {
		Grid.gridLineStroke = gridLineStroke;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		Grid.debug = debug;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}
	
}