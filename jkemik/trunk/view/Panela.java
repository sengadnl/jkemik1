package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panela extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//JKIcon ex;
	public Panela(){
		createPanel();
	}
	public void createPanel(){
	//	ex = new JKIcon("media/exit-idle.gif", "");
	//	add(ex.createIcon());
	}
	public static void main(String args[]){
		JFrame frame= new JFrame("Panel A");
		frame.setSize(500, 500);
		Panela panel = new Panela();
		frame.add(panel);
		frame.setVisible(true);
	}

}
