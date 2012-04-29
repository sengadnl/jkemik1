/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;

/**
 * 
 * @author dalet
 */
public class GTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String P1_name, P2_name;
	private String str_p1_c = "", str_p2_c = "";
	private String gType = "";
	
	private Color p1_c;
	private Color p2_c;
	private double G_size;
	private Dimension dimension; // = Toolkit.getDefaultToolkit().getScreenSize();
	private final double GRID_PERCENT = 0.8; //
	private final double VERY_LARGE_SCALAR = 128;
	private final double LARGE_SCALAR = 16;
	private final double MIDIUM_SCALAR = 32;
	private final double SMALL_SCALAR = 64;
	

	public GTemplate() {
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.P1_name = "p1";
		this.P2_name = "p2";
		this.p1_c = new Color(0, 250, 0);
		this.p2_c = new Color(250, 0, 0);
		this.G_size = ((this.dimension.getWidth() * this.GRID_PERCENT) / this.MIDIUM_SCALAR);
	}

	/**
	 * @return the str_p1_c
	 */
	public String getStr_p1_c() {
		return this.str_p1_c;
	}

	/**
	 * @param strP1C the str_p1_c to set
	 */
	public void setStr_p1_c(String strP1C) {
		this.str_p1_c = strP1C;
	}

	/**
	 * @return the str_p2_c
	 */
	public String getStr_p2_c() {
		return this.str_p2_c;
	}

	/**
	 * @param strP2C the str_p2_c to set
	 */
	public void setStr_p2_c(String strP2C) {
		this.str_p2_c = strP2C;
	}

	/**
	 * @return the gType
	 */
	public String getgType() {
		return this.gType;
	}

	/**
	 * @param gType the gType to set
	 */
	public void setgType(String gType) {
		this.gType = gType;
	}
	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return this.dimension;
	}

	/**
	 * @param dimension
	 *            the dimension to set
	 */
	public void setDimension(Dimension d) {
		this.dimension = d;
	}

	public void setG_size(double G_size) {
		this.G_size = G_size;
	}

	public void setP1_name(String P1_name) {
		System.out.println("----------" + P1_name);
		this.P1_name = P1_name;
	}

	public void setP2_name(String P2_name) {
		System.out.println("-----------" + P2_name);
		this.P2_name = P2_name;
	}

	public void setGridSize(String Grid_size) {

		Grid_size = Grid_size.toLowerCase();

		if (Grid_size.equals("64x40")) {
			this.G_size = ((dimension.getWidth() * GRID_PERCENT) / LARGE_SCALAR);
		}

		if (Grid_size.equals("32x20")) {
			this.G_size = ((dimension.getWidth() * GRID_PERCENT) / MIDIUM_SCALAR);
		}

		if (Grid_size.equals("16x10")) {
			this.G_size = ((dimension.getWidth() * GRID_PERCENT) / SMALL_SCALAR);
		}

		if (Grid_size.equals("8x5")) {
			this.G_size = ((dimension.getWidth() * GRID_PERCENT) / VERY_LARGE_SCALAR);
		}
	}
	public String gridSizeToString(){
		if(this.G_size == (double)8){
			return "8x5";
		}else if(this.G_size == (double)16){
			return "16x10";
		}else if(this.G_size == (double)32){
			return "32x20";
		}else{
			return "64x40";
		}
	}
	public String gameTypeToString(){
		if(this.gType.equals("H vs H")){
			return "H vs H";
		}else if(this.gType.equals("H vs C")){
			return "H vs C";
		}else{
			return "LAN";
		}
	}

	public double getG_size() {
		return G_size;
	}

	public void setTheme(Color p1,Color p2) {
		this.p1_c = p1;
		this.p2_c = p2;
	}

	public String getP1_name() {
		return P1_name;
	}

	public String getP2_name() {
		return P2_name;
	}

	public Color getP1_c() {
		return p1_c;
	}

	public Color getP2_c() {
		return p2_c;
	}

	/**
	 * @param p1C the p1_c to set
	 */
	public void setP1_c(Color p1C) {
		p1_c = p1C;
	}
	/**
	 * @param p2C the p2_c to set
	 */
	public void setP2_c(Color p2C) {
		p2_c = p2C;
	}
	public String toString() {
		return " " + P1_name + " " + P2_name + " " + G_size;
	}
}
