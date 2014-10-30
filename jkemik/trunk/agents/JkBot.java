package agents;
import api.*;
import api.Point;
import controler.JKemik;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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

   
        public BoardStatus aiStatus;
        public BoardStatus humanStatus;
        
	public JkBot(Color color, String name) {
		super(color, name);
		this.setAi(true);

                this.aiStatus = new BoardStatus();
                this.humanStatus = new BoardStatus();
                turnChangeLock = new ReentrantLock();
	}
        
        private Point decide(AIGame game){

          
            //Return a random point if there is no status to track
            if(this.aiStatus.getStatus().isEmpty() || 
                    this.humanStatus.getStatus().isEmpty()){
//                boolean done = false;
//                Point random = null;
//                while(!done){
//                    random = randomPoint();
//                    if (!game.getCollection().containsKey(random.toString())) {
//                        done = true;
//                    }
//                }
                System.err.println("??? can't decide were to play!!! mmmmm, trying ");
                return game.getLastp();
            }

            int indexai,indexh;
            indexai = this.aiStatus.getStatus().size();
            indexh = this.humanStatus.getStatus().size();
            /*get most vulnerable ai point*/
            HotPoint ai,h;
            ai = this.aiStatus.getStatus().get(indexai - 1);
            h = this.humanStatus.getStatus().get(indexh - 1);
            
            /*offense if ai is less or equally vulnerable than h*/
            if(ai.compareTo(h) <= 0){
                return offense(game, game.getCollection().get(h.getKey()));
            }
            /*Defense if ai is more vulnerable than h*/
            return defense(game, game.getCollection().get(ai.getKey()));
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

                Point move = move(point, game);
                
                
                
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
        
        @Override
        public Point offense(AIGame game, Point p) {
            //TODO implement a offense strategy
            return p;
        }

        @Override
        public Point defense(AIGame game, Point p) {
            //TODO implement a offense strategy
            return p;
        }
        
        @SuppressWarnings("empty-statement")
        public Point move(Point p, AIGame game){
            
            Point[] box;
            
            box = p.box(Grid.squareSize);
            System.out.println("Boxing human point ...");
   
            ArrayList<Point> adj = new ArrayList<>();
            for (Point box1 : box) {
                if (!game.getCollection().containsKey(box1.toString())) {
                    adj.add(box1);
                    //return adj;
                }
            }
            int index = 0;
            if(!adj.isEmpty()){
                System.err.println("Bound was : " + (adj.size()));
                index = (new Random()).nextInt(adj.size());
                return adj.get(index);
            }
            System.err.println("All points around the target already exist\n"
                    + "Picking a random one" + index);
       
            //TODO deal with the null condition
            return randomPoint();
        }
//    public Point randomArrayPoint(Point[] a){
//      
//      
//     return null;
//    }
    /**
     *
     * @return a random point on the grid.
     */
        public Point randomPoint(){
            
            int wbound = (int) JKemik.settings_t.getGridDimension().getPixelDimension().getWidth();
            int hbound = (int) JKemik.settings_t.getGridDimension().getPixelDimension().getHeight();
            
            Random r = new Random();
            
            double x = r.nextInt(wbound);
            double y = r.nextInt(hbound);
            
            return Grid.closestPoint(x,y, (int) Grid.squareSize);
        }
        public BoardStatus getAiStatus() {
            return aiStatus;
        }

        public void setAiStatus(BoardStatus aiStatus) {
            this.aiStatus = aiStatus;
        }

        public BoardStatus getHumanStatus() {
            return humanStatus;
        }
        
     
        public void setHumanStatus(BoardStatus humanStatus) {
            this.humanStatus = humanStatus;
        }
        private Lock turnChangeLock;
        private HashMap<String, Point> offense;
        private HashMap<String, Point> offenseArea;
        private ArrayList<Point> defense;

       
}
