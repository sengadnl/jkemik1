package api;

import agents.JkBot;


public class AIGame extends AbstractGame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        //private BoardStatus status;
       
	//private static volatile AIGame instance = null; 
	public AIGame(Player player1, JkBot player2) {
		super(player1, player2);
		this.setAI(true);
                //this.status = new BoardStatus();
	}

        public Point put(String key, Point p){ 
            Point object = null;
           try{

                object = this.getCollection().put(key, p);

                /*Update board status*/
                JkBot ai = (JkBot)this.getMachine();
                if(p.getId() == ai.getId()){
                    ai.getAiStatus().add(new HotPoint(key,0));
                }else{
                    ai.getHumanStatus().add(new HotPoint(key,0));  
                }
                ai.getHumanStatus().updateStatus(p);
                ai.getAiStatus().updateStatus(p);
                
                this.setLastp(p);

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

}
