/**
 * 
 */
package view;

import java.awt.*;
import javax.swing.*;

import utilities.*;
import controler.*;
import api.*;
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
	
	protected static int gridLineStroke = 2, squareFadeVariant = 4,
			CURSOR_VARIANT_STROKE = 6;
	public static double CIRCLE_DIAMETER = 10.0, HALF_DIAMETER = 5.0,
			squareSize = 64, half_squareSize = 32;

	public static boolean mouseclicked = false, plotPoint = false,
			selectPoint = false, refresh = false, manualc = false,
			mouseMove = false, undo = false, saveSettings = false, ON = false;
	
	public boolean drawn = false;

	protected static Color pcolor = new Color(255, 255, 255);
	protected static Color ccolor = new Color(255, 255, 255);// Cursor color
	protected static Color gridLineCol = new Color(10, 60, 30);

	public static double Height = 704;//640
	public static double Width = 1024;//1024

	public static double Columns = 0.0, rows = 0.0;
	public static double x = 0, y = 0, hl_x = 0, hl_y = 0;
	public static Point selectedP = new Point(0, 0);
	public static Cell cell = null;
	public static int position_count = 0;
	private static int size;
	private static volatile Grid instance = null;
	public Grid(int squareSize) {
		setPreferredSize(new Dimension((int) Width, (int) Height));
		setSquareSize(squareSize);
		calColAndRows((int) squareSize);
	}

	public static Grid getInstance(int squareSize) {
		if (instance == null) {
			synchronized (Grid.class) {
				if (instance == null) {
					instance = new Grid(squareSize);
				}
			}
		}
		return instance;
	}

	public void paintComponent(Graphics g) {
		Grid.g2 = (Graphics2D) g;
		// super.paintComponent(g2);
		try {
			Artist.drawCursor(new Point(hl_x, hl_y), gridLineStroke,
					Grid.half_squareSize, gridLineCol, g2);
			highLightDot(ccolor);

			AbstractGame game = JKemik.game;
			if (mouseclicked && plotPoint) {
				Artist.drawCircle(new Point(x, y), game.getCurrentP()
						.getColor(), Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						gridLineStroke, g2);
				plotPoint = false;
				mouseclicked = false;
			}

			if (selectPoint && game.getCurrentP().getSelected().size() >= 1) {
				Color fade = game.getCurrentP().getFadedColor();
				Artist.drawLine(game.getLastp(), selectedP, gridLineStroke
						+ CURSOR_VARIANT_STROKE, fade, g2);
				Artist.drawCircle(game.getLastp(), fade, Grid.HALF_DIAMETER,
						Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
				Artist.drawCursor(game.getLastp(), gridLineStroke,
						Grid.half_squareSize, gridLineCol, g2);
				Artist.drawCircle(selectedP, fade, Grid.HALF_DIAMETER,
						Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
				Artist.drawCursor(selectedP, gridLineStroke,
						Grid.half_squareSize, gridLineCol, g2);
				game.setLastp(selectedP);
				g2.setColor(fade);
				selectPoint = false;
			}

			if (Artist.drawCell(cell, g2)) {
				cell = null;
			}

			if (undo) {
				if (manualc) {
					if (game.getCurrentP().getSelected().size() >= 1) {
						Artist.unDrawSelection(
								game.getCurrentP().getSelected(), g2);//
					} else {

					}
				} else {
					if (game.undo()) {
						Artist.unDraw(game.getLastp(), g2);
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
					Artist.drawGame(game, g2);
					Grid.refresh = false;
				}
				this.drawn = true;
			}
		} catch (Exception e) {
			System.out.println("Error in paint: " + e.getMessage());
		}

	}

	public void highLightDot(Color c) {
		if (mouseMove) {
			BoardFrame.print_point.setText("" + (new Point(hl_x, hl_y)).toString());
			// g2.setColor(ccolor);
			// drawCursor(x, y);
			Artist.drawCursor(new Point(x, y), gridLineStroke,
					Grid.half_squareSize, ccolor, g2);
			hl_x = x;
			hl_y = y;
		}
		mouseMove = false;
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

	public void switchTurn() {
		AbstractGame game = JKemik.game;
		if (game.getCurrentP().compareTo(game.getPlayer1()) == 0) {
			BoardFrame.p2panel.setLabelColor(game.getPlayer2().getColor());
			BoardFrame.p1panel.setLabelColor(game.getPlayer1().getFadedColor());
		} else {
			BoardFrame.p1panel.setLabelColor(game.getPlayer1().getColor());
			BoardFrame.p2panel.setLabelColor(game.getPlayer2().getFadedColor());
		}
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
			squareFadeVariant = 4;
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
		Artist.drawGridBG(g2, 50, 50);
		while (index < Columns + 1) {
			// draw columns
			if (currentposition <= Width) {
				Point from = new Point(squareSize * index, 0);
				Point to = new Point(Grid.squareSize * index, Grid.Height);
				Artist.drawLine(from, to, squareFadeVariant, Tools
						.fade(BoardFrame.BOARD_COLOR), g2);
				Artist.drawLine(from, to, gridLineStroke, Tools
						.fade(BoardFrame.BOARD_COLOR), g2);
				position_count++;
			}
			// draw rows
			if (currentposition <= Grid.Height) {

				Point from = new Point(0, Grid.squareSize * index);
				Point to = new Point(Grid.Width, Grid.squareSize * index);
				Artist.drawLine(from, to, squareFadeVariant, Tools
						.fade(BoardFrame.BOARD_COLOR), g2);
				Artist.drawLine(from, to, gridLineStroke, Tools
						.fade(BoardFrame.BOARD_COLOR), g2);
				position_count++;
			}
			currentposition += Grid.squareSize;
			index++;
		}

		int index2 = 0;
		int currentposition2 = 0;
		while (index2 < Columns + 1) {
			// draw columns
			if (currentposition2 <= Width) {
				Point from = new Point(squareSize * index2, 0);
				Point to = new Point(Grid.squareSize * index2, Grid.Height);
				Artist.drawLine(from, to, gridLineStroke, Color.BLACK, g2);
			}
			// draw rows
			if (currentposition2 <= Grid.Height) {

				Point from = new Point(0, Grid.squareSize * index2);
				Point to = new Point(Grid.Width, Grid.squareSize * index2);
				Artist.drawLine(from, to, gridLineStroke, Color.BLACK, g2);
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
		// xcoor = xcoor + squareSize;
		// ycoor = ycoor + squareSize;
		int deltax = 0, deltay = 0;
		int xc = 0, yc = 0;
		deltax = (int) (xcoor % square);
		xc = square - deltax;
		// xc = square;

		deltay = (int) (ycoor % square);
		yc = square - deltay;
		// if (!outOfBoard(xcoor, ycoor, square)) {
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
		// }
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

	public static void setRefresh(boolean debug) {
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