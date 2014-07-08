/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controler;

import agents.JkBot;
import api.AIGame;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.BoardFrame;

/**
 *
 * @author admin
 */
public class AgentMoveRunnable implements Runnable{

    @Override
    public void run() {
        try {
            AIGame game = (AIGame) JKemik.game;
            JkBot bot = (JkBot) game.getMachine();
            Thread.sleep(DELAY);
            if(bot.play(game)){
                Thread.sleep(DELAY);
                BoardFrame.grid.repaint();
            }
            //To change body of generated methods, choose Tools | Templates.
            //throw new UnsupportedOperationException("Not supported yet."); 
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private static final int DELAY = 1000;
}
