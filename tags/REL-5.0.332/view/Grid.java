/**
 * 
 */
package view;

import java.awt.*;

import javax.swing.*;
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
	// private JKIcon board = new JKIcon("media/board1.PNG", "");
	protected static int gridLineStroke = 2, squareFadeVariant = 4,
			CURSOR_VARIANT_STROKE = 6;
	public static double CIRCLE_DIAMETER = 10.0, HALF_DIAMETER = 5.0,
			squareSize = 64, half_squareSize = 32;

	public static boolean mouseclicked = false, plotPoint = false,
			selectPoint = false, refresh = false, manualc = false,
			mouseMove = false, undo = false, saveSettings = false, ON = false;

	public boolean drawn = false;
	protected static Color gridLineCol = new Color(10, 60, 30);
	public static double Height = 640;// 640
	public static double Width = 1024;// 1024

	public static double Columns = 0.0, rows = 0.0;
	public static double x = 0, y = 0, hl_x = 0, hl_y = 0;
	public static Point selectedP = new Point(0, 0);
	public static Cell cell = null;

	private static volatile Grid instance = null;
	private static GridDimension dimension = null;

	public Grid(GridDimension dimension) {
		Grid.dimension = dimension;
		setPreferredSize(dimension.getPixelDimension());
		System.out.println("Grid size: " + dimension.getPixelDimension());
		setDimension(dimension);
		// getRootPane().add(board.createIcon());
	}

	public static Grid getInstance(GridDimension dimension) {
		if (instance == null) {
			synchronized (Grid.class) {
				if (instance == null) {
					instance = new Grid(dimension);
				}
			}
		}
		return instance;
	}

	public void paintComponent(Graphics g) {
		//super.paintComponent(g2);
		Grid.g2 = (Graphics2D) g;
		AbstractGame game = JKemik.game;

	try {
			Artist.drawCursor(new Point(hl_x, hl_y, 0), gridLineStroke,
					Grid.half_squareSize, gridLineCol, g2);
			highLightDot(game.getCurrentP().getColor());

			// play
			if (mouseclicked && plotPoint) {
				Artist.drawCircle(new Point(x, y, game.getCurrentP().getId()),
						game.getCurrentP().getColor(), Grid.HALF_DIAMETER,
						Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
				plotPoint = false;
				mouseclicked = false;
			}

			// capture by selection
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

			if (cell != null) {
				Artist.drawCell(cell, game.getCurrentP().getColor(), g2);
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
			}

			if (!this.drawn) {
				Artist.drawGrid(g2, Grid.dimension, Grid.squareFadeVariant,
						Grid.gridLineStroke, BoardFrame.BOARD_COLOR);

				if (Grid.refresh) {
					Artist.drawGame(game, g2);
					Grid.refresh = false;
				}
				this.drawn = true;
			}
		} catch (NullPointerException e) {
			System.out.println("NullPointer in paintComponent: "
					+ e.getMessage());
		}

	}

	public void highLightDot(Color c) {
		if (mouseMove) {
			BoardFrame.print_point
					.setText(""
							+ (new Point(hl_x, hl_y, JKemik.game.getCurrentP()
									.getId())).toString());
			Artist.drawCursor(
					new Point(x, y, JKemik.game.getCurrentP().getId()),
					gridLineStroke, Grid.half_squareSize, JKemik.game
							.getCurrentP().getColor(), g2);

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
		return new Point(Grid.x - HALF_DIAMETER, Grid.y - HALF_DIAMETER,
				JKemik.game.getCurrentP().getId());
	}

	public Point makeDrawable(Point p) {
		return new Point(p.getXC() - HALF_DIAMETER, p.getYC() - HALF_DIAMETER,
				JKemik.game.getCurrentP().getId());
	}

	public static Point undoMakeDrawable(Point drawable) {
		return new Point(drawable.getXC() + HALF_DIAMETER, drawable.getYC()
				+ HALF_DIAMETER, JKemik.game.getCurrentP().getId());
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
	 * @return the squareSize
	 */
	public static double getSquareSize() {
		return squareSize;
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

	public void repaintGrid() {
		this.repaint();
	}

	public static GridDimension getDimension() {
		return dimension;
	}

	public static Point getSelectedP() {
		return selectedP;
	}

	public static void setSelectedP(Point selectedP) {
		Grid.selectedP = selectedP;
	}

	public static void setDimension(GridDimension dimension) {
		Grid.dimension = dimension;
		calColAndRows(dimension.getSqrSize());
		Grid.squareSize = Grid.dimension.getSqrSize();
		Grid.half_squareSize = squareSize / 2;
		CIRCLE_DIAMETER = (int) (squareSize * .46);
		HALF_DIAMETER = CIRCLE_DIAMETER / 2;
		gridLineStroke = (int) (squareSize * .12);
		squareFadeVariant = (int) (squareSize * .25);
	}

}