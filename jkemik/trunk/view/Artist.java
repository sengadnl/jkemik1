package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import utilities.Tools;
import api.Point;

public class Artist {

	public Artist(double squareSize){
	
	}
	
	public static void drawGridLine(Point from, Point to, Color c, Graphics2D g2) {
		g2.setColor(c);
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));
		g2.setColor(Tools.fade(c));
		
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));
	}

	public static void drawLine(Point from, Point to, int stroke, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) (from.getXC()), (int) (from.getYC()), (int) (to
				.getXC()), (int) (to.getYC()));

	}

	public static void drawLine(double x1, double y1, double x2, double y2,
			int stroke, Color c, Graphics2D g2) {
		g2.setStroke(new BasicStroke(stroke));
		g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}
}
