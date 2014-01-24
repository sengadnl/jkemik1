/**
 * 
 */
package api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 * @author dalet
 * 
 */
public class Circle extends AbstractPoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color color;

	private Ellipse2D.Double circle;
	private double d;
	private double Diameter = 10.0;

	public Circle(double x, double y,int id, double d, Color color) {
		super(x, y, id);
		this.color = color;
		this.d = d;
		circle = new Ellipse2D.Double(x - (Diameter / 2), y - (Diameter / 2),
				Diameter, Diameter);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/* used for testing */
	// public void paintComponent(Graphics g){
	// super.paintComponent(g);
	// g2 = (Graphics2D)g;
	// circle = new Ellipse2D.Double(x,y,d,d);
	// g2.draw(circle);
	// g2.setColor(color);
	// //setBackground(color);
	// g2.fill(circle);
	// }
	public void drawCircle(Graphics2D g) {
		g.setColor(color);
		g.fill(circle);
		g.draw(circle);
	}

	public void drawCircle(Graphics2D g, Color c) {
		g.setColor(c);
		g.fill(circle);
		g.draw(circle);
	}

	/**
	 * @return the d
	 */
	public double getD() {
		return d;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public void setD(double d) {
		this.d = d;
	}

	public double getDiameter() {
		return Diameter;
	}

	public void setDiameter(double diameter) {
		Diameter = diameter;
	}

	public void render() {
		// repaint();
	}

}
