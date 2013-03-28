package api;

import agents.JkBot;

public class AIGame extends AbstractGame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AIGame(Player player1, JkBot player2) {
		super(player1, player2);
		this.setAI(true);
	}
}
