package agents;
import api.*;
import api.Point;
import controler.JKemik;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
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
                //turnChangeLock = new ReentrantLock();
	}
	@Override
	public boolean play(AIGame game) {
            //turnChangeLock.lock();
            try{
                
                ArrayList<Point> hPoints = game.getHuman().getLastpoints();
                ArrayList<Point> machineMove = game.getMachine().getLastpoints();
               // offenseOrigine = machineMove.get(machineMove.size() - 1);
                Point hMove = hPoints.get((hPoints.size() - 1));
                if(offense(game)){
                	System.out.println("Launching an offense");
                }
                 
                //================================================
                //ArrayList<Point> o = offenseStrategy(machineMove.get(machineMove.size() - 1), game, Grid.squareSize);

                //Point move = bestAdjacantMoveTo(o.get(o.size() - 1), game.getCollection());
                Point move = bestAdjacantMoveTo(hMove, game.getCollection());
                //===================================================
                move.setStatus(Point.PLAYED);

                // Mark point as belonging to current player
                move.setId(this.getId());

                // Remember last play
                this.setLatestP(move);

                // Add to the board
                game.getCollection().put(move.toString(), move);
                game.getCurrentP().rememberPoint(move,
                                JKemik.settings_t.getBacktrackingDistance());
                game.setPlay_count(game.getPlay_count() - 1);
                game.setEmbuche_on(true);

                // Setting turn
                game.setPlayFlag();
                game.getCurrentP().setTurn(false);
                Grid.mouseMove = false;
            }catch(NullPointerException | ArrayIndexOutOfBoundsException e){
                System.out.println("play: " + e.getMessage());
            }
            
//            finally{
//                turnChangeLock.unlock();
//            }
            return false;
	}

        /**
         *
         * @param offense
         * @param defense
         * @return
         */
        public boolean offense(AIGame game){
        	//get AI latest points
            int size = game.getMachine().getLastpoints().size();
            
           //Get the last human point
           offenseOrigine = game.getHuman().getLastpoints().get(size - 1);
           // offense.put(offenseOrigine.toString(), offenseOrigine);
           
           //check how far human is to a capture
            if(humanNextMoveFrom(offenseOrigine,game)){
                System.out.println("strategy: " + offense);
                return true;
            } 
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
        /**
         * This functions goal is a add to a path that defines the current strategy
	 * @param o
	 *            Point where to start
     * @param game
	 * @param squareSize
	 *            integer length of the sides of a grid square
	 * @return true when a valid capture was found, and false otherwise.
	 */
	private boolean humanNextMoveFrom(Point o, AIGame game) {

                Point[] box = o.box(Grid.squareSize);
                for(Point p: box){
                    /*Spik if*/
                	if(offenseArea.isEmpty()){
                		
                	}
                	
                	/*Setting origine*/
                    if(offense.isEmpty() && 
                    		game.getCollection().containsKey(p.toString()) 
                    		&& p.getId() == game.getMachine().getId()){
                    	offense.put(p.toString(),p);
                    	offenseFrom = p;
                    }
                    
                    if(offenseArea.containsKey(p.toString()) 
                            || offense.containsKey(p.toString())){
                        continue;
                    }
                    
                    /*if this point belongs to human add it to area*/
                    if(game.getCollection().containsKey(p.toString()) 
                            && p.getId() == game.getHuman().getId()){
                        //offenseArea.put(p.toString(),p);
                        if(!humanNextMoveFrom(p, game)){
                        }
                        continue;
                    }
                    
                    if(offenseFrom.compareTo(p) != 0){
                        if(offenseOrigine.compareTo(p) == 0 && offense.size() > 3){
                            return true;
                        }
                        /*if not add to path*/
                        offense.put(p.toString(),p);
                        offenseFrom = p;
                    }
                }
            return false;        
	}
        
        /**
         * This functions goal is a add to a path that defines the current strategy
	 * @param o
	 *            Point where to start
     * @param game
	 * @param squareSize
	 *            integer length of the sides of a grid square
	 * @return true when a valid capture was found, and false otherwise.
	 */
	public ArrayList<Point> defenseStrategy(Point o, AIGame game, double squareSize) {
		if (this.getSuccessful()) {
			return defense;
		}
		/* Get all adjacent Points */
		Point[] box = Tools.boxCoord(o, squareSize);
            for (Point box1 : box) {
                /* Stop recursive call here if a path was already found */
                if (this.getSuccessful()) {
                    return defense;
                }
                Point temp = game.getCollection().get(box1.toString());
                if (temp == null) {
                    continue;
                }
                if (AbstractGame.isPath(temp)) {// if this Point is path
                    if (temp.compareTo(game.getHuman().getFrom()) != 0) {
                        if (!Tools.containPoint(o, game.getHuman().getSelected())) {
                            /* Add o if it hasn't been visited */
                            game.getHuman().getSelected().add(o);
                            // System.out.println("Adding " +
                            // currentP.getSelected());
                            game.getHuman().setFrom(o); /* Move to the next Point */
                            if (temp.compareTo(game.getHuman().getOrigin()) == 0
                                    && game.getHuman().getSelected().size() > 3) {
                                game.getHuman().setSuccessful(true);
                                game.getHuman().setOrigin(null);/* Reset the origin */
                                // System.err.println("\nbuildPath: "
                                // + currentP.getSelected());
                                return defense;
                            }
                            
                            /* This adjacent Point was a dead end */
                            if (defenseStrategy(temp, game, squareSize) != null) {
                                game.getHuman().getSelected().remove(o);
                            }
                        }
                    }
                }
            }
            return defense;
	}
    //private Lock turnChangeLock;
        private HashMap<String, Point> offense;
        private HashMap<String, Point> offenseArea;
        private Point offenseOrigine = new Point(0,0);
        private Point offenseFrom = new Point(0,0);
        private ArrayList<Point> defense;
        
}
