/**
 * 
 */
package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;

import utilities.Tools;

import controler.JKemik;
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
	private static Color gridLineCol = new Color(10, 60, 30);
	private static int gridLineStroke = 2;
	private static int squareFadeVariant = 4;
	private static int CURSOR_VARIANT_STROKE = 6;
	public static double CIRCLE_DIAMETER = 10.0;
	public static double HALF_DIAMETER = 5.0;
	public static double squareSize = 64;
	public static double half_squareSize = 32;
	public static boolean mouseclicked = false;
	public static boolean plotPoint = false;
	public static boolean selectPoint = false;
	public static boolean refresh = false;
	public static boolean manualc = false;
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
	public static Point selectedP = new Point(0, 0);
	public static Cell cell = null;
	public static int position_count = 0;
	private static int size;

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

	public static Point makeDrawable(double x, double y) {
		return new Point(Grid.x - HALF_DIAMETER, Grid.y - HALF_DIAMETER);
	}

	public Point makeDrawable(Point p) {
		return new Point(p.getXC() - HALF_DIAMETER, p.getYC() - HALF_DIAMETER);
	}

	public static Point undoMakeDrawable(Point drawable) {
		return new Point(drawable.getXC() + HALF_DIAMETER, drawable.getYC()
				+ HALF_DIAMETER);
	}

	public void paintComponent(Graphics g) {
		Grid.g2 = (Graphics2D) g;
		// super.paintComponent(g2);
		try {
			drawCursor(hl_x, hl_y, gridLineCol);
			highLightDot(ccolor);

			Game game = JKemik.game;
			if (mouseclicked && plotPoint) {
				drawCircle(new Point(x, y), game.getCurrentP().getColor());
				plotPoint = false;
				mouseclicked = false;
			}

			if (selectPoint && game.getCurrentP().getSelected().size() >= 1) {
				Color fade = game.getCurrentP().getFadedColor();
				g2.setColor(fade);
				drawLine(game.getLastp(), selectedP, gridLineStroke
						+ CURSOR_VARIANT_STROKE);
				drawCircle(game.getLastp(), fade);
				drawCursor(game.getLastp(), gridLineCol);
				drawCircle(selectedP, fade);
				drawCursor(selectedP, gridLineCol);
				game.setLastp(selectedP);
				g2.setColor(fade);
				selectPoint = false;
			}

			if (drawCell(cell)) {
				cell = null;
			}

			if (undo) {
				if (manualc) {
					if (game.getCurrentP().getSelected().size() >= 1) {
						unDrawSelection(game.getCurrentP().getSelected());
					} else {

					}
				} else {
					if (game.undo()) {
						unDraw(game.getLastp());
					}
				}
				undo = false;
			}

			//
			if (JKemik.settings_t.isAutoPass()
					&& game.getCurrentP().getPlay_flag() == 1) {
				System.out.println("Automatic pass is true...");
				game.switchPlayTurns();
				Grid.setCcolor(game.getCurrentP().getColor());
			}

			if (!this.drawn) {
				drawGrid();
				size = (int) (Columns * rows);
				if (Grid.refresh) {
					drawGame(game);
					Grid.refresh = false;
				}
				this.drawn = true;
			}
		} catch (Exception e) {
			System.out.println("Error in paint: " + e.getMessage());
		}

	}

	public void highLightDot(Color c) {
		BoardFrame.print_point.setText("" + (new Point(hl_x, hl_y)).toString());
		if (mouseMove) {
			g2.setColor(ccolor);
			drawCursor(x, y);
			hl_x = x;
			hl_y = y;
		}
		mouseMove = false;
	}

	private boolean drawCell(Cell cell) {
		Game game = JKemik.game;
		try {
			if (cell == null) {
				return false;
			}
			ArrayList<Point> contour = cell.getCellContour();
			ArrayList<Point> captured = cell.getCapturedPoints();
			ArrayList<Point> area = cell.getAreaIncell();
			if (area.isEmpty()) {
				return false;
			}
			if (captured.isEmpty()) {
				System.out.println("About to undraw a cell");
				return unDrawCell(cell);
			}
			game.getCurrentP().getConnectedPoints().addAll(contour);
			/* set color */
			g2.setColor(game.getCurrentP().getColor());
			g2
					.setStroke(new BasicStroke(gridLineStroke
							+ CURSOR_VARIANT_STROKE));
			/* draw cell contour */
			drawLine(contour.get(0), contour.get(contour.size() - 1));
			for (int i = 0; i < contour.size() - 1; i++) {
				drawLine(contour.get(i), contour.get(i + 1));
			}

			// draw positions
			g2.setColor(game.getCurrentP().getColor());
			g2.setStroke(new BasicStroke(gridLineStroke));// +
			for (Point p : contour) {
				drawCircle(p, game.getCurrentP().getColor());
				drawCursor(p, gridLineCol);
				g2.setColor(game.getCurrentP().getColor());
				g2.setStroke(new BasicStroke(gridLineStroke));// +
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

	public boolean unDrawCell(Cell cell) {
		try {
			ArrayList<Point> contour = cell.getCellContour();

			/* set color */
			g2.setColor(BoardFrame.BOARD_COLOR);
			g2
					.setStroke(new BasicStroke(gridLineStroke
							+ CURSOR_VARIANT_STROKE));// +

			/* Erase cell contour */
			drawLine(contour.get(0), contour.get(contour.size() - 1));
			for (int i = 0; i < contour.size() - 1; i++) {
				drawLine(contour.get(i), contour.get(i + 1));
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(gridLineStroke
						+ CURSOR_VARIANT_STROKE));
			}

			// Fix circle graphics
			for (int e = 0; e < contour.size() - 1; e++) {
				drawCircle(contour.get(e), JKemik.game.getCurrentP().getColor());
				drawCursor(contour.get(e), gridLineCol);
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(gridLineStroke
						+ CURSOR_VARIANT_STROKE));
			}
		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	public void simplyUndrawSelection(ArrayList<Point> contour) {
		Game game = JKemik.game;
		try {
			for (int i = 0; i < contour.size() - 1; i++) {
				drawCircle(contour.get(i), game.getCurrentP().getColor());
			}
			game.getCurrentP().setSelected(null);
		} catch (NullPointerException e) {
			System.out.println("In simplyUndrawSelection: " + e.getMessage());
		}
	}

	public void unDrawSelection(ArrayList<Point> contour) {
		Game game = JKemik.game;
		try {
			/* set color */
			g2.setColor(BoardFrame.BOARD_COLOR);
			g2
					.setStroke(new BasicStroke(gridLineStroke
							+ CURSOR_VARIANT_STROKE));// 

			/* Erase last line */
			int index = contour.size() - 1;
			Point lastp = null, before_lastp = null;
			if (contour.size() > 1) {
				lastp = contour.get(index);
				before_lastp = contour.get(index - 1);
			} else if (contour.size() == 1) {
				lastp = contour.get(index);
				drawCircle(lastp, game.getCurrentP().getColor());
				drawCursor(lastp, gridLineCol);
				contour.remove(index);
				return;
			} else {
				return;
			}

			drawLine(lastp, before_lastp);
			drawCircle(lastp, game.getCurrentP().getColor());
			drawCursor(lastp, gridLineCol);
			drawCircle(before_lastp, game.getCurrentP().getFadedColor());
			drawCursor(before_lastp, gridLineCol);

			if (!contour.isEmpty()) {
				game.setLastp(before_lastp);
				contour.remove(index);
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
	}

	/**
	 * @param Arraylist
	 *            of pl1 cells, pl1, pl2
	 * @return void Draws a cell with all its content.
	 * */
	private static boolean drawCell(ArrayList<Cell> cells, Player pl1,
			Player pl2) {

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

					// draw intersection
					drawCursor(contour.get(i), gridLineCol);
					drawCursor(contour.get(i + 1), gridLineCol);
					g2.setColor(pl1.getColor());
					g2.setStroke(new BasicStroke(gridLineStroke
							+ CURSOR_VARIANT_STROKE));
				}
				if (drawCell(c.getCellsInCell(), pl2, pl1)) {
				}
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

		// draw p1 cells
		if (drawCell(p1c, p1, p2)) {

		}
		// draw p1 cells
		if (drawCell(p2c, p2, p1)) {
		}
		// draw p1 points
		for (Point p : p1.getPloted()) {
			drawCircle(p, p1.getColor());
			drawCursor(p, gridLineCol);
		}
		// draw p1 points
		for (Point p : p2.getPloted()) {
			drawCircle(p, p2.getColor());
			drawCursor(p, gridLineCol);
		}
		if (Grid.manualc) {
			for (Point p : g.getCurrentP().getSelected()) {
				drawCircle(p, g.getCurrentP().getFadedColor());
				drawCursor(p, gridLineCol);
			}
		}
	}

	private static void drawLine(Point from, Point to) {
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));
	}

	private static void drawLine(Point from, Point to, int stroke) {
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));

	}

	private static void drawLine(double x1, double y1, double x2, double y2,
			int stroke) {
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
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
			this.circle = new Ellipse2D.Double(px - HALF_DIAMETER, py
					- HALF_DIAMETER, Grid.CIRCLE_DIAMETER, Grid.CIRCLE_DIAMETER);
			g2.setColor(BoardFrame.BOARD_COLOR);
			g2.fill(this.circle);
			g2.draw(this.circle);
			g2.setColor(gridLineCol);
			drawCursor(p, squareFadeVariant, Tools.fade(BoardFrame.BOARD_COLOR));
			drawLongCursor(p, Grid.gridLineStroke, gridLineCol);
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
		// BasicStroke bs = new BasicStroke(gridLineStroke,
		// BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(x, y + half_squareSize, x, y
				- half_squareSize));
		g2.draw(new Line2D.Double(x - half_squareSize, y, x + half_squareSize,
				y));
		g2.setColor(pcolor);

	}

	public static void drawCursor(Point p, int stroke, Color c) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + half_squareSize, p
				.getXC(), p.getYC() - half_squareSize));
		g2.draw(new Line2D.Double(p.getXC() - half_squareSize, p.getYC(), p
				.getXC()
				+ half_squareSize, p.getYC()));
		g2.setColor(pcolor);
	}

	public static void drawCursor(Point p, Color c) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + half_squareSize, p
				.getXC(), p.getYC() - half_squareSize));
		g2.draw(new Line2D.Double(p.getXC() - half_squareSize, p.getYC(), p
				.getXC()
				+ half_squareSize, p.getYC()));
		g2.setColor(pcolor);
	}

	public static void drawCursor(Point p, Color c, Color set_back) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(gridLineStroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + half_squareSize, p
				.getXC(), p.getYC() - half_squareSize));
		g2.draw(new Line2D.Double(p.getXC() - half_squareSize, p.getYC(), p
				.getXC()
				+ half_squareSize, p.getYC()));
		g2.setColor(set_back);
	}

	public static void drawLongCursor(Point p, int stroke, Color c) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + squareSize, p.getXC(),
				p.getYC() - squareSize));
		g2.draw(new Line2D.Double(p.getXC() - squareSize, p.getYC(), p.getXC()
				+ squareSize, p.getYC()));
		g2.setColor(pcolor);
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
			squareFadeVariant = 6;
			size = ((int) (Width / Grid.squareSize) * (int) (Height / Grid.squareSize))
			+ ((int) (Width / Grid.squareSize) + (int) (Height / Grid.squareSize));
		}

		if (Grid.squareSize == 32.0) {
			CIRCLE_DIAMETER = 18;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 4;
			squareFadeVariant = 8;
			size = ((int) (Width / Grid.squareSize) * (int) (Height / Grid.squareSize))
			+ ((int) (Width / Grid.squareSize) + (int) (Height / Grid.squareSize));
		}

		if (Grid.squareSize == 64.0) {
			CIRCLE_DIAMETER = 24;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 6;
			squareFadeVariant = 14;
			size = ((int) (Width / Grid.squareSize) * (int) (Height / Grid.squareSize))
			+ ((int) (Width / Grid.squareSize) + (int) (Height / Grid.squareSize));
		}

		if (Grid.squareSize == 128.0) {
			CIRCLE_DIAMETER = 32;
			HALF_DIAMETER = CIRCLE_DIAMETER / 2;
			gridLineStroke = 8;
			squareFadeVariant = 20;
			size = ((int) (Width / Grid.squareSize) * (int) (Height / Grid.squareSize))
					+ ((int) (Width / Grid.squareSize) + (int) (Height / Grid.squareSize));
		}
	}

	public static void drawGrid() {
		calColAndRows((int) Grid.squareSize);

		int currentposition = 0;
		int index = 0;
		while (index < Columns + 1) {
			// draw columns
			if (currentposition <= Width) {
				g2.setColor(Tools.fade(BoardFrame.BOARD_COLOR));
				drawLine(squareSize * index, 0, Grid.squareSize * index,
						Grid.Height, squareFadeVariant);
				g2.setColor(Grid.gridLineCol);
				drawLine(squareSize * index, 0, Grid.squareSize * index,
						Grid.Height, gridLineStroke);
				position_count++;
			}
			// draw rows
			if (currentposition <= Grid.Height) {
				g2.setColor(Tools.fade(BoardFrame.BOARD_COLOR));
				drawLine(0, Grid.squareSize * index, Grid.Width,
						Grid.squareSize * index, squareFadeVariant);
				g2.setColor(Grid.gridLineCol);
				drawLine(0, Grid.squareSize * index, Grid.Width,
						Grid.squareSize * index, gridLineStroke);
				position_count++;
			}
			currentposition += Grid.squareSize;
			index++;
		}

		// calColAndRows((int) Grid.squareSize);
		// g2.setColor(Grid.gridLineCol);
		int index2 = 0;
		int currentposition2 = 0;
		while (index2 < Columns + 1) {
			// draw columns
			if (currentposition2 <= Width) {
				drawLine(squareSize * index2, 0, Grid.squareSize * index2,
						Grid.Height, gridLineStroke);
			}
			// draw rows
			if (currentposition2 <= Grid.Height) {
				drawLine(0, Grid.squareSize * index2, Grid.Width,
						Grid.squareSize * index2, gridLineStroke);
			}
			currentposition2 += Grid.squareSize;
			index2++;
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

	public static void closestTo(double xcoor, double ycoor, int square) {
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
		return mouseclicked;
	}

	/**
	 * @param mouseclicked
	 *            the mouse clicked to set
	 */
	public void setMouseclicked(boolean mouseclicked) {
		Grid.mouseclicked = mouseclicked;
	}

	public void drawCircle(Point p, Color c) {
		g2.setStroke(new BasicStroke(gridLineStroke));
		Point recenter = new Point(p.getXC() - HALF_DIAMETER, p.getYC()
				- HALF_DIAMETER);
		this.circle = new Ellipse2D.Double(recenter.getXC(), recenter.getYC(),
				CIRCLE_DIAMETER, CIRCLE_DIAMETER);
		g2.setColor(c);
		g2.fill(this.circle);
		g2.setColor(Tools.fade(c));
		g2.draw(this.circle);
		g2.setColor(c);
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
		return refresh;
	}

	public static void setDebug(boolean debug) {
		Grid.refresh = debug;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

	public static boolean isManualc() {
		return manualc;
	}

	public static void setManualc(boolean manualc) {
		Grid.manualc = manualc;
	}

	public static boolean isSelectPoint() {
		return selectPoint;
	}

	public static void setSelectPoint(boolean selectPoint) {
		Grid.selectPoint = selectPoint;
	}

	public static Point getSelectedP() {
		return selectedP;
	}

	public static void setSelectedP(Point selectedP) {
		Grid.selectedP = selectedP;
	}

	public void repaintGrid() {
		this.repaint();
	}

	public static int getBoardSize() {
		return size;
	}

	public static void setBoardSize(int size) {
		Grid.size = size;
	}

}