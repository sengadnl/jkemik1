package api;

import agents.JkBot;

public class AIGame extends AbstractGame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static volatile AIGame instance = null; 
	public AIGame(Player player1, JkBot player2) {
		super(player1, player2);
		this.setAI(true);
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

//	public static AIGame getInstance(Player player1, JkBot player2){
//		if (instance == null) {
//			synchronized (AIGame.class) {
//				if (instance == null) {
//					instance = new AIGame(player1,player2);
//				}
//			}
//		}
//		return instance;
//	}
}
