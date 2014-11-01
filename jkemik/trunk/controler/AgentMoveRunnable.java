/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controler;
import Events.ViewEvents;
import agents.JkBot;
import api.AIGame;
import api.Point;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.BoardFrame;
import view.Grid;

/**
 *
 * @author admin
 */
public class AgentMoveRunnable implements Runnable{
    public AgentMoveRunnable(){
        aiMoveLock = new ReentrantLock();
    }

    @Override
    public void run() {
        aiMoveLock.lock();
        
        try {
           
            BoardFrame.progressB.setVisible(true);
            BoardFrame.progressB.setIndeterminate(true);

            AIGame game = (AIGame) JKemik.game;
            JkBot bot = (JkBot) game.getMachine();
            Thread.sleep(DELAY);
            Point move = bot.play(game);
            if(move != null){
                if (game.isEmbuche_on()) {
                    if (JKemik.settings_t.isAutoCapture()) {
                            Grid.cell = JKemik.embush(Grid.squareSize);
                            if(Grid.cell != null){
                                BoardFrame.grid.repaint();
                            } 
                    }
                }
            }
            System.out.println(bot.getAiStatus().toString());
            BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
            BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
            BoardFrame.updateBoardStatus();

            Grid.setRefresh(true);
            BoardFrame.displayGrid(true);
            BoardFrame.grid.repaint((int)move.getXC() - (int)Grid.squareSize * 2, (int)move.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
            
            Thread.sleep(DELAY);

            //Remove progress bar
            BoardFrame.progressB.setIndeterminate(false);
            BoardFrame.progressB.setVisible(false);
            
            System.err.println("about to check end of Game");
            
//            if (JKemik.checkEndGame()) {
//                JOptionPane.showMessageDialog(null, "" + JKemik.getEndingMessage(),
//                                " Win", JOptionPane.OK_OPTION);
//                BoardFrame.feedback(JKemik.getEndingMessage());
//                JKemik.game.setStatus(1);
//                JKemik.createGame(JKemik.template, JKemik.settings_t);
//                JKemik.settings_t.setGameSetupMode(true);
//                // Reset game exit label
//                BoardFrame.Game_status.setText("NEW");
//                BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
//                ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);
//
//                Grid.setRefresh(true);
//                BoardFrame.displayGrid(true);
//                BoardFrame.grid.repaint();
//            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            aiMoveLock.unlock();
        }
        //Thread.interrupted();
    }
    private static final int DELAY = 1000;
    private Lock aiMoveLock ;
    
}
