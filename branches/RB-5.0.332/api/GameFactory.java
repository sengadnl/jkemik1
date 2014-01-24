package api;

import agents.JkBot;

interface GameFactory {
	public Game createGame(Player p1, Player p2);
	public AIGame createGame(Player p1, JkBot p2);
}
