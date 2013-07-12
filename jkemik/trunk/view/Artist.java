package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controler.JKemik;

import utilities.Tools;
import api.Cell;
import api.AbstractGame;
import api.GridDimension;
import api.Player;
import api.Point;

public class Artist {
	protected static void drawGridBG(Graphics2D g2, double w, double h) {

//		 Image img1 =
//		 Toolkit.getDefaultToolkit().getImage("media/board2.PNG");
//		 g2.drawImage(img1, (int)w, (int)h, BoardFrame.getGrid());
		 //g2.finalize();
		/*-------------------------------------------------------*/
//		BufferedImage img1 = null;
//		try {
//			img1 = ImageIO.read(new File("media/board2.PNG"));
//			g2.drawImage(img1, (int) w, (int) h, JKemik.view.getGrid());
//			g2.finalize();
//		} catch (IOException e) {
//		}
		/*-------------------------------------------------------*/
		 g2.setColor(Tools.boost(BoardFrame.THEME_COLOR));//BoardFrame.BOARD_COLOR
		 Rectangle2D.Double bg = new Rectangle2D.Double(0, 0,w,h);
		 g2.draw(bg);
		 g2.fill(bg);
	}
	protected static void drawBG(Graphics2D g2, JPanel jp, int w, int h, String str) {

		/*-------------------------------------------------------*/
		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(new File(str));
			g2.drawImage(img1, (int) w, (int) h, jp);
			g2.finalize();
		} catch (IOException e) {
		}
		/*-------------------------------------------------------*/
		// g2.setColor(BoardFrame.BOARD_COLOR);//BoardFrame.BOARD_COLOR
		// Rectangle2D.Double bg = new Rectangle2D.Double(0, 0,w,h);
		// g2.draw(bg);
		// g2.fill(bg);
	}

	public static void drawGrid(Graphics2D g2, GridDimension dimension,
			int squareFadeVariant, int gridLineStroke) {
		Dimension d = dimension.getPixelDimension();
		int Width = (int) d.getWidth(), Height = (int) d.getHeight(), squareSize = dimension
				.getSqrSize();
		Color c = Tools.fade(BoardFrame.THEME_COLOR);
		int currentColPos = 0;
		int currentRowPos = 0;
		int index = 0;
		double Columns = dimension.positionsDimension().getWidth();
		Artist.drawGridBG(g2, Width, Height);
		while (index < Columns) {
			// draw columns
			if (currentColPos <= Width) {
				Point from = new Point(squareSize * index, 0);
				Point to = new Point(squareSize * index, Height);
				Artist.drawLine(from, to, squareFadeVariant,
						Tools.fade(c), g2);
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c), g2);
				currentColPos += squareSize;
			}
			// draw rows
			if (currentRowPos <= Height) {

				Point from = new Point(0, squareSize * index);
				Point to = new Point(Width, squareSize * index);
				Artist.drawLine(from, to, squareFadeVariant,
						Tools.fade(c), g2);
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c), g2);
				currentRowPos += squareSize;
			}
			index++;
		}

		int index2 = 0;
		int currentColPos2 = 0;
		int currentRowPos2 = 0;
		while (index2 < Columns) {
			// draw columns
			if (currentColPos2 <= Width) {
				Point from = new Point(squareSize * index2, 0);
				Point to = new Point(squareSize * index2, Height);
				Artist.drawLine(from, to, gridLineStroke,c , g2);
				currentColPos2 += squareSize;
			}
			// draw rows
			if (currentRowPos2 <= Height) {

				Point from = new Point(0, squareSize * index2);
				Point to = new Point(Width, squareSize * index2);
				Artist.drawLine(from, to, gridLineStroke, c, g2);
				currentRowPos2 += squareSize;
			}
			index2++;
		}
		// Grid.Columns = Columns;

	}

	protected static void drawLine(Point from, Point to, int stroke, Color c,
			Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()),
				(int) (to.getXC()), (int) (to.getYC()));
	}

	protected static void drawCircle(Point p, Color c, double h_d, double c_d,
			int stroke, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		Point recenter = new Point(p.getXC() - h_d, p.getYC() - h_d);
		Ellipse2D.Double circle = new Ellipse2D.Double(recenter.getXC(),
				recenter.getYC(), c_d, c_d);
		g2.setColor(c);
		g2.fill(circle);
		g2.setColor(Tools.fade(c));
		g2.draw(circle);
		g2.setColor(c);
	}

	protected static void drawCursor(Point p, int stroke, double h_sqr,
			Color c, Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + h_sqr, p.getXC(), p
				.getYC() - h_sqr));
		g2.draw(new Line2D.Double(p.getXC() - h_sqr, p.getYC(), p.getXC()
				+ h_sqr, p.getYC()));
		g2.setColor(c);
	}

	protected static boolean drawCell(Cell cell, Graphics2D g2) {
		AbstractGame game = JKemik.game;
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
				return unDrawCell(cell, g2);
			}
			game.getCurrentP().getConnectedPoints().addAll(contour);

			/* draw cell contour */
			drawLine(contour.get(0), contour.get(contour.size() - 1),
					Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, game
							.getCurrentP().getColor(), g2);
			for (int i = 0; i < contour.size() - 1; i++) {

				drawLine(contour.get(i), contour.get(i + 1),
						Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, game
								.getCurrentP().getColor(), g2);
			}

			// draw positions
			for (Point p : contour) {
				drawCircle(p, game.getCurrentP().getColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);

				drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize,
						Grid.gridLineCol, g2);
				g2.setColor(game.getCurrentP().getColor());
				g2.setStroke(new BasicStroke(Grid.gridLineStroke));// +
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

	protected static boolean unDrawCell(Cell cell, Graphics2D g2) {
		try {
			ArrayList<Point> contour = cell.getCellContour();
			System.out.println("Contour: " + contour);
			/* Erase cell contour */
			drawLine(contour.get(0), contour.get(contour.size() - 1),
					Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE,
					BoardFrame.BOARD_COLOR, g2);
			for (int i = 0; i < contour.size() - 1; i++) {
				System.out.println("Lines: " + i);
				drawLine(contour.get(i), contour.get(i + 1),
						Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE,
						BoardFrame.BOARD_COLOR, g2);
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(Grid.gridLineStroke
						+ Grid.CURSOR_VARIANT_STROKE));
			}

			/* Fix circle graphics */
			for (int e = 0; e < contour.size(); e++) {
				drawCircle(contour.get(e),
						JKemik.game.getCurrentP().getColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
				drawCursor(contour.get(e), Grid.gridLineStroke,
						Grid.half_squareSize, Grid.gridLineCol, g2);
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(Grid.gridLineStroke
						+ Grid.CURSOR_VARIANT_STROKE));
			}
		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	protected static void drawGame(AbstractGame g, Graphics2D g2) {
		Player p1 = (Player) g.getPlayer1();
		Player p2 = (Player) g.getPlayer2();
		ArrayList<Cell> p1c = p1.getCells();
		ArrayList<Cell> p2c = p2.getCells();

		// draw p1 cells
		if (drawCell(p1c, p1, p2, g2)) {
		}

		// draw p1 cells
		if (drawCell(p2c, p2, p1, g2)) {
		}
		// draw p1 points
		for (Point p : p1.getPloted()) {

			Artist.drawCircle(p, p1.getColor(), Grid.HALF_DIAMETER,
					Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);
			Artist.drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize,
					Grid.gridLineCol, g2);
		}
		// draw p1 points
		for (Point p : p2.getPloted()) {

			Artist.drawCircle(p, p2.getColor(), Grid.HALF_DIAMETER,
					Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);
			Artist.drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize,
					Grid.gridLineCol, g2);
		}
		if (Grid.manualc) {
			for (Point p : g.getCurrentP().getSelected()) {

				Artist.drawCircle(p, g.getCurrentP().getFadedColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
				Artist.drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize,
						Grid.gridLineCol, g2);
			}
		}
	}

	/**
	 * @param Arraylist
	 *            of pl1 cells, pl1, pl2
	 * @return void Draws a cell with all its content.
	 * */
	protected static boolean drawCell(ArrayList<Cell> cells, Player pl1,
			Player pl2, Graphics2D g2) {

		try {
			for (Cell c : cells) {
				ArrayList<Point> contour = c.getCellContour();

				/* draw cell contour */
				Artist.drawLine(contour.get(0),
						contour.get(contour.size() - 1), Grid.gridLineStroke
								+ Grid.CURSOR_VARIANT_STROKE, pl1.getColor(),
						g2);
				for (int i = 0; i < contour.size() - 1; i++) {

					Artist.drawLine(contour.get(i), contour.get(i + 1),
							Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE,
							pl1.getColor(), g2);

					// draw intersection
					Artist.drawCursor(contour.get(i), Grid.gridLineStroke,
							Grid.half_squareSize, Grid.gridLineCol, g2);

					Artist.drawCursor(contour.get(i + 1), Grid.gridLineStroke,
							Grid.half_squareSize, Grid.gridLineCol, g2);
					g2.setColor(pl1.getColor());
					g2.setStroke(new BasicStroke(Grid.gridLineStroke
							+ Grid.CURSOR_VARIANT_STROKE));
				}
				if (drawCell(c.getCellsInCell(), pl2, pl1, g2)) {
				}
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	protected static void unDraw(Point p, Graphics2D g2) {

		try {
			double px = p.getXC();
			double py = p.getYC();
			Ellipse2D.Double circle = new Ellipse2D.Double(px
					- Grid.HALF_DIAMETER, py - Grid.HALF_DIAMETER,
					Grid.CIRCLE_DIAMETER, Grid.CIRCLE_DIAMETER);
			g2.setColor(BoardFrame.BOARD_COLOR);
			//g2.setColor(BoardFrame.THEME_COLOR);
			g2.fill(circle);
			g2.draw(circle);
			g2.setColor(Grid.gridLineCol);
			// drawCursor(p, squareFadeVariant,
			Artist.drawCursor(p, Grid.squareFadeVariant, Grid.half_squareSize,
					Tools.fade(BoardFrame.BOARD_COLOR), g2);
			drawLongCursor(p, Grid.gridLineStroke, Grid.gridLineCol, g2);
		} catch (Exception e) {
			System.err.println("Error in unDraw: " + e.getMessage());
		}
	}

	protected static void drawLongCursor(Point p, int stroke, Color c,
			Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + Grid.squareSize, p
				.getXC(), p.getYC() - Grid.squareSize));
		g2.draw(new Line2D.Double(p.getXC() - Grid.squareSize, p.getYC(), p
				.getXC() + Grid.squareSize, p.getYC()));
		g2.setColor(JKemik.game.getCurrentP().getColor());
	}

	protected static void unDrawSelection(ArrayList<Point> contour,
			Graphics2D g2) {
		AbstractGame game = JKemik.game;
		try {
			/* Erase last line */
			int index = contour.size() - 1;
			Point lastp = null, before_lastp = null;
			if (contour.size() > 1) {
				lastp = contour.get(index);
				before_lastp = contour.get(index - 1);
			} else if (contour.size() == 1) {
				lastp = contour.get(index);
				drawCircle(lastp, game.getCurrentP().getColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
				drawCursor(lastp, Grid.gridLineStroke, Grid.half_squareSize,
						Grid.gridLineCol, g2);
				contour.remove(index);
				return;
			} else {
				return;
			}

			drawLine(lastp, before_lastp, Grid.gridLineStroke
					+ Grid.CURSOR_VARIANT_STROKE, BoardFrame.BOARD_COLOR, g2);
			drawCircle(lastp, game.getCurrentP().getColor(),
					Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
					Grid.gridLineStroke, g2);
			drawCursor(lastp, Grid.gridLineStroke, Grid.half_squareSize,
					Grid.gridLineCol, g2);
			drawCircle(before_lastp, game.getCurrentP().getFadedColor(),
					Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
					Grid.gridLineStroke, g2);
			drawCursor(before_lastp, Grid.gridLineStroke, Grid.half_squareSize,
					Grid.gridLineCol, g2);

			if (!contour.isEmpty()) {
				game.setLastp(before_lastp);
				contour.remove(index);
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
	}
}
