/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Events;

import api.AIGame;
import api.Point;
import controler.AgentMoveRunnable;
import controler.HumanMoveRunnable;
import controler.JKemik;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import view.BoardFrame;
import view.Grid;

/**
 * 
 * @author admin
 */
public class GridAIMouseListener implements MouseListener, MouseMotionListener {
	private final Grid grid;

	public GridAIMouseListener(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Get X and Y
            Grid.x = e.getX();
            Grid.y = e.getY();
            AIGame game = (AIGame) JKemik.getGame();
            HumanMoveRunnable hThread = new HumanMoveRunnable();
            AgentMoveRunnable mThread = new AgentMoveRunnable();		
            Thread ht = new Thread(hThread);
            Thread mt = new Thread(mThread);
            ExecutorService pool = Executors.newFixedThreadPool(2);
            pool.execute(ht);         
            pool.execute(mt);
     
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

            if (JKemik.getGame().getCollection().containsKey(temp.toString())) {
            } else {
                    Grid.setRefresh(true);
                    BoardFrame.displayGrid(true);
                    BoardFrame.grid.repaint();
                    BoardFrame.grid.repaint((int)temp.getXC() - (int)Grid.squareSize * 2, (int)temp.getYC() - (int)Grid.squareSize * 2, (int)Grid.squareSize * 4, (int)Grid.squareSize * 4);
                    Grid.mouseMove = true;
            }
	}
}
