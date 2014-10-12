package api;

import agents.JkBot;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utilities.Tools;
import view.Grid;

public class AIGame extends AbstractGame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        private BoardStatus status;
	//private static volatile AIGame instance = null; 
	public AIGame(Player player1, JkBot player2) {
		super(player1, player2);
		this.setAI(true);
                this.status = new BoardStatus();
	}
        //TODO set point score
        public int score(Point p, int id){
            int max = 0;
            Point[] box = Tools.boxForBot(p, Grid.squareSize);
            for(int i = 0; i < box.length - 1; i++){
                Point temp = this.getCollection().get(box[i].toString());
                if(temp == null){
                    continue;
                }
                if(id != temp.getId()){
                    max++;
                }
            }
            return max;
        }
        public Point put(String key, Point p){ 
            Point object = null;
           try{
                
            object = this.getCollection().put(key, p);
           //if(object != null){
            this.status.add(new PointScore(key,score(p, p.getId())));
            //System.out.println("" + this.status.toString());
           
            }catch(NullPointerException ex){
                 System.err.println("Error in AIGame:put > " + ex.getMessage()
                 );
            }
            return object;
        }
        /**
	 * @return the player1
	 */
	public AbstractPlayer getHuman() {
		return this.getPlayer1();
	}

	/**
	 * @param player1
	 *            the player1 to set
	 */
	public void setHuman(Player player1) {
		this.setPlayer1(player1);
	}

	/**
	 * @return the player2
	 */
	public AbstractPlayer getMachine() {
		return this.getPlayer2();
	}

	/**
	 * @param player2
	 *            the player2 to set
	 */
	public void setMachine(JkBot player2) {
		this.setPlayer2(player2);
	}

        public BoardStatus getBoardStatus() {
            return status;
        }

        public void setBoardStatus(BoardStatus status) {
            this.status = status;
        }
        
}
