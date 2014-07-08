package agents;

import api.AIGame;

/**
 * Interface defining Agent actions. Example play()
 * Every Agent must implement this interfaces*/
public interface AgentAction {
	public boolean play(AIGame game);
}
