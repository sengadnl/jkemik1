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
	private String str_p1_c = "", str_p2_c = "";
	//private String gType = "";
	
	private Color p1_c;
	private Color p2_c;
        //private String theme;
	private Dimension dimension; // = Toolkit.getDefaultToolkit().getScreenSize();


	public GTemplate(STemplate t) {
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.P1_name = "P1";
		this.P2_name = "P2";
                setTheme(t);
                //System.err.println("Creating a new game template...");
	}
        private void setTheme(STemplate t){
            switch (t.getTheme()) {
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
                default: 
                    this.p1_c = Globals.CLASSIC_COLOR[0];
                    this.p1_c = Globals.CLASSIC_COLOR[1];
                    break;
            }
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
		p1_c = p1C;
	}
	/**
	 * @param p2C the p2_c to set
	 */
	public void setP2_c(Color p2C) {
		p2_c = p2C;
	}
	public String toString() {
		return " " + P1_name + " " + P2_name + " ";
	}
}
