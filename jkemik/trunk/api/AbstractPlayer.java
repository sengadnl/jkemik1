package api;

import controler.JKemik;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utilities.Globals;
import view.Grid;

/**
 * COPYRIGHT(c)2010 Daniel Senga. All Right Reserved. This class is a parent to
 * all Player Objects and their subclasses.
 * 
 * @author Daniel Senga Version 0.02010
 * @version 10.0
 */
public abstract class AbstractPlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        private final Lock boardStatusLock;
        private ArrayList<HotPoint> status;
	public AbstractPlayer(Color color, String name) {
		super();
		this.color = color;
		this.name = name;
		this.score = 0.0;
		this.selected = new ArrayList<>();
		this.Cells = new HashMap<>();
                this.boardStatusLock = new ReentrantLock();
                this.status = new ArrayList<>();
	}
        public void updateBoardStatus(){
            //System.err.println("---------------------");
            this.boardStatusLock.lock();
            AIGame game;Cell c;
            //ArrayList<HotPoint> status;
            
            try{
                game = (AIGame) JKemik.getGame();
                //status = this.aiStatus.getStatus();
                if(null != game.getLastCell()){
                    c = game.getLastCell();
                    ArrayList<Point> cellWall = c.getCellContour();
                    ArrayList<Point> captures = c.getAreaIncell();

                    //remove dead points from boardStatus
                    for(Point p: captures){
                        if(p.getStatus() == Globals.POINT_DEAD || p.getStatus() == Globals.POINT_CAPTURED){
                            for(HotPoint s: status){
                                if(s.getKey().equals(p.toString())){
                                    //System.out.println("Removing " + s.toString());
                                    status.remove(s);
                                    break;
                                }
                            }
                        }
                    }
                } 

                //update status
                for(Point temp: game.getCollection().values()){
                    temp.setVulnerability(game, (int) Grid.squareSize);
                }
                //System.err.println("Verifying collection size = " + game.getCollection().size());

                //Update status
                if(!status.isEmpty()){
                    for(HotPoint s: status){
                        Point z = game.getCollection().get(s.getKey());
                        if(z != null){ 
                            s.setScore(z.heatLevel);
                        } 
                    }
                }
                Collections.sort(status);
                System.err.println(this.getName() + " V status = " + status);
            }catch(NullPointerException ex){
                System.out.println("Exception in BoardStatus:updateStatus " + ex.getMessage());
            }finally{
                this.boardStatusLock.unlock();
            }

        }

        public ArrayList<HotPoint> getStatus() {
            return status;
        }

        public void setStatus(ArrayList<HotPoint> status) {
            this.status = status;
        }
        
	public int countRedeemedPoints() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			/* Look through this player's cells */
			for (Cell c1 : this.Cells.values()) {
				/* Find any captured cells */
				count += c1.getRedeemedCount();
			}
		}
		return count;
	}

	public int countCapturedPoints() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		}

		for (Cell c1 : this.Cells.values()) {
			if (c1.getStatus() == Globals.CELL_FREE) {
				count += c1.getCapturesCount();
			}
		}
		return count;
	}

	public int countFreeCells() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			for (Cell c1 : this.Cells.values()) {
				if (c1.getStatus() == Globals.CELL_FREE) {
					count++;
				}
			}
		}
		return count;
	}

	public int countCapturedCells() {
		int count = 0;
		if (this.Cells.isEmpty()) {
			return 0;
		} else {
			for (Cell c1 : this.Cells.values()) {
				for (Cell c2 : c1.getCellsInCell().values()) {
					if (c2.getStatus() == Globals.CELL_CAPTURED) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * @return the successful
	 */
	public boolean isSuccessful() {
		return this.successful;
	}

	/**
	 * @param successful
	 *            the successful to set
	 */
	public boolean getSuccessful() {
		return this.successful;
	}

	/**
	 * @param successful
	 *            the successful to set
	 */
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	/** Adds a captured cell to this player */
	public void addCell(Cell c) {

		// Add what this cell is worth to this player'score

		if (c != null && c.getStatus() == Globals.CELL_FREE) {
			this.score += c.getValue();
			this.Cells.put(c.hashCode(), c);
		}
	}

	/** Adds a captured cell to this player */
	public void removeCell(Cell c) {

		// Add what this cell is worth to this player'score
		System.out.println("Removing a cell : " + c.toString());
		this.Cells.remove(c);
		this.score -= c.getValue();
	}

	/**
	 * @return the fadedColor
	 */
	public Color getFadedColor() {
		int red = this.color.getRed(), green = this.color.getGreen(), blue = this.color
				.getBlue();

		if (red > this.FADE_VARIANT) {
			red = Math.abs(red - this.FADE_VARIANT);
		}

		if (blue > this.FADE_VARIANT) {
			blue = Math.abs(blue - this.FADE_VARIANT);
		}

		if (green > this.FADE_VARIANT) {
			green = Math.abs(green - this.FADE_VARIANT);
		}
		return new Color(red, green, blue);
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(HashMap<Integer, Cell> cells) {
		this.Cells = cells;
	}

	/**
	 * @return the cells
	 */
	public HashMap<Integer, Cell> getCells() {
		return this.Cells;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return " " + this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		System.out.println("setting color" + color);
		this.color = color;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getPlay_flag() {
		return play_flag;
	}

	public void setPlay_flag(int playFlag) {
		play_flag = playFlag;
	}

	public int compareTo(AbstractPlayer o) {
		if (this.getColor().equals(o.getColor())) {
			return 0;
		}
		return 1;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */

	public String toString() {
		return "\nID: " + this.id + "\nName: " + this.name + "\nColor: "
				+ this.color.toString() + "\nFaded Color: "
				+ this.getFadedColor() + "\nScore: " + this.score
				+ "\nMy turn: " + this.turn + "\nCells: " + this.Cells
				+ "\nCapturedCells: " + this.countCapturedCells()
				+ "\nPlay Flag: " + this.play_flag + "\nCapture successfull: "
				+ this.successful + "\nFade variant: " + this.FADE_VARIANT
				+ "\n";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Point> getSelected() {
		return selected;
	}

	public void setSelected(ArrayList<Point> arrayList) {
		this.selected = arrayList;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Point getFrom() {
		return from;
	}

	public void setFrom(Point from) {
		this.from = from;
	}

	public Point getLatestP() {
		return latestP;
	}

	public void setLatestP(Point latestP) {
		this.latestP = latestP;
	}

	public Cell getLastCapture() {
		return lastCapture;
	}

	public void setLastCapture(Cell lastCapture) {
		this.lastCapture = lastCapture;
	}

	/**
	 * Remember this point as previously visited
	 * 
	 * @param p
	 *            point to remember and max the maximum number of points
	 *            remembered max also determines how far the capture algorithm
	 *            should backtrack from the latest point
	 * @return void
	 */
	public void rememberPoint(Point p, int max) {
		if (this.lastpoints.size() < max) {
			this.lastpoints.add(p);
		} else {
			this.lastpoints.remove(0);
			this.lastpoints.add(p);
		}
	}

	public ArrayList<Point> getLastpoints() {
		return lastpoints;
	}

	public void setLastpoints(ArrayList<Point> lastpoints) {
		this.lastpoints = lastpoints;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points += points;
	}

	private String name = "player";
	private int id = 0;// player1 = -1 and player2 = 1
	private int points = 0;

	private boolean turn = false;
	private boolean ai = false;
	private double score = 0.0;
	private Color color;

	private Point origin = new Point(444444, 7798979);
	private Point from = new Point(553355, 7798979);
	private Point latestP = new Point(550055, 7798979);

	public ArrayList<Point> lastpoints = new ArrayList<Point>();

	private HashMap<Integer, Cell> Cells = null;
	private Cell lastCapture = null;
	private ArrayList<Point> selected = null;

	private boolean successful = false;
	private final int FADE_VARIANT = 70;
	private int play_flag = 0;
}
