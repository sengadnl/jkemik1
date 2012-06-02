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
public class RotateLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] list;
	private int index = 0;
	private Color bg = new Color(255,255,255);

	public RotateLabel(String[] list) {
		this.list = list;
		setText(list[0]);
		setForeground(this.bg);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void newSetText(String str) {
		for (int i = 0; i < this.list.length; i++) {
			if (this.list[i].equals(str)) {
				setText(str);
			}
		}
	}

	public String getActiveLabel() {
		return this.list[this.index];
	}
	
	public void rotateLabel() {
		this.index++;
		if (this.index >= this.list.length) {
			this.index = 0;
			this.setText(this.list[this.index]);
		} else {
			this.setText(this.list[this.index]);
		}
	}

	public void highlight() {
		setForeground(new Color(250,0,250));
	}

	public void resetBGC() {
		setForeground(this.bg);
	}

	/**
	 * @return the list
	 */
	public String[] getList() {
		return this.list;
	}

	/**
	 * @param list to set
	 */
	public void setList(String[] list) {
		this.list = list;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
