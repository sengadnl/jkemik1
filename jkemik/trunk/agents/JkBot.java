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
        private Point move(AIGame game){

          
            //Return a random point if there is no status to track
            if(this.aiStatus.getStatus().isEmpty()){
                System.err.println("??? can't decide were to play!!! mmmmm, trying ");
                return offense(game, game.getLastp());
            }

            ArrayList<HotPoint> hStatus, aiStatus;
            int sizeAI,sizeH;
            
            hStatus = this.humanStatus.getStatus();
            aiStatus = this.aiStatus.getStatus();
                    
            sizeAI = aiStatus.size();
            sizeH = hStatus.size();
            
            /*get most vulnerable ai point*/
            HotPoint ai,h;
            ai = this.aiStatus.getStatus().get(sizeAI - 1);
            h = hStatus.get(sizeH - 1);
            
            /*offense if ai is less or equally vulnerable than h*/
            Point temp;
            System.err.println("offense >>>");
            //Evaluate who is most vulnerable
            if(ai.compareTo(h) < 0){
                for(int i = sizeH - 1; i >= 0 ; i--){
                    temp = offense(game, game.getCollection().get(hStatus.get(i).getKey()));
                    if(temp != null){
                        return temp;
                    } 
                }
            }
            /*Defense if ai is more vulnerable than h*/
            System.err.println("Defense >>>");
            for(int i = sizeAI - 1; i >= 0 ; i--){
                temp = offense(game, game.getCollection().get(aiStatus.get(i).getKey()));
                if(temp != null){
                    return temp;
                } 
            }
            return null;
        }

	@Override
        /*Strategy: 
        - Defense or offence
        - If defence, predict opponents next move, return the infered next move.
        - If offence, pursuit a plan, return the next*/
	public Point play(AIGame game) {
            turnChangeLock.lock();
            Point move = null;
            try{
                move = move(game);
                

                move.setStatus(Point.PLAYED);

                // Mark point as belonging to current player
                move.setId(this.getId());

                // Remember last play
                this.setLatestP(move);

                //System.out.println("Adjacent point: " + move);
                
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
            return move;
	}
        public Point offense(AIGame game, Point humanPoint) {
            Point[] axis,diagonals;
            ArrayList<Point> holder;
            double w, h;
            
            w = JKemik.settings_t.getGridDimension().getPixelDimension().getWidth();
            h = JKemik.settings_t.getGridDimension().getPixelDimension().getHeight();

            //Detect a square cell
            axis = humanPoint.axisBox(Grid.squareSize);
            holder = new ArrayList<>();
            for (Point a : axis) {
                if (!game.getCollection().containsKey(a.toString())) {
                    if(this.isPointInBoard(a, w, h)){
                        holder.add(a);
                    }
                }
            }
            //Randomization
            if(!holder.isEmpty()){
                System.err.println("offense - quare cell : " + (holder.size()));
                return holder.get((new Random()).nextInt(holder.size()));
            }
            
            //Detect other forms
            diagonals = humanPoint.diagonalBox(Grid.squareSize);
            holder = new ArrayList<>();//reset
            for (Point d : diagonals) {
                if (!game.getCollection().containsKey(d.toString())) {
                    if(this.isPointInBoard(d, w, h)){
                        holder.add(d);
                    }
                }
            }
            //Randomization
            if(!holder.isEmpty()){
                System.err.println("offense - not quare cell : " + (holder.size()));
                return holder.get((new Random()).nextInt(holder.size()));
            }
            
            return null;
        }

    /**
     *
     * @return a random point on the grid.
     */
        public Point randomPoint(){
            AIGame game;
            Point p1,p2;
            Random r;
            double x,y;
            
            game = (AIGame)JKemik.getGame();
            int wbound = (int) JKemik.settings_t.getGridDimension().getPixelDimension().getWidth();
            int hbound = (int) JKemik.settings_t.getGridDimension().getPixelDimension().getHeight();
            
            r = new Random();
            
            do{
                x = r.nextInt(wbound);
                y = r.nextInt(hbound);
                p1 = Grid.closestPoint(x,y, (int) Grid.squareSize);
                p2 = game.getCollection().get(p1.toString());
            }while(p2 != null);
            System.err.println("Returning a random point!!!!!!!!");
            return p1;
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

    @Override
        public boolean isPointInBoard(Point p, double w, double h) {
            double x,y;
            x = p.getXC();
            y = p.getYC();
            
           if(x > w || x < 0.0 || y > h || y < 0.0){
               return false;
           }
           return true;
        }

       
}
