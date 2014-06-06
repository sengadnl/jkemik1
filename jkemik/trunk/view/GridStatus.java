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


/**
 * @author dalet
 *
 */
public class GridStatus extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel deadGridInPercent;
	private JLabel freeGridInPercent;
	private JLabel deadCount;
	private JLabel tot_play_count;

	//private JLabel gridSize;
	
	
	private final int FONT_SIZE = 11;
	public GridStatus(int w, int h){
		
		setPreferredSize(new Dimension(w,h));
		setOpaque(true);
		//setBackground(BoardFrame.BOARD_COLOR);
		//setBorder(BorderFactory.createLineBorder(BoardFrame.BOARD_COLOR, 1));
		setLayout(new GridLayout(2,4));
		
		//this.gridSize = new JLabel(0 + " x " + 0); // Assuming the GridPanel already exists
		this.deadCount = new JLabel();
		this.deadGridInPercent = new JLabel();
		this.freeGridInPercent = new JLabel();
		this.tot_play_count = new JLabel();
		
		this.deadCount.setForeground(new Color(204,255,100));
        Font pttvfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        this.deadCount.setFont(pttvfont);
       
        this.deadGridInPercent.setForeground(new Color(255,51,100));
        Font cvfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        this.deadGridInPercent.setFont(cvfont);
       
        this.freeGridInPercent.setForeground(new Color(179,100,255));
        Font clvfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        this.freeGridInPercent.setFont(clvfont);
       
        
        this.tot_play_count.setForeground(Color.YELLOW);
        Font gsfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        this.tot_play_count.setFont(gsfont);
          
        final JLabel dc = new JLabel("  Tot plots:");
        dc.setForeground(new Color(255,255,255));
        Font pttfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        dc.setFont(pttfont);
       
        final JLabel dgp = new JLabel("  Occupied:");
        dgp.setForeground(new Color(255,255,255));
        Font cfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        dgp.setFont(cfont);
       
        final JLabel fgp = new JLabel("  Free:");
        fgp.setForeground(new Color(255,255,255));
        Font clfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        fgp.setFont(clfont);
        
        final JLabel gs = new JLabel("  Play: ");
        gs.setForeground(new Color(255,255,255));
        Font gfont = new Font("Arial",Font.BOLD,this.FONT_SIZE);  
        gs.setFont(gfont);
        init();

        
        add(dc);
        add(this.deadCount);
        add(dgp);
        add(this.deadGridInPercent);
        add(fgp);
        add(this.freeGridInPercent);
        add(gs);
        add(this.tot_play_count);
	}

	/**
	 * @return the dridSize
	 */
//	public JLabel getGridSize() {
//		return this.gridSize;
//	}
//	/**
//	 * @param dridSize the dridSize to set
//	 */
//	public void setGridSize(int rows, int cols) {
//	//	if(rows > 0 && cols > 0){
//		cols = cols - 1;
//		rows = rows - 1;
//		this.gridSize.setText(cols + " x " + rows);
//		System.out.println((cols - 1) + " x " + (rows - 1));
////		}else{
////			throw new IllegalStateException();
////		}
//	}
	public void init(){
		setDeadCountV("0");
		setDeadGridInPercentV(0);
		setFreeGridInPercentV(0);
		setTot_play_count(0);
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
	 * @param deadCount the deadCount to set
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
	 * @param deadGridInPercent the deadGridInPercent to set
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
	 * @param freeGridInPercent the freeGridInPercent to set
	 */
	public void setFreeGridInPercentV(double num) {
		DecimalFormat format = new DecimalFormat("##.##");
		String str = format.format(num);
		this.freeGridInPercent.setText(str + " %");
	}


	/**
	 * @param gridSize the gridSize to set
	 */
//	public void setGridSize(JLabel gridSize) {
//		this.gridSize = gridSize;
//	}
}
