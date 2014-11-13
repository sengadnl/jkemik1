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
            /*Give bot total cursor control*/
            BoardFrame.getGrid().removeMouseListener(ViewEvents.AIgridListener);
            BoardFrame.getGrid().removeMouseMotionListener(ViewEvents.AIgridListener);
            
            AIGame game;JkBot bot;
            
            game = (AIGame) JKemik.getGame();
            bot = (JkBot) game.getMachine();
            
            /*Give a little more time to the human thread to finish*/
            Thread.sleep(DELAY);
            
            /*Play only if the it is bot's turn and if bot has not played yet*/
            if(bot.isTurn() && bot.getPlay_flag() == 0){
                BoardFrame.progressB.setVisible(true);
                BoardFrame.progressB.setIndeterminate(true);

                Point move;
                /*Bot decedes where to play*/
                move = bot.play((AIGame) game);
                
                /*Bot moves the cursor*/
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
                    /*Keep cursor where bot played for a little while*/
                    Thread.sleep(DELAY);
                    
                    /*Check for captures*/
                    if (game.isEmbuche_on()) {
                        if (JKemik.settings_t.isAutoCapture()) {
                                Grid.cell = JKemik.embush(Grid.squareSize);
                                if(Grid.cell != null){
                                    BoardFrame.grid.repaint();
                                } 
                        }
                    }
                    
                    /*Draw move*/
                    Grid.setRefresh(true);
                    BoardFrame.displayGrid(true);
                    BoardFrame.grid.repaint((int)move.getXC() - (int)Grid.squareSize * 2, (int)move.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
                }
                BoardFrame.progressB.setIndeterminate(false);
                BoardFrame.progressB.setVisible(false);
     
                game.switchPlayTurns();
                
                /*Check the end of the game*/
                if (JKemik.checkEndGame()) {
                    JOptionPane.showMessageDialog(null, "" + JKemik.getEndingMessage(),
                                    " Win", JOptionPane.OK_OPTION);
                    BoardFrame.feedback(JKemik.getEndingMessage());
                    JKemik.getGame().setStatus(1);
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
                
                /*Update scores*/
                BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
                BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
                BoardFrame.updateBoardStatus();
            }
            /*Return cursor control to human*/
            BoardFrame.getGrid().addMouseListener(ViewEvents.AIgridListener);
            BoardFrame.getGrid().addMouseMotionListener(ViewEvents.AIgridListener);
        }catch(NullPointerException ex){
            System.out.println(ex.getMessage() + ": AgentMoveRunnable");
        }catch (InterruptedException ex) {
            Logger.getLogger(AgentMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            aiMoveLock.unlock();
        }
        //Thread.interrupted();
    }
    private static final int DELAY = 300;
    private final Lock aiMoveLock ;
    
}
