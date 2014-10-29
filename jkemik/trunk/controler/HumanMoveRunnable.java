/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controler;

import agents.JkBot;
import api.AIGame;
import api.Player;
import api.Point;
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
public class HumanMoveRunnable implements Runnable{
    public HumanMoveRunnable(){
        humanMoveLock = new ReentrantLock();
    }
    @Override
    public void run() {
        humanMoveLock.lock();
         try {
            AIGame game = (AIGame) JKemik.game;
            Player current;
            current = (Player) game.getHuman();
            JkBot bot = (JkBot) game.getMachine();
            // Allow mouse click
            Grid.mouseclicked = true;

            // Guess player's point
            Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
            Point temp = new Point(Grid.x, Grid.y);


            if (!game.getCollection().containsKey(temp.toString())) {
                    Grid.plotPoint = true;

                    //BoardFrame.grid.repaint();

                    // Mark point as played
                    temp.setStatus(Point.PLAYED);

                    // Mark point as belonging to current player
                    temp.setId(current.getId());

                    // Remember last play
                    current.setLatestP(temp);

                    // Add to the board
                    game.put(temp.toString(), temp);
                    game.getCurrentP().rememberPoint(temp,
                                    JKemik.settings_t.getBacktrackingDistance());
                    game.setPlay_count(game.getPlay_count() - 1);
                    game.setEmbuche_on(true);

                    // Setting turn
                    game.setPlayFlag();
                    game.getCurrentP().setTurn(false);
                    Grid.mouseMove = false;
                    BoardFrame.feedback(game.getCurrentP().getName() + " "
                                    + BoardFrame.messages.getString("feedback4"));
            }


            BoardFrame.progressB.setVisible(true);
            BoardFrame.progressB.setIndeterminate(true);
            if (game.isEmbuche_on()) {
                    if (JKemik.settings_t.isAutoCapture()) {
                            Grid.cell = JKemik.embush(Grid.squareSize);
                            BoardFrame.grid.repaint();
                            game.getBoardStatus().updateStatus();
                    }
            }
            if (game.getCurrentP().isTurn()) {
                    BoardFrame.grid.setMouseclicked(true);
            }
            BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
            BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
            BoardFrame.updateBoardStatus();

            Grid.setRefresh(true);
            BoardFrame.displayGrid(true);
            BoardFrame.grid.repaint((int)temp.getXC() - (int)Grid.squareSize * 2, (int)temp.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);

            BoardFrame.progressB.setIndeterminate(false);
            BoardFrame.progressB.setVisible(false);
       
       
            Thread.sleep(DELAY);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(HumanMoveRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            humanMoveLock.unlock();
        }
    }
    private static final int DELAY = 1000;
    private Lock humanMoveLock ;
}
