package api;

import agents.JkBot;

public class GameCreator implements GameFactory {

	@Override
	public Game createGame(Player p1, Player p2) {
		return new Game(p1, p2);
	}

	@Override
	public AIGame createGame(Player p1, JkBot p2) {
		return new AIGame(p1, p2);
	}

}
