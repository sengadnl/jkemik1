package agents;

import api.AIGame;
import api.Point;
import java.util.ArrayList;
import view.Grid;

/**
 * Interface defining Agent actions. Example play()
 * Every Agent must implement this interfaces*/
public interface AgentAction {
    public Point play(AIGame game);
    public Point offense(AIGame game, Point humanPoint);
    public boolean isPointInBoard(Point p, double w, double h);
    public ArrayList<Point> moveCursorTo(Point o, Point p, double squareSize);
    
}
