/**
 * 
 */
package api;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Junes
 * 
 */
public class Player extends AbstractPlayer implements Comparable<Player>,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param color
	 * @param name
	 * @param score
	 */
	public Player(Color color, String name) {
		super(color, name);

	}

//	public String toString() {
//		return "" + this.getName() + "\nSelected: " + 
//		this.getSelected().size() + "\nPlotted: "
//				+ this.getPloted().size() + "\ncapturedDots: " +
//						this.getCapturedDots().size() + "\n\n";
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Player o) {
		if (this.getColor().equals(o.getColor())) {
			return 0;
		}
		return 1;
	}

}
