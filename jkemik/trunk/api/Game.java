package api;
import java.io.Serializable;

public class Game extends AbstractGame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static volatile Game instance = null;

	public Game(Player player1, Player player2) {
		super(player1, player2);
	}

//	public static Game getInstance(Player player1, Player player2) {
//		if (instance == null) {
//			synchronized (Game.class) {
//				if (instance == null) {
//					instance = new Game(player1, player2);
//				}
//			}
//		}
//		return instance;
//	}
}
