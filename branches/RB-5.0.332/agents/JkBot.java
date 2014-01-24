package agents;
import java.awt.*;

//import api.AbstractPlayer;
import api.Player;


public class JkBot extends Player implements AgentAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean engage = false;
	public JkBot(Color color, String name) {
		super(color, name);
		this.setAi(true);
	}
	@Override
	public boolean play() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isEngage() {
		return engage;
	}
	public void setEngage(boolean engage) {
		this.engage = engage;
	}

}
