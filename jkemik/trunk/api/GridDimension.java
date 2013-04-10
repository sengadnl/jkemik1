package api;

import java.awt.Dimension;

public class GridDimension {
	/**
	 * This class take a Dimension in squares of a board 
	 * Example: 30X20 where 30 is the number of square in a row and 
	 * 20 is the number of squares in a column*/
	private Dimension squares;
	private int sqrSize = 0;
	
	public GridDimension(Dimension squares,int sqrSize) {
		super();
		this.squares = squares;
		this.sqrSize = sqrSize;
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

	/*return the pixels dimensions of the board*/
	public Dimension getPixelDimension(){
		int w = (int)(this.squares.getWidth() * this.sqrSize);
		int h = (int)(this.squares.getHeight() * this.sqrSize);
		return new Dimension(w,h);
	}

	public String toString(){
		return "\nBoard Container: " + getPixelDimension() + " Board : " + this.squares + " Square: " + getSqrSize(); 
	}
}
