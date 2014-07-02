/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Events;

import api.AbstractGame;
import api.Player;
import api.Point;
import controler.JKemik;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import view.BoardFrame;
import view.Grid;

/**
 *
 * @author admin
 */
public class GridAIMouseListener implements MouseListener, MouseMotionListener{
    private final Grid grid;

    public GridAIMouseListener(Grid grid) {
        this.grid = grid;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

	
        AbstractGame game = JKemik.game;
        Player current = (Player) game.getCurrentP();
        // Allow mouse click
        Grid.mouseclicked = true;

        // Get X and Y
        Grid.x = e.getX();
        Grid.y = e.getY();

        // Guess player's point
        Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
        Point temp = new Point(Grid.x, Grid.y);

        if (game.getCurrentP().isTurn()) {
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
                        game.getCollection().put(temp.toString(), temp);
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
        }
        
        BoardFrame.progressB.setVisible(true);
        BoardFrame.progressB.setIndeterminate(true);
//        if (game.isEmbuche_on()) {
//                if (JKemik.settings_t.isAutoCapture()) {
//                        Grid.cell = JKemik.embush(Grid.squareSize);
//                        BoardFrame.grid.repaint();
//                }
//        }
        if (game.getCurrentP().isTurn()) {
                this.grid.setMouseclicked(true);
        }
        BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
        BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());

        BoardFrame.updateBoardStatus();

        Grid.setRefresh(true);
        BoardFrame.displayGrid(true);

        BoardFrame.grid.repaint((int)temp.getXC() - (int)Grid.squareSize * 2, (int)temp.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);

        BoardFrame.progressB.setIndeterminate(false);
        BoardFrame.progressB.setVisible(false);
   
		
	}

    @Override
	public void mouseEntered(MouseEvent e) {

	}

    @Override
	public void mouseExited(MouseEvent e) {

	}

    @Override
	public void mousePressed(MouseEvent e) {

	}

    @Override
	public void mouseReleased(MouseEvent e) {

	}

    @Override
	public void mouseDragged(MouseEvent arg0) {

	}

    @Override
	public void mouseMoved(MouseEvent e) {
		
            Grid.mouseMove = true;
            Grid.x = e.getX();
            Grid.y = e.getY();

            Grid.closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
            Point temp = Grid.makeDrawable(Grid.x, Grid.y);

            if (JKemik.game.getCollection().containsKey(temp.toString())) {
            } else {     
                Grid.setRefresh(true);
                BoardFrame.displayGrid(true);
                BoardFrame.grid.repaint();
                Grid.mouseMove = true;
                
            }
	}
}
