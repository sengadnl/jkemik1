package agents;
import api.*;
import api.Point;
import controler.JKemik;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
                //turnChangeLock = new ReentrantLock();
	}
	@Override
	public boolean play(AIGame game) {
            //turnChangeLock.lock();
            try{
            ArrayList<Point> hPoints;
            hPoints = game.getHuman().getLastpoints();
            //for(int i = (hPoints.size() - 1) ; i < 0; i--){
                Point hMove = hPoints.get((hPoints.size() - 1));
                Point machineMove = bestAdjacantMoveTo(hMove, game.getCollection());
            //}
                machineMove.setStatus(Point.PLAYED);

                // Mark point as belonging to current player
                machineMove.setId(this.getId());

                // Remember last play
                this.setLatestP(machineMove);

                // Add to the board
                game.getCollection().put(machineMove.toString(), machineMove);
                game.getCurrentP().rememberPoint(machineMove,
                                JKemik.settings_t.getBacktrackingDistance());
                game.setPlay_count(game.getPlay_count() - 1);
                game.setEmbuche_on(true);

                // Setting turn
                game.setPlayFlag();
                game.getCurrentP().setTurn(false);
                Grid.mouseMove = false;
            }catch(NullPointerException e){
                System.out.println("play: " + e.getMessage());
            }
            
//            finally{
//                turnChangeLock.unlock();
//            }
            return false;
	}
        public Point bestAdjacantMoveTo(Point p, HashMap<String,Point> collection){
            Point[] box = p.box(Grid.squareSize);
            Point adj = null;
            for (Point box1 : box) {
                if (!collection.containsKey(box1.toString())) {
                    adj = box1;
                    break;
                }
            }
            if(adj == null){
                throw new NullPointerException();
            }
            return adj;
        }
        /**
         @param game
         @return true when machine has engaged to play
         */
	public boolean isEngage(AIGame game) {
            //determine next move
            //move cursor from the last players move to this new move
            //play
            Point humanLastMove = game.getHuman().getLatestP();
            Point machineLastMove = game.getMachine().getLatestP();
            
            
            return engaged;
	}
	public void setEngage(boolean engage) {
		this.engaged = engage;
	}
    private Lock turnChangeLock;
}
