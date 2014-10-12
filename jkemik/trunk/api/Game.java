package api;
import java.io.Serializable;

public class Game extends AbstractGame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static volatile Game instance = null;

	public Game(Player player1, Player player2) {
		super(player1, player2);
	}

	public static Game getInstance(Player player1, Player player2) {
		if (instance == null) {
			synchronized (AIGame.class) {
				if (instance == null) {
					instance = new Game(player1, player2);
				}
			}
		}
		return instance;
	}
//        public void switchPlayTurns() {
//		try {
//			super.getCurrentP().setSelected(new ArrayList<>());
//			if (super.getPlayer1().compareTo(super.getCurrentP()) == 0) {
//				//currentP = super.getPlayer1();
//                                super.setCurrentP((Player) super.getPlayer2());
//				//guest = this.player1;
//                                super.setGuest((Player) super.getPlayer1());
//			} else {
//				super.setCurrentP((Player) super.getPlayer1());
//				super.setGuest((Player) super.getPlayer2());
//			}
//			// play_count--;
//			//currentP.setTurn(true);
//                        super.getCurrentP().setTurn(true);
//		} catch (NullPointerException e) {
//			JOptionPane.showMessageDialog(null,
//					"in switchPlayTurns: " + e.getMessage());
//		}
//	}
}
