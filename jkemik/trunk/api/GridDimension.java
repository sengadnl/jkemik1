package api;

import java.awt.Dimension;
import java.io.Serializable;

public class GridDimension implements Comparable<GridDimension>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This class take a Dimension in squares of a board Example: 30X20 where 30
	 * is the number of square in a row and 20 is the number of squares in a
	 * column
	 */
	private Dimension squares = new Dimension(0, 0);
	private int sqrSize = 0;

	public GridDimension(Dimension squares, int sqrSize) {
		super();
		this.squares = squares;
		this.sqrSize = sqrSize;
	}
        public int gridHeight(){
		//int height = (int) this.squares.getHeight() + 1;
            return (int) (this.squares.getHeight() * this.sqrSize);
        }
        public int gridWidth(){
             return (int) (this.squares.getWidth() * this.sqrSize);
        }

	/**
	 * @param none
	 * @return Board dimensions in terms of play position. This is not to be
	 *         used in any GUI dimension representation. It represents how many
	 *         positions are available for play
	 */
	public Dimension positionsDimension() {
		int width = (int) this.squares.getWidth() + 1;
		int height = (int) this.squares.getHeight() + 1;
		return new Dimension(width, height);
	}

	/**
	 * @param none
	 * @return Number of positions
	 */
	public int positions() {
		int width = (int) positionsDimension().getWidth();
		int height = (int) positionsDimension().getHeight();
		return (width * height);
	}

	public int getSqrSize() {
		return sqrSize;
	}

	public void setSqrSize(int sqrSize) {
		this.sqrSize = sqrSize;
	}

	public Dimension getDimensionSquares() {
		return squares;
	}

	public void setDimensionSquares(Dimension squares) {
		this.squares = squares;
	}

	/* return the pixels dimensions of the board */
	public Dimension getPixelDimension() {
		int w = 0, h = 0;
		try {
			w = (int) (this.squares.getWidth() * this.sqrSize);
			h = (int) (this.squares.getHeight() * this.sqrSize);
		} catch (NullPointerException e) {
			System.out.println("GridDimention -> getPixelDimension: "
					+ e.getMessage());
		}
		return new Dimension(w, h);
	}

	public String toString() {
		return "" + (this.squares.width + 1) + "x" + (this.squares.height + 1);
	}

	@Override
	public int compareTo(GridDimension o) {
		if (this.squares.width > o.squares.width) {
			return 1;
		} else if (this.squares.width < o.squares.width) {
			return -1;
		} else {
			return 0;
		}
	}
}
