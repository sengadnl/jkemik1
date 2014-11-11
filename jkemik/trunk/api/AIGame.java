package api;

import agents.JkBot;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AIGame extends AbstractGame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        private Lock aiGameLock;
       
	//private static volatile AIGame instance = null; 
	public AIGame(Player player1, JkBot player2) {
		super(player1, player2);
		this.setAI(true);
                this.aiGameLock = new ReentrantLock();
	}
//        public static AIGame getInstance(Player player1, JkBot player2) {
//		if (instance == null) {
//			synchronized (AIGame.class) {
//				if (instance == null) {
//					instance = new AIGame(player1, player2);
//				}
//			}
//		}
//		return instance;
//	}
        public Point put(String key, Point p){ 
            this.aiGameLock.lock();
            Point object = null;
           try{
                object = this.getCollection().put(key, p);

                /*Update board status*/
                JkBot ai = (JkBot) this.getMachine();
                Player hu = (Player) this.getHuman();
                if(p.getId() == ai.getId()){
                    ai.getStatus().add(new HotPoint(key,0));
                }else{
                    hu.getStatus().add(new HotPoint(key,0));  
                }
                ai.updateBoardStatus();
                hu.updateBoardStatus();
                this.setLastp(p);

            }catch(NullPointerException ex){
                 System.err.println("Error in AIGame:put > " + ex.getMessage()
                 );
            }finally{
               this.aiGameLock.unlock();
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
        
}
