package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import controler.JKemik;

import utilities.Tools;
import api.Cell;
import api.Game;
import api.Point;

public class Artist {

	protected static void drawLine(Point from, Point to,int stroke, Color c, Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));
	}
	protected static void drawCircle(Point p, Color c, double h_d, double c_d, int stroke, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		Point recenter = new Point(p.getXC() - h_d, p.getYC()
				- h_d);
		Ellipse2D.Double circle = new Ellipse2D.Double(recenter.getXC(), recenter.getYC(),
				c_d, c_d);
		g2.setColor(c);
		g2.fill(circle);
		g2.setColor(Tools.fade(c));
		g2.draw(circle);
		g2.setColor(c);
	}
	protected static void drawCursor(Point p, int stroke,double h_sqr, Color c, Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + h_sqr, p
				.getXC(), p.getYC() - h_sqr));
		g2.draw(new Line2D.Double(p.getXC() - h_sqr, p.getYC(), p
				.getXC()
				+ h_sqr, p.getYC()));
		g2.setColor(c);
	}
	protected static boolean drawCell(Cell cell, Graphics2D g2) {
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
				return unDrawCell(cell, g2);
			}
			game.getCurrentP().getConnectedPoints().addAll(contour);
			/* draw cell contour */
			
			drawLine(contour.get(0), contour.get(contour.size() - 1), Grid.gridLineStroke
							+ Grid.CURSOR_VARIANT_STROKE, game.getCurrentP().getColor(), g2);
			for (int i = 0; i < contour.size() - 1; i++) {
				
				drawLine(contour.get(i), contour.get(i + 1), Grid.gridLineStroke
						+ Grid.CURSOR_VARIANT_STROKE, game.getCurrentP().getColor(), g2);
			}

			// draw positions
//			g2.setColor(game.getCurrentP().getColor());
//			g2.setStroke(new BasicStroke(gridLineStroke));// +
			for (Point p : contour) {
				drawCircle(p, game.getCurrentP()
						.getColor(), Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
				
				drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize, Grid.gridLineCol, g2);
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
	public static boolean unDrawCell(Cell cell, Graphics2D g2) {
		try {
			ArrayList<Point> contour = cell.getCellContour();

			/* set color */
//			g2.setColor(BoardFrame.BOARD_COLOR);
//			g2.setStroke(new BasicStroke(gridLineStroke + CURSOR_VARIANT_STROKE));// +

			/* Erase cell contour */
			//drawLine(contour.get(0), contour.get(contour.size() - 1));
			drawLine(contour.get(0), contour.get(contour.size() - 1), Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, BoardFrame.BOARD_COLOR, g2);
			for (int i = 0; i < contour.size() - 1; i++) {
				//drawLine(contour.get(i), contour.get(i + 1));
				drawLine(contour.get(i), contour.get(i + 1), Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, BoardFrame.BOARD_COLOR, g2);
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(Grid.gridLineStroke
						+ Grid.CURSOR_VARIANT_STROKE));
			}

			// Fix circle graphics
			for (int e = 0; e < contour.size() - 1; e++) {
				//drawCircle(contour.get(e), JKemik.game.getCurrentP().getColor());
				drawCircle(contour.get(e), JKemik.game.getCurrentP()
						.getColor(), Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
				//drawCursor(contour.get(e), gridLineCol);
				drawCursor(contour.get(e), Grid.gridLineStroke, Grid.half_squareSize, Grid.gridLineCol, g2);
				g2.setColor(BoardFrame.BOARD_COLOR);
				g2.setStroke(new BasicStroke(Grid.gridLineStroke
						+ Grid.CURSOR_VARIANT_STROKE));
			}
		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}
}
