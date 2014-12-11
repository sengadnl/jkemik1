/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import utilities.Globals;

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
	//private String str_p1_c = "", str_p2_c = "";
	//private String gType = "";
	
	private Color p1_c;
	private Color p2_c;
        //private String theme;
	private Dimension dimension; // = Toolkit.getDefaultToolkit().getScreenSize();


	public GTemplate() {
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.P1_name = "P1";
		this.P2_name = "P2";
                System.err.println("Creating a new game template...");
	}
        public void setTheme(STemplate t){
            String str = t.getTheme();
            switch (str) {
                case Globals.ORIGINS:
                    this.p1_c = Globals.ORIGINE_COLOR[0];
                    this.p2_c = Globals.ORIGINE_COLOR[1];
                    break;
                case Globals.JKEMIK:
                    this.p1_c = Globals.JKEMIK_COLOR[0];
                    this.p2_c = Globals.JKEMIK_COLOR[1];
                    break;
                case Globals.GEEKY:
                    this.p1_c = Globals.GEECKY_COLOR[0];
                    this.p2_c = Globals.GEECKY_COLOR[1];
                    break;
                case Globals.GO:
                    this.p1_c = Globals.GO_COLOR[0];
                    this.p2_c = Globals.GO_COLOR[1];
                    break;
                default: 
                    this.p1_c = Globals.CLASSIC_COLOR[0];
                    this.p1_c = Globals.CLASSIC_COLOR[1];
                    break;
            }
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



	public void setP1_name(String P1_name) {
		//System.out.println("----------" + P1_name);
		this.P1_name = P1_name;
	}

	public void setP2_name(String P2_name) {
		//System.out.println("-----------" + P2_name);
		this.P2_name = P2_name;
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
            System.err.println("Setting P1 color..");
		p1_c = p1C;
	}
	/**
	 * @param p2C the p2_c to set
	 */
	public void setP2_c(Color p2C) {
            System.err.println("Setting P2 color..");
		p2_c = p2C;
	}
	public String toString() {
		return "" + P1_name + " " + P2_name + "\nC1: " + p1_c + "\nC2: " + p2_c;
	}
}
