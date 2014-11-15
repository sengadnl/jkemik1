package agents;
import api.*;
import api.Point;
import controler.JKemik;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import utilities.Globals;
import view.Grid;

//import api.AbstractPlayer;

public class JkBot extends Player implements AgentAction,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean engaged = false;

   
//        private BoardStatus aiStatus;
//        private BoardStatus humanStatus;
        private Point cursorPosition;
        
	public JkBot(Color color, String name) {
		super(color, name);
		this.setAi(true);

//                this.aiStatus = new BoardStatus();
//                this.humanStatus = new BoardStatus();
                turnChangeLock = new ReentrantLock();
	}
        private Point move(AIGame game){

            ArrayList<HotPoint> hStatus, aiStats;
            int sizeAI,sizeH;
            
            hStatus = game.getHuman().getStatus();
            aiStats = this.getStatus();
            
            if(hStatus.isEmpty() && aiStats.isEmpty()){
                return this.randomPoint();
            }
            
            //Return a random point if there is no status to track
            if(hStatus.isEmpty() || aiStats.isEmpty()){
                return offense(game, game.getLastp());
            }
                    
            sizeAI = aiStats.size();
            sizeH = hStatus.size();
            
            /*get most vulnerable ai point*/
            HotPoint ai,h;
            ai = aiStats.get(sizeAI - 1);
            h = hStatus.get(sizeH - 1);
            
            /*offense if ai is less or equally vulnerable than h*/
            Point temp;
            //Evaluate who is most vulnerable
            if(ai.compareTo(h) <= 0){
                //System.err.println("AI-" + ai.toString() + " --- " + "HU-" + h.toString());
                for(int i = sizeH - 1 ; i >= 0 ; i--){
                    temp = offense(game, game.getCollection().get(hStatus.get(i).getKey()));
                    if(temp != null){
                        System.err.println("Offense <<< at " + temp);
                        return temp;
                    } 
                }
            }
            /*Defense if ai is more vulnerable than h*/
            for(int i = sizeAI - 1; i >= 0 ; i--){
                temp = offense(game, game.getCollection().get(aiStats.get(i).getKey()));
                if(temp != null){
                    System.err.println("Defense <<< at " + temp);
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
                if(!game.getMachine().isTurn()){
                    return move;
                }
                move = move(game);
                move.setStatus(Globals.POINT_PLAYED);

                //Mark point as belonging to current player
                move.setId(this.getId());

                //Remember last play
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
            }finally{
                turnChangeLock.unlock();
            }
            return move;
	}

    /**
     *
     * @param game
     * @param humanPoint
     * @return Point representing a move
     */
    @Override
        public Point offense(AIGame game, Point humanPoint) {
            Point[] axis,diagonals;
            ArrayList<Point> holder;
            double w, h;
            
            w = JKemik.settings_t.getGridDimension().getPixelDimension().getWidth();
            h = JKemik.settings_t.getGridDimension().getPixelDimension().getHeight();

            /*Find free Points on the axises*/
            axis = humanPoint.axisBox(Grid.squareSize);
            holder = new ArrayList<>();
            for (Point a : axis) {
                if (!game.getCollection().containsKey(a.toString())) {
                    if(this.isPointInBoard(a, w, h)){
                        holder.add(a);
                    }
                }
            }
            /*Pick a random Point*/
            if(!holder.isEmpty()){
                return holder.get((new Random()).nextInt(holder.size()));
            }
            
            /*Find free Points on the diagonals*/
            diagonals = humanPoint.diagonalBox(Grid.squareSize);
            holder = new ArrayList<>();//reset
            for (Point d : diagonals) {
                if (!game.getCollection().containsKey(d.toString())) {
                    if(this.isPointInBoard(d, w, h)){
                        holder.add(d);
                    }
                }
            }
            /*Pick a random Point*/
            if(!holder.isEmpty()){
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
            //System.err.println("Returning a random point!!!!!!!!");
            return p1;
        }

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

        public Point getCursorPosition() {
            return cursorPosition;
        }

        public void setCursorPosition(Point cursorPosition) {
            this.cursorPosition = cursorPosition;
        }

        @Override
        public ArrayList<Point> moveCursorTo(Point o, Point d, double squareSize) {
            ArrayList<Point> list; Point temp;
            list = new ArrayList<>();
            ArrayList<Point> a = o.twoPointsMoveTo(d, squareSize);
            temp = a.get(0);
            list.add(o);
            while(!(temp.compareTo(d) == 0)){

                for(Point p: a){
                    list.add(p);
                }
                temp = new Point(a.get(a.size() - 1).getXC(),a.get(a.size() - 1).getYC());
                a = temp.twoPointsMoveTo(d, squareSize);
            }
            return list;
        }
        private Lock turnChangeLock;       
}
