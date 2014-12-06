/**
 * 
 */
package view;

import java.awt.*;
import javax.swing.*;

/**
 * @author dalet
 * 
 */
public class RotateColor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int BOARD_SIZE = 3;
	private int W = 0;
	private int H = 0;
	private Color[] colors;
	private int colorIndex = 0;// specifies which color to pick
	private boolean changeFlag = false;
	private final Dimension DIMENSION = new Dimension((int) (this.W * .8),
			(int) (this.H * .2));

	// private Color bg;

	public RotateColor(int w, int h) {
		this.W = w;
		this.H = h;
		setPreferredSize(new Dimension(this.W, this.H));
		loadColors();
		setBackground(this.colors[this.colorIndex]);
//		this.setBorder(BorderFactory
//				.createLineBorder(Tools.fade(this.colors[this.colorIndex]), 2));
	}

	/**
	 * @return the changeFlag
	 */
	public boolean isChangeFlag() {
		return this.changeFlag;
	}

	/**
	 * @param changeFlag
	 *            the changeFlag to set
	 */
	public void setChangeFlag(boolean changeFlag) {
		this.changeFlag = changeFlag;
	}

	public Color getPanelBGC() {
		return this.getBackground();
	}
        //TODO check if it is working properly
	public void rotateColor(JPanel panel) {
		this.colorIndex++;
		if (this.colorIndex > this.BOARD_SIZE - 1) {
			this.colorIndex = 0;
			panel.setBackground(this.colors[this.colorIndex]);
		} else {
			panel.setBackground(this.colors[this.colorIndex]);
		}
	}

	public void rotateColor() {
		this.colorIndex++;
		if (this.colorIndex > this.BOARD_SIZE - 1) {
			this.colorIndex = 0;
			this.setBackground(this.colors[this.colorIndex]);
		}
		this.setBackground(this.colors[this.colorIndex]);
	}

	/**
	 * @param an
	 *            integer specifying the position of the color to rotate to
	 * @return void
	 */
	public Color rotateColor(int i) {
		this.setBackground(this.colors[i]);
		return this.colors[i];
	}

	/**
	 * */
	public void rotateColor(Color c) {
		for (int i = 0; i < colors.length; i++) {
			if (c.equals(colors[i])) {
				this.setBackground(colors[i]);
			}
		}
	}

	protected Color[] loadColors() {
		this.colors = new Color[this.BOARD_SIZE];
		this.colors[0] = new Color(255, 255, 255);
		this.colors[1] = new Color(233, 71, 153);
		this.colors[2] = new Color(233, 100, 160);
		return this.colors;
	}
/**
 * col must be == BOARD_SIZE*/
	protected boolean setArrayColors(Color[] col) {

		if (colors.length == col.length) {
			this.colors = new Color[col.length];
			for (int i = 0; i < col.length; i++) {
				this.colors[i] = col[i];
			}
			return true;
		}
		return false;
	}

	protected Color[] loadColors(Color[] col) {
		this.colors = new Color[col.length];
		for (int i = 0; i < col.length; i++) {
			this.colors[i] = col[i];
		}
		return this.colors;
	}

	/**
	 * /**
	 * 
	 * @return the colors
	 */
	public Color[] getColors() {
		return this.colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	/**
	 * @return the colorIndex
	 */
	public int getColorIndex() {
		return this.colorIndex;
	}

	/**
	 * @param colorIndex
	 *            the colorIndex to set
	 */
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	/**
	 * @return the boardSize
	 */
	public int getBoardSize() {
		return this.BOARD_SIZE;
	}

	/**
	 * @return the w
	 */
	public int getW() {
		return this.W;
	}

	/**
	 * @return the h
	 */
	public int getH() {
		return this.H;
	}

	/**
	 * @return the dIMENSION
	 */
	public Dimension getDIMENSION() {
		return this.DIMENSION;
	}

	public static void main(String[] args) {
		JPanel dlog = new RotateColor(200, 20);
		JFrame frame = new JFrame();
		frame.setSize(100, 60);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(dlog);
		frame.setVisible(true);
	}

}
