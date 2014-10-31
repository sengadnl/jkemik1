package agents;

import api.AIGame;
import api.Point;

/**
 * Interface defining Agent actions. Example play()
 * Every Agent must implement this interfaces*/
public interface AgentAction {
    public boolean play(AIGame game);
    public Point offense(AIGame game, Point humanPoint);
}
