/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

//import api.AIGame;
//import api.AbstractGame;
//import controler.JKemik;

/**
 * @author dalet
 * 
 */
public class GridStatus extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel deadGridInPercent, freeGridInPercent, deadCount,
			tot_play_count, p1count, p2count;

	// private JLabel gridSize;

	private final int FONT_SIZE = 11;

	public GridStatus(int w, int h) {

		setPreferredSize(new Dimension(w, h));
		setOpaque(true);
		// setBackground(BoardFrame.BOARD_COLOR);
		// setBorder(BorderFactory.createLineBorder(BoardFrame.BOARD_COLOR, 1));
		setLayout(new GridLayout(2, 5));

		// this.gridSize = new JLabel(0 + " x " + 0); // Assuming the GridPanel
		// already exists
		this.deadCount = new JLabel();
		this.deadGridInPercent = new JLabel();
		this.freeGridInPercent = new JLabel();
		this.tot_play_count = new JLabel();
		this.p1count = new JLabel("0");
		this.p2count = new JLabel("0");

		this.deadCount.setForeground(new Color(204, 255, 100));
		Font pttvfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		this.deadCount.setFont(pttvfont);

		this.deadGridInPercent.setForeground(new Color(255, 51, 100));
		Font cvfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		this.deadGridInPercent.setFont(cvfont);

		this.freeGridInPercent.setForeground(new Color(179, 100, 255));
		Font clvfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		this.freeGridInPercent.setFont(clvfont);

		this.tot_play_count.setForeground(Color.YELLOW);
		Font gsfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		this.tot_play_count.setFont(gsfont);

		final JLabel dc = new JLabel("  Tot plots:");
		dc.setForeground(new Color(255, 255, 255));
		Font pttfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		dc.setFont(pttfont);

		final JLabel dgp = new JLabel("  Occupied:");
		dgp.setForeground(new Color(255, 255, 255));
		Font cfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		dgp.setFont(cfont);

		final JLabel fgp = new JLabel("  Free:");
		fgp.setForeground(new Color(255, 255, 255));
		Font clfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		fgp.setFont(clfont);

		final JLabel gs = new JLabel("  Moves: ");
		gs.setForeground(new Color(255, 255, 255));
		Font gfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		gs.setFont(gfont);
		
		init();

		//First row
		add(this.p1count);
		add(gs);
		add(this.tot_play_count);
		add(dgp);
		add(this.deadGridInPercent);
		
		//Second row
		add(this.p2count);
		add(dc);
		add(this.deadCount);
		add(fgp);
		add(this.freeGridInPercent);
	}

	public void init() {
		setDeadCountV("0");
		setDeadGridInPercentV(0);
		setFreeGridInPercentV(0);
		setTot_play_count(0);
		setP1count(0);
		setP2count(0);
	}

	public JLabel getTot_play_count() {
		return tot_play_count;
	}

	public void setTot_play_count(int player1_count) {
		this.tot_play_count.setText("" + player1_count);
	}

	/**
	 * @return the deadCount
	 */
	public JLabel getDeadCount() {
		return deadCount;
	}

	/**
	 * @param deadCount
	 *            the deadCount to set
	 */
	public void setDeadCountV(String str) {

		this.deadCount.setText(str);
	}

	/**
	 * @return the deadGridInPercent
	 */
	public JLabel getDeadGridInPercent() {
		return deadGridInPercent;
	}

	/**
	 * @param deadGridInPercent
	 *            the deadGridInPercent to set
	 */
	public void setDeadGridInPercentV(double num) {
		DecimalFormat format = new DecimalFormat("##.##");
		String str = format.format(num);
		this.deadGridInPercent.setText(str + " %");
	}

	/**
	 * @return the freeGridInPercent
	 */
	public JLabel getFreeGridInPercent() {
		return freeGridInPercent;
	}

	/**
	 * @param freeGridInPercent
	 *            the freeGridInPercent to set
	 */
	public void setFreeGridInPercentV(double num) {
		DecimalFormat format = new DecimalFormat("##.##");
		String str = format.format(num);
		this.freeGridInPercent.setText(str + " %");
	}

	public JLabel getP1count() {
		return p1count;
	}

	public void setP1count(int p1count) {
		this.p1count.setText(" " + p1count);
	}

	public JLabel getP2count() {
		return p2count;
	}

	public void setP2count(int p2count) {
		this.p2count.setText(" " + p2count);
	}

}
