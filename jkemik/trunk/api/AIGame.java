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
