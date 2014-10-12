package agents;
import api.*;
import api.Point;
import controler.JKemik;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import utilities.Tools;
import view.Grid;

//import api.AbstractPlayer;

public class JkBot extends Player implements AgentAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean engaged = false;
        
	public JkBot(Color color, String name) {
		super(color, name);
		this.setAi(true);
		offenseArea = new HashMap<String,Point>();
		offense = new HashMap<String,Point>();
                //
                turnChangeLock = new ReentrantLock();
	}
        private Point decide(AIGame game){
            ArrayList<Point> hPoints = game.getHuman().getLastpoints();
            Point hMove = hPoints.get((hPoints.size() - 1));
            if(game.getBoardStatus().getStatus().size() < 2){
                return hMove;
            }
            
           int index = game.getBoardStatus().getStatus().size();
           PointScore score1 = game.getBoardStatus().getStatus().get(index - 1);
           return game.getCollection().get(score1.getKey());
        }
	@Override
        /*Strategy: 
        - Defense or offence
        - If defence, predict opponents next move, return the infered next move.
        - If offence, pursuit a plan, return the next*/
	public boolean play(AIGame game) {
            turnChangeLock.lock();
            try{
                //Return the most vulnerable point
                Point point = decide(game);
                //System.err.println("decision: " + point);

                Point move = bestAdjacantMoveTo(point, game);
                
                //3. Take action
                
                
                //strategy is before this point
                //==========================================================
                move.setStatus(Point.PLAYED);

                // Mark point as belonging to current player
                move.setId(this.getId());

                // Remember last play
                this.setLatestP(move);

                System.out.println("Adjacent point: " + move);
                
                //Add to the board 
                game.put(move.toString(), move);
                game.getCurrentP().rememberPoint(move,
                                JKemik.settings_t.getBacktrackingDistance());
                game.setPlay_count(game.getPlay_count() - 1);
                game.setEmbuche_on(true);

                // Setting turn
                game.setPlayFlag();
                game.getCurrentP().setTurn(false);
               game.getCurrentP().setPoints(1);//count this point
                Grid.mouseMove = false;
            }catch(NullPointerException | ArrayIndexOutOfBoundsException e){
                System.out.println("play: " + e.getMessage());
            }
            
            finally{
                turnChangeLock.unlock();
            }
            return true;
	}

        /**
         *
         * @param offense
         * @param defense
         * @return
         *///TODO
        public void offense(Point p, HashMap<String,Point> collection){
        	
        }
        
        public void defense(Point p, HashMap<String,Point> collection){
            
        }
        
        public Point bestAdjacantMoveTo(Point p, AIGame game){
            //Point[] box = p.box(Grid.squareSize);
            Point[] box = Tools.boxForBot(p,Grid.squareSize);
            Point adj = null;
            for (Point box1 : box) {
                if (!game.getCollection().containsKey(box1.toString())) {
                    adj = box1;
                    return adj;
                }
            }
            System.err.println("No point found in the box... ");
            ArrayList<Point> hPoints = game.getHuman().getLastpoints();
            Point hMove = hPoints.get((hPoints.size() - 1));
            
            return hMove;
        }

        
        private Lock turnChangeLock;
        private HashMap<String, Point> offense;
        private HashMap<String, Point> offenseArea;
        private Point offenseOrigine = new Point(0,0);
        private Point offenseFrom = new Point(0,0);
        private ArrayList<Point> defense;
        
}
