package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import utilities.Globals;
import utilities.Tools;
import api.Cell;
import api.AbstractGame;
import api.GridDimension;
import api.Player;
import api.Point;
import static view.Grid.gridLineStroke;

public class Artist {
    
	protected static void drawGridBG(Graphics2D g2, double w, double h) {

		// Image img1 =
		// Toolkit.getDefaultToolkit().getImage("media/board2.PNG");
		// g2.drawImage(img1, (int)w, (int)h, BoardFrame.getGrid());
		// g2.finalize();
		/*-------------------------------------------------------*/
		// BufferedImage img1 = null;
		// try {
		// img1 = ImageIO.read(new File("media/board2.PNG"));
		// g2.drawImage(img1, (int) w, (int) h, JKemik.view.getGrid());
		// g2.finalize();
		// } catch (IOException e) {
		// }
		/*-------------------------------------------------------*/
		g2.setColor(BoardFrame.BOARD_COLOR);// BoardFrame.BOARD_COLOR
		Rectangle2D.Double bg = new Rectangle2D.Double(0, 0, w, h);
		g2.draw(bg);
		g2.fill(bg);
	}

	protected static void drawBG(Graphics2D g2, JPanel jp, int w, int h,
			String str) {

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
			int squareFadeVariant, int gridLineStroke, Color c) {
		Dimension d = dimension.getPixelDimension();
		int Width = (int) d.getWidth(), Height = (int) d.getHeight(), squareSize = dimension
				.getSqrSize();

		// double sqrLineFadePercent = Globals.SQR_LINE_SHADE_PERCENT;
		int gridLineFadePercent = 35;
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
						Tools.fade(c, Globals.SQR_LINE_SHADE_PERCENT), g2);
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c, Globals.SQR_LINE_SHADE_PERCENT), g2);
				currentColPos += squareSize;
			}
			// draw rows
			if (currentRowPos <= Height) {

				Point from = new Point(0, squareSize * index);
				Point to = new Point(Width, squareSize * index);
				Artist.drawLine(from, to, squareFadeVariant,
						Tools.fade(c, Globals.SQR_LINE_SHADE_PERCENT), g2);
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c, Globals.SQR_LINE_SHADE_PERCENT), g2);
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
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c, gridLineFadePercent), g2);
				currentColPos2 += squareSize;
			}
			// draw rows
			if (currentRowPos2 <= Height) {

				Point from = new Point(0, squareSize * index2);
				Point to = new Point(Width, squareSize * index2);
				Artist.drawLine(from, to, gridLineStroke,
						Tools.fade(c, gridLineFadePercent), g2);
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
		g2.setStroke(new BasicStroke(stroke + 1));
		Point recenter = new Point(p.getXC() - h_d, p.getYC() - h_d);
		Ellipse2D.Double circle = new Ellipse2D.Double(recenter.getXC(),
				recenter.getYC(), c_d, c_d);

		g2.setColor(c);
		g2.fill(circle);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Tools.fade(c,Globals.SQR_LINE_SHADE_PERCENT));
		g2.draw(circle);
                
                /*Debug mode: uncomment de debut AI board status*/
                g2.setColor(Color.ORANGE);
                String str = "" + p.getHeatLevel();
                g2.drawString(str, (int)(p.getXC() - h_d), (int)(p.getYC() - h_d));
                /*End debug code*/
                g2.setColor(c);
                
                
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
						JKemik.getGame().getCurrentP().getColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
