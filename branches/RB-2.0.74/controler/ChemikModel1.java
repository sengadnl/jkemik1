/**
 * 
 */
package controler;

import java.util.ArrayList;




import api.Cell;
import api.GTemplate;
import api.Game;
import api.Player;
import api.Point;


/**
 * @author dalet
 *
 */
public class ChemikModel1 {
	private static Game game;
	public ChemikModel1(){
	}
	public void init(GTemplate t){
		Player p1 = new Player(t.getP1_c(),t.getP1_name());
		Player p2 = new Player(t.getP2_c(),t.getP2_name());
		game = new Game(p1,p2);
	}
	/**
	 * @return the game
	 */
	public static Game getGame() {
		return game;
	}
	/**
	 * @param game the game to set
	 */
	public void setGame(Game g) {
		game = g;
	}
	/**
	 * creates a new game*/
	public void newGame(GTemplate t){
		//game = null;
		Player p1 = new Player(t.getP1_c(),t.getP1_name());
		Player p2 = new Player(t.getP2_c(),t.getP2_name());
		game = new Game(p1,p2);
	}
	/**
	 * initiates a re-match
	 */
	public void initRematch() {
		game.getPlayer1().setCapturedDots(new ArrayList<Point>());
		game.getPlayer1().setCells(new ArrayList<Cell>());
		game.getPlayer1().setConnectedPoints(new ArrayList<Point>());
		game.getPlayer1().setPloted(new ArrayList<Point>());
		game.getPlayer1().setScore(0.0);
		
		game.getPlayer2().setCapturedDots(new ArrayList<Point>());
		game.getPlayer2().setCells(new ArrayList<Cell>());
		game.getPlayer2().setConnectedPoints(new ArrayList<Point>());
		game.getPlayer2().setPloted(new ArrayList<Point>());
		game.getPlayer2().setScore(0.0);
	}
	public Player getCurrentPlayer(){
		return game.getCurrentP();
	}
	public Player getGuestPlayer(){
		return game.getGuest();
	}
	public ArrayList<Point> getDeadDots(){
		return game.getDeadDots();
	}
}
