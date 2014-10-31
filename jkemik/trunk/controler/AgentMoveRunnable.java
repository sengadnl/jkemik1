/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controler;
import Events.ViewEvents;
import agents.JkBot;
import api.AIGame;
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
        boolean done = false;
        try {
           
            BoardFrame.progressB.setVisible(true);
            BoardFrame.progressB.setIndeterminate(true);

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
            System.out.println(bot.getAiStatus().toString());
            BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
            BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
            BoardFrame.updateBoardStatus();

            Grid.setRefresh(true);
            BoardFrame.displayGrid(true);
            BoardFrame.grid.repaint();
            Thread.sleep(DELAY);

            //Remove progress bar
            BoardFrame.progressB.setIndeterminate(false);
            BoardFrame.progressB.setVisible(false);

            if (JKemik.checkEndGame()) {
                JOptionPane.showMessageDialog(null, "" + JKemik.getEndingMessage(),
                                " Win", JOptionPane.OK_OPTION);
                BoardFrame.feedback(JKemik.getEndingMessage());
                JKemik.game.setStatus(1);
                JKemik.createGame(JKemik.template, JKemik.settings_t);
                JKemik.settings_t.setGameSetupMode(true);
                // Reset game exit label
                BoardFrame.Game_status.setText("NEW");
                BoardFrame.uiLooksUpdate(JKemik.settings_t, JKemik.template);
                ViewEvents.uiEventUpdates(JKemik.settings_t, JKemik.template);

                Grid.setRefresh(true);
                BoardFrame.displayGrid(true);
                BoardFrame.grid.repaint();
            }
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
