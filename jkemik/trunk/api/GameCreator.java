package api;

import java.io.Serializable;

import agents.JkBot;

public class GameCreator implements GameFactory,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Game createGame(Player p1, Player p2) {
		//return Game.getInstance(p1, p2);
		return new Game(p1,p2);
	}

	@Override
	public AIGame createGame(Player p1, JkBot p2) {
		//return AIGame.getInstance(p1, p2);
		return new AIGame(p1,p2);
	}

}
