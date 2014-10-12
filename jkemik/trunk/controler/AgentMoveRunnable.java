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
import view.Grid;

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
                if (game.isEmbuche_on()) {
                    if (JKemik.settings_t.isAutoCapture()) {
                            Grid.cell = JKemik.embush(Grid.squareSize);
                            BoardFrame.grid.repaint();
                    }
                }
            }
            BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
            BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
            BoardFrame.updateBoardStatus();
            Thread.sleep(DELAY);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private static final int DELAY = 1000;
}
