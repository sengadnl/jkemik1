/**
 *
 */
package view;

import java.awt.*;

import javax.swing.*;

import utilities.Tools;
import api.AbstractPlayer;


/**
 * @author dalet
 * 
 */
public class PlayerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int HEIGHT = 100;
	private int WIDTH = 100;

	private JPanel holder;

	private JLabel player_name;

	// private JLabel plotted;
	private JLabel plottedv;

	// private JLabel captured;
	private JLabel capturedv;

	// private JLabel cells;
	private JLabel cellsv;

	// private JLabel plotted;
	private JLabel captCellsv;

	private JLabel scorev;
	private final int FONT_SIZE = 11;
	private final int LINE_BORDER_STOKE = 6;
	private final double LB_FADE_VAR = .60;
	private final int FADE_TH = 10;// fade threshold

	public PlayerPanel() {
		
		setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

		System.out.println("PlayerPanel -- Width = " + this.WIDTH
				+ " Height = " + this.HEIGHT);
		createPlayerPanel();
	}
	/**
	 * constructor for BoardFrame*/
	public PlayerPanel(int w, int h) {
		 this.WIDTH = w;
		 this.HEIGHT = h;
		setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
		System.out.println("PlayerPanel -- Width = " + this.WIDTH
				+ " Height = " + this.HEIGHT);
		createPlayerPanel();
	}

	public JPanel getHolder() {
		return holder;
	}
	public void setHolder(JPanel holder) {
		this.holder = holder;
	}
	public void createPlayerPanel() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		this.holder = new JPanel();
		this.holder.setBackground(Color.BLACK);
		this.holder.setPreferredSize(new Dimension(this.WIDTH,
				(int) (.7 * this.HEIGHT)));

		this.holder.setLayout(new GridLayout(6, 2));

		setBorder(BorderFactory.createLineBorder(Color.WHITE, LINE_BORDER_STOKE));
		this.player_name = new JLabel("PName");
		this.player_name.setHorizontalAlignment(SwingConstants.CENTER);

		plottedv = new JLabel();
		capturedv = new JLabel();
		cellsv = new JLabel();
		captCellsv = new JLabel();
		scorev = new JLabel();

		Font pfont = new Font("Arial", Font.BOLD, this.FONT_SIZE + 1);
		this.player_name.setFont(pfont);

		plottedv.setForeground(new Color(204, 255, 100));
		Font pttvfont = new Font("Arial", plottedv.getFont().getStyle(),
				this.FONT_SIZE);
		plottedv.setFont(pttvfont);

		capturedv.setForeground(new Color(255, 51, 100));
		Font cvfont = new Font("Arial", capturedv.getFont().getStyle(),
				this.FONT_SIZE);
		capturedv.setFont(cvfont);

		cellsv.setForeground(new Color(179, 100, 255));
		Font clvfont = new Font("Arial", cellsv.getFont().getStyle(),
				this.FONT_SIZE);
		cellsv.setFont(clvfont);

		captCellsv.setForeground(new Color(255, 150, 100));
		Font cplvfont = new Font("Arial", captCellsv.getFont().getStyle(),
				this.FONT_SIZE);
		captCellsv.setFont(cplvfont);

		scorev.setForeground(new Color(0, 255, 0));
		Font scvfont = new Font("Arial", scorev.getFont().getStyle(),
				this.FONT_SIZE);
		scorev.setFont(scvfont);

		 final JLabel plotted = new JLabel(" Plt Pt: ");
		final JLabel captured = new JLabel(" Cp Pt: ");
		final JLabel cptcells = new JLabel(" Cp Cll: ");
		   final JLabel cells = new JLabel(" Cll: ");
		   final JLabel score = new JLabel(" Scr: ");


		plotted.setForeground(new Color(255, 204, 51));
		Font pttfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		plotted.setFont(pttfont);

		captured.setForeground(new Color(255, 204, 51));
		Font cfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		captured.setFont(cfont);

		cells.setForeground(new Color(255, 204, 51));
		Font clfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		cells.setFont(clfont);

		cptcells.setForeground(new Color(255, 204, 51));
		Font ctcellsf = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		cptcells.setFont(ctcellsf);

		score.setForeground(new Color(250, 104, 60));
		Font scfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		score.setFont(scfont);

		// add(new JLabel("  "));
		this.holder.add(plotted);
		this.holder.add(plottedv);
		this.holder.add(captured);
		this.holder.add(capturedv);
		this.holder.add(cells);
		this.holder.add(cellsv);
		this.holder.add(cptcells);
		this.holder.add(captCellsv);
		this.holder.add(score);
		this.holder.add(scorev);
		add(add(this.player_name), BorderLayout.NORTH);
		add(add(this.holder), BorderLayout.SOUTH);
		initPanel();

	}

	/**
	 * @return the player
	 */
	public JLabel getPlayer() {
		return this.player_name;
	}

	/**
	 * @param player_name
	 *            the player to set
	 */
	public void setPlayer(String pname) {
		this.player_name.setText(pname);
	}


	public void updatePlayerPanel(AbstractPlayer abstractPlayer) {
		try {
			plottedv.setText("" + abstractPlayer.getPloted().size());
			capturedv.setText("" + abstractPlayer.getCapturedDots().size());
			cellsv.setText("" + abstractPlayer.getCells().size());
			captCellsv.setText("" + abstractPlayer.getCapturedCells().size());
			scorev.setText("" + abstractPlayer.getScore());
		} catch (Exception e) {
			System.out.println(" In PlayerPanel " + e.getMessage());
		}
	}

	public void initPanel() {
		plottedv.setText("0");
		capturedv.setText("0");
		cellsv.setText("0");
		captCellsv.setText("0");
		scorev.setText("0");

	}

	public void initPanelForNewGame(String pname, Color pcolor) {
		try {
			setBorder(BorderFactory.createLineBorder(Tools.fade(pcolor, FADE_TH, LB_FADE_VAR), LINE_BORDER_STOKE));
			pname = pname.toUpperCase();
			setPlayer("  " + pname);
			setBackground(Tools.fade(pcolor, FADE_TH, LB_FADE_VAR));
			this.player_name.setForeground(pcolor);
			plottedv.setText("0");
			capturedv.setText("0");
			cellsv.setText("0");
			captCellsv.setText("0");
			scorev.setText("0");
		} catch (Exception e) {
			System.err.println(" in PlayerPanel " + e.getMessage());
		}
	}

	/**
	 * @return the plottedv
	 */
	public JLabel getPlottedv() {
		return plottedv;
	}

	/**
	 * @param plottedv
	 *            the plottedv to set
	 */
	public void setPlottedv(String str) {
		plottedv.setText(str);
	}

	/**
	 * @return the capturedv
	 */
	public JLabel getCapturedv() {
		return capturedv;
	}

	/**
	 * @param capturedv
	 *            the capturedv to set
	 */
	public void setCapturedv(String str) {
		capturedv.setText(str);
	}

	/**
	 * @return the cellsv
	 */
	public JLabel getCellsv() {
		return cellsv;
	}

	/**
	 * @param cellsv
	 *            the cellsv to set
	 */
	public void setCellsv(String str) {
		cellsv.setText(str);
	}

	/**
	 * @return the captCellsv
	 */
	public JLabel getCaptCellsv() {
		return captCellsv;
	}

	/**
	 * @param captCellsv
	 *            the captCellsv to set
	 */
	public void setCaptCellsv(String str) {
		captCellsv.setText(str);
	}

	/**
	 * @return the scorev
	 */
	public JLabel getScorev() {
		return scorev;
	}

	/**
	 * @param scorev
	 *            the scorev to set
	 */
	public void setScorev(String str) {
		scorev.setText(str);
	}

	// public void setPlayerName(String name) {
	// p.setName(name);
	// }

	public void setLabelColor(Color c) {
		this.player_name.setForeground(c);
	}
}
