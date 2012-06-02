/**
 *
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import api.Player;

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
	private final int FONT_SIZE = 9;

	public PlayerPanel() {
		
		setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
		// setPreferredSize(new Dimension(w, h));
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
		// setPreferredSize(new Dimension(w, h));
		System.out.println("PlayerPanel -- Width = " + this.WIDTH
				+ " Height = " + this.HEIGHT);
		createPlayerPanel();
	}

	public void createPlayerPanel() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		this.holder = new JPanel();
		this.holder.setBackground(Color.BLACK);
		this.holder.setPreferredSize(new Dimension(this.WIDTH,
				(int) (.7 * this.HEIGHT)));

		this.holder.setLayout(new GridLayout(6, 2));

		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		this.player_name = new JLabel("PName");
		this.player_name.setHorizontalAlignment(SwingConstants.CENTER);

		this.plottedv = new JLabel();
		this.capturedv = new JLabel();
		this.cellsv = new JLabel();
		this.captCellsv = new JLabel();
		this.scorev = new JLabel();

		//this.player_name.setForeground(new Color(250, 250, 250));
//		 player_name.setBorder(BorderFactory.createLineBorder(Color.BLACK,
//		 2));
		Font pfont = new Font("Arial", Font.BOLD, this.FONT_SIZE + 1);
		this.player_name.setFont(pfont);

		this.plottedv.setForeground(new Color(204, 255, 100));
		Font pttvfont = new Font("Arial", this.plottedv.getFont().getStyle(),
				this.FONT_SIZE);
		this.plottedv.setFont(pttvfont);

		this.capturedv.setForeground(new Color(255, 51, 100));
		Font cvfont = new Font("Arial", this.capturedv.getFont().getStyle(),
				this.FONT_SIZE);
		this.capturedv.setFont(cvfont);

		this.cellsv.setForeground(new Color(179, 100, 255));
		Font clvfont = new Font("Arial", this.cellsv.getFont().getStyle(),
				this.FONT_SIZE);
		this.cellsv.setFont(clvfont);

		this.captCellsv.setForeground(new Color(255, 150, 100));
		Font cplvfont = new Font("Arial", this.captCellsv.getFont().getStyle(),
				this.FONT_SIZE);
		this.captCellsv.setFont(cplvfont);

		this.scorev.setForeground(new Color(0, 255, 0));
		Font scvfont = new Font("Arial", this.scorev.getFont().getStyle(),
				this.FONT_SIZE);
		this.scorev.setFont(scvfont);

		final JLabel plotted = new JLabel("  Plt:");
		final JLabel captured = new JLabel("  Cap:");
		final JLabel cptcells = new JLabel("  Cce:");
		final JLabel cells = new JLabel("  Cel:");
		final JLabel score = new JLabel("  Scr:");

		// JKIcon p = new JKIcon("media/yellow.gif", "");
		// plotted = p.createIcon();

		plotted.setForeground(new Color(255, 204, 51));
		Font pttfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		plotted.setFont(pttfont);

		// final JKIcon c = new JKIcon("media/capt_dot.gif", "");
		// captured = c.createIcon();

		captured.setForeground(new Color(255, 204, 51));
		Font cfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		captured.setFont(cfont);

		// final JKIcon cl = new JKIcon("media/cell.gif", "");
		// cells = cl.createIcon();

		cells.setForeground(new Color(255, 204, 51));
		Font clfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		cells.setFont(clfont);

		// final JKIcon cc = new JKIcon("media/capt_cell.gif", "");
		// cptcells = cc.createIcon();

		cptcells.setForeground(new Color(255, 204, 51));
		Font ctcellsf = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		cptcells.setFont(ctcellsf);

		score.setForeground(new Color(250, 104, 60));
		Font scfont = new Font("Arial", Font.BOLD, this.FONT_SIZE);
		score.setFont(scfont);

		// add(new JLabel("  "));
		this.holder.add(plotted);
		this.holder.add(this.plottedv);
		this.holder.add(captured);
		this.holder.add(this.capturedv);
		this.holder.add(cells);
		this.holder.add(this.cellsv);
		this.holder.add(cptcells);
		this.holder.add(this.captCellsv);
		this.holder.add(score);
		this.holder.add(this.scorev);
		add(add(this.player_name), BorderLayout.NORTH);
		add(add(this.holder), BorderLayout.SOUTH);
		initPanel();

	}

	/**
	 * This function determines the predominant color between R,G,B
	 * 
	 * @return 1 if R, 2 if G and 3 if B
	 */
	public int compareRGB(Color c) {
		int ret = 3;
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		if (r > c.getGreen() && r >= b) {
			return 1;
		}

		if (g > r && r >= b) {
			return 2;
		}

		if (b > r && r >= g) {
			return 3;
		}

		if (b < r && r == g) {
			ret = 4;
		}

		if (b == r && r == g && b > 200) {
			ret = 5;
		}
		return ret;
	}

	/**
	 * @param bg
	 *            the bg to set
	 */
	public void setBg(Color bg) {
		int c = compareRGB(bg);
		if (c == 1) {
			setBackground(new Color(40, 10, 10));
			//setBackground(new Color(250, 220, 220));
			// JKIcon r = new JKIcon("media/red.gif","");
			// plotted = r.createIcon();
			// plotted.setLabelFor(r.createIcon());
		}
		if (c == 2) {
			setBackground(new Color(10, 40, 10));
			//setBackground(new Color(220, 250, 220));
			// plotted = new JKIcon("media/green.gif","");
		}
		if (c == 3) {
			setBackground(new Color(10, 10, 40));
			//setBackground(new Color(220, 220, 250));
			// plotted = new JKIcon("media/blue.gif","");
		}

		if (c == 4) {
			setBackground(new Color(40, 40, 10));
			//setBackground(new Color(250, 250, 220));
			// plotted = new JKIcon("media/yellow.gif","");
		}
		if (c == 5) {
			setBackground(new Color(40, 40, 40));
			//setBackground(new Color(220, 220, 220));
			// plotted = new JKIcon("media/yellow.gif","");
		}
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

	// public void updatePlayerPanel(double score, Player p) {
	// try {
	// setPlottedv("" + p.getPloted().size());
	// setCapturedv("" + p.getCapturedDots().size());
	// setCellsv("" + p.getCells().size());
	// setCaptCellsv("" + p.getCapturedCells().size());
	// setScorev("" + p.getScore());
	// } catch (Exception e) {
	// System.out.println(" in PlayerPanel " + e.getMessage());
	// }
	// }

	public void updatePlayerPanel(Player p) {
		try {
			setPlottedv("" + p.getPloted().size());
			setCapturedv("" + p.getCapturedDots().size());
			setCellsv("" + p.getCells().size());
			setCaptCellsv("" + p.getCapturedCells().size());
			setScorev("" + p.getScore());
		} catch (Exception e) {
			System.out.println(" In PlayerPanel " + e.getMessage());
		}
	}

	public void initPanel() {
		this.plottedv.setText("0");
		this.capturedv.setText("0");
		this.cellsv.setText("0");
		this.captCellsv.setText("0");
		this.scorev.setText("0");

	}

	public void initPanelForNewGame(String pname, Color pcolor) {
		try {
			setBorder(BorderFactory.createLineBorder(pcolor, 1));
			pname = pname.toUpperCase();
			setPlayer("  " + pname);
			setBg(pcolor);
			this.player_name.setForeground(pcolor);
			this.plottedv.setText("0");
			this.capturedv.setText("0");
			this.cellsv.setText("0");
			this.captCellsv.setText("0");
			this.scorev.setText("0");
		} catch (Exception e) {
			System.err.println(" in PlayerPanel " + e.getMessage());
		}
	}

	/**
	 * @return the plottedv
	 */
	public JLabel getPlottedv() {
		return this.plottedv;
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
		this.capturedv.setText(str);
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
		this.cellsv.setText(str);
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
		this.captCellsv.setText(str);
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
		this.scorev.setText(str);
	}

	// public void setPlayerName(String name) {
	// p.setName(name);
	// }

	public void setLabelColor(Color c) {
		this.player_name.setForeground(c);
	}
}