//				drawCursor(contour.get(e), Grid.gridLineStroke,
//						Grid.half_squareSize, Grid.gridLineCol, g2);
//				g2.setColor(BoardFrame.BOARD_COLOR);
//				g2.setStroke(new BasicStroke(Grid.gridLineStroke
//						+ Grid.CURSOR_VARIANT_STROKE));
			}
		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	protected static void drawGame(AbstractGame g, Graphics2D g2) {
           
		Player p1 = (Player) g.getPlayer1();
		Player p2 = (Player) g.getPlayer2();

		// drap p1 cells
		for (Cell c : p1.getCells().values()) {
			Color cc = p1.getColor();
			if (c.getStatus() == Globals.CELL_CAPTURED) {
				cc = Tools.fade(cc);
			}
			drawCell(c, cc, g2);
		}

		// drap p2 cells
		for (Cell c : p2.getCells().values()) {
			Color cc = p2.getColor();
			if (c.getStatus() == Globals.CELL_CAPTURED) {
				cc = Tools.fade(cc);
			}
			drawCell(c, cc, g2);
		}

		// draw points
		for (Point p : JKemik.getGame().getCollection().values()) {
			if (p.getId() == p1.getId()) {
				if (p.getStatus() == Globals.POINT_CAPTURED) {
					Artist.drawCircle(p, Tools.fade(p1.getColor()),
							Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
							Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				} else {
					Artist.drawCircle(p, p1.getColor(), Grid.HALF_DIAMETER,
							Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				}
			}

			if (p.getId() == p2.getId()) { // &&
				if (p.getStatus() == Globals.POINT_CAPTURED) {
					Artist.drawCircle(p, Tools.fade(p2.getColor()),
							Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
							Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				} else {
					Artist.drawCircle(p, p2.getColor(), Grid.HALF_DIAMETER,
							Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				}
			}
		}
		if (Grid.manualc) {
			for (Point p : g.getCurrentP().getSelected()) {

				Artist.drawCircle(p, g.getCurrentP().getFadedColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
//				Artist.drawCursor(p, Grid.gridLineStroke, Grid.half_squareSize,
//						Grid.gridLineCol, g2);
			}
		}
        
	}
	/**
	 * @param Arraylist
	 *            of pl1 cells, pl1, pl2
	 * @return void Draws a cell with all its content.
	 * */
	protected static boolean drawCell(Cell c, Color col, Graphics2D g2) {
		try {
			// for (Cell c : cells) {
			if (c.getStatus() == Globals.CELL_EMPTY) {
				System.out.println(" This cell is empty");
				return false;
			}

			ArrayList<Point> contour = c.getCellContour();
			// Color cellc = Artist.getCellColor(c);
			Color capturedc = Artist.getCellCapturesColor(c);

			/* draw cell contour */
			Artist.drawLine(contour.get(0), contour.get(contour.size() - 1),
					Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, col, g2);
			for (int i = 0; i < contour.size() - 1; i++) {

				Artist.drawLine(contour.get(i), contour.get(i + 1),
						Grid.gridLineStroke + Grid.CURSOR_VARIANT_STROKE, col,
						g2);
			}
			// circles in contour
			for (int i = 0; i < contour.size(); i++) {
				Artist.drawCircle(contour.get(i), col, Grid.HALF_DIAMETER,
						Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);
//				Artist.drawCursor(contour.get(i), Grid.gridLineStroke,
//						Grid.half_squareSize, Grid.gridLineCol, g2);
			}

			// cells inside this cell
			for (Point p : c.getAreaIncell()) {
			
				if (p.getStatus() == Globals.POINT_CAPTURED) {
					Artist.drawCircle(p, Tools.fade(capturedc),
							Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
							Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				}

				if (p.getStatus() == Globals.POINT_DEAD) {
					Artist.drawCircle(p,BoardFrame.BOARD_COLOR,
							Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
							Grid.gridLineStroke, g2);
//					Artist.drawCursor(p, Grid.gridLineStroke,
//							Grid.half_squareSize, Grid.gridLineCol, g2);
				}
			}

			for (Cell in : c.getCellsInCell().values()) {
				drawCell(in, Tools.fade(capturedc), g2);
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
		return true;
	}

	protected static Color getCellColor(Cell c) {
		if (c.getId() == JKemik.getGame().getCurrentP().getId()) {
			return JKemik.getGame().getCurrentP().getColor();
		}
		return JKemik.getGame().getGuest().getColor();
	}

	protected static Color getCellCapturesColor(Cell c) {
		if (c.getId() == JKemik.getGame().getCurrentP().getId()) {
			return JKemik.getGame().getGuest().getColor();
		}
		return JKemik.getGame().getCurrentP().getColor();
	}

	protected static void unDraw(Point p, Graphics2D g2) {

		try {
			double px = p.getXC();
			double py = p.getYC();

			// Artist.drawCircle(new Point(px, py,
			// JKemik.game.getCurrentP().getId()),
			// BoardFrame.BOARD_COLOR, Grid.HALF_DIAMETER,
			// Grid.CIRCLE_DIAMETER, Grid.gridLineStroke, g2);

			Ellipse2D.Double circle = new Ellipse2D.Double(px
					- Grid.HALF_DIAMETER, py - Grid.HALF_DIAMETER,
					Grid.CIRCLE_DIAMETER, Grid.CIRCLE_DIAMETER);
			g2.setColor(BoardFrame.BOARD_COLOR);
			g2.setStroke(new BasicStroke(Grid.gridLineStroke + 1));
			g2.fill(circle);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.draw(circle);
			g2.setColor(Grid.gridLineCol);

//			Artist.drawCursor(p, Grid.squareFadeVariant, Grid.half_squareSize,
//					Tools.fade(BoardFrame.BOARD_COLOR,
//							Globals.SQR_LINE_SHADE_PERCENT), g2);
//			drawLongCursor(p, Grid.gridLineStroke, Grid.gridLineCol, g2);

		} catch (Exception e) {
			System.err.println("Error in unDraw: " + e.getMessage());
		}
	}

	protected static void drawCursor(Point p, int stroke, double h_sqr,
			Color c, Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));

		// Highlight of visited intersection
//		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//		 RenderingHints.VALUE_ANTIALIAS_ON);
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + h_sqr, p.getXC(), p
				.getYC() - h_sqr));
		g2.draw(new Line2D.Double(p.getXC() - h_sqr, p.getYC(), p.getXC()
				+ h_sqr, p.getYC()));
		g2.setColor(c);
	}

	protected static void drawLongCursor(Point p, int stroke, Color c,
			Graphics2D g2) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.draw(new Line2D.Double(p.getXC(), p.getYC() + Grid.squareSize, p
				.getXC(), p.getYC() - Grid.squareSize));
		g2.draw(new Line2D.Double(p.getXC() - Grid.squareSize, p.getYC(), p
				.getXC() + Grid.squareSize, p.getYC()));
		g2.setColor(JKemik.getGame().getCurrentP().getColor());
	}
        protected static void drawSelection(ArrayList<Point> list, Point newPoint, Color fade,Graphics2D g2){
            try{
                if(list.isEmpty()){
                    return;
                }

                Point last = list.get(0);
                for(int i = 0; i < list.size(); i++){
                    if(i + 1 >= list.size()){
                        last = list.get(i);
                        break;
                    }
                   
                    Artist.drawLine(last, list.get(i + 1), gridLineStroke
                                    + Grid.CURSOR_VARIANT_STROKE, fade, g2);
                    Artist.drawCircle(last, fade, Grid.HALF_DIAMETER,
                                    Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
//                    Artist.drawCursor(last, gridLineStroke,
//                                    Grid.half_squareSize, Grid.gridLineCol, g2);
                    Artist.drawCircle(list.get(i + 1), fade, Grid.HALF_DIAMETER,
                                    Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
//                    Artist.drawCursor(list.get(i + 1), gridLineStroke,
//                                        Grid.half_squareSize, Grid.gridLineCol, g2);
                    last = list.get(i + 1);
                }
                list.add(newPoint);
                Artist.drawLine(last, newPoint, gridLineStroke
                                    + Grid.CURSOR_VARIANT_STROKE, fade, g2);
                Artist.drawCircle(last, fade, Grid.HALF_DIAMETER,
                                Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
//                Artist.drawCursor(last, gridLineStroke,
//                                Grid.half_squareSize, Grid.gridLineCol, g2);
                Artist.drawCircle(newPoint, fade, Grid.HALF_DIAMETER,
                                Grid.CIRCLE_DIAMETER, gridLineStroke, g2);
//                Artist.drawCursor(newPoint, gridLineStroke,
//                                        Grid.half_squareSize, Grid.gridLineCol, g2);
                
            }catch(Exception e){
            }
        }
	protected static void unDrawSelection(ArrayList<Point> contour,
			Graphics2D g2) {
		AbstractGame game = JKemik.getGame();
		try {
			/* Erase last line */
			int index = contour.size() - 1;
			Point lastp, before_lastp;
			if (contour.size() > 1) {
				lastp = contour.get(index);
				before_lastp = contour.get(index - 1);
			} else if (contour.size() == 1) {
				lastp = contour.get(index);
				drawCircle(lastp, game.getCurrentP().getColor(),
						Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
						Grid.gridLineStroke, g2);
//				drawCursor(lastp, Grid.gridLineStroke, Grid.half_squareSize,
//						Grid.gridLineCol, g2);
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
//			drawCursor(lastp, Grid.gridLineStroke, Grid.half_squareSize,
//					Grid.gridLineCol, g2);
			drawCircle(before_lastp, game.getCurrentP().getFadedColor(),
					Grid.HALF_DIAMETER, Grid.CIRCLE_DIAMETER,
					Grid.gridLineStroke, g2);
//			drawCursor(before_lastp, Grid.gridLineStroke, Grid.half_squareSize,
//					Grid.gridLineCol, g2);

			if (!contour.isEmpty()) {
				game.setLastp(before_lastp);
				contour.remove(index);
			}

		} catch (NullPointerException e) {
			System.out.println("In drawCell: " + e.getMessage());
		}
	}
 
}
