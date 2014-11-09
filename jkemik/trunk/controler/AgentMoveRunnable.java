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
import java.util.ArrayList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            BoardFrame.getGrid().removeMouseListener(ViewEvents.AIgridListener);
            BoardFrame.getGrid().removeMouseMotionListener(ViewEvents.AIgridListener);
            BoardFrame.progressB.setVisible(true);
            BoardFrame.progressB.setIndeterminate(true);

            AIGame game = (AIGame) JKemik.getGame();
            JkBot bot = (JkBot) game.getMachine();
            Thread.sleep(DELAY);
            Point move = bot.play((AIGame) game);
            System.err.println("MOVE IS " + move);
            if(move != null){
                ArrayList<Point> path = bot.moveCursorTo(new Point(0.0,0.0), move, Grid.squareSize);
                for(Point m: path){
                    Grid.x = m.getXC();
                    Grid.y = m.getYC();
                    Grid.setRefresh(true);
                    BoardFrame.displayGrid(true);
                    BoardFrame.grid.repaint((int)Grid.x - (int)Grid.squareSize * 2, (int)Grid.y - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
                    Grid.mouseMove = true;
                    Thread.sleep(20);
                }
                Thread.sleep(DELAY);
                if (game.isEmbuche_on()) {
                    //System.out.println("Embush is on....");
                    if (JKemik.settings_t.isAutoCapture()) {
                         //System.out.println("About to embush....");
                            Grid.cell = JKemik.embush(Grid.squareSize);
                            if(Grid.cell != null){
                                //System.out.println("Cell was not empty....");
                                BoardFrame.grid.repaint();
                            } 
                    }
                }
                Grid.setRefresh(true);
                BoardFrame.displayGrid(true);
                BoardFrame.grid.repaint((int)move.getXC() - (int)Grid.squareSize * 2, (int)move.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
            }
            
            BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
            BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
            BoardFrame.updateBoardStatus();

            //Thread.sleep(DELAY);

            //Remove progress bar
            BoardFrame.progressB.setIndeterminate(false);
            BoardFrame.progressB.setVisible(false);
            BoardFrame.getGrid().addMouseListener(ViewEvents.AIgridListener);
            BoardFrame.getGrid().addMouseMotionListener(ViewEvents.AIgridListener);
            

                        System.err.println("Switching turns .....");
                        game.switchPlayTurns();
       
        }catch(NullPointerException ex){
            System.out.println(ex.getMessage() + ": AgentMoveRunnable");
        }catch (InterruptedException ex) {
            Logger.getLogger(AgentMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            aiMoveLock.unlock();
        }
        //Thread.interrupted();
    }
    private static final int DELAY = 500;
    private final Lock aiMoveLock ;
    
}
