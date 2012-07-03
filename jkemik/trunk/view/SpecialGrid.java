package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import utilities.Tools;
import api.Game;
import api.Point;
import controler.JKemik;

public class SpecialGrid extends Grid{

	public SpecialGrid(int squareSize) {
		super(squareSize);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g) {

		Grid.g2 = (Graphics2D) g;
		drawGrid();
		try {
			drawCursor(hl_x, hl_y, GRID_LINE_COL);

			/* Find the closest point to the cursor */
			closestTo(Grid.x, Grid.y, (int) Grid.squareSize);
			Point temp = makeDrawable(Grid.x, Grid.y);
			BoardFrame.print_point.setText(""
					+ (new Point(hl_x, hl_y)).toString());
			highLightDot(ccolor);
			Game game = JKemik.game;
			if (this.mouseclicked && game.getCurrentP().isTurn()) {
				if (!Tools.containPoint(temp, game.getCurrentP().getPloted())
						&& !Tools.containPoint(temp, game.getGuest()
								.getPloted())
						&& !Tools.containPoint(temp, game.getDeadDots())) {

					drawCircle(temp, game.getCurrentP().getColor());
					game.getCurrentP().getPloted().add(temp);
					game.setEmbuche_on(true);
					game.setPlayFlag();
					game.getCurrentP().setTurn(false);

					BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
					BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
					mouseMove = false;
				}
				this.mouseclicked = false;
			}

			if (undo) {
				if (game.undo()) {
					super.unDraw(game.getLastp());
					BoardFrame.p1panel.updatePlayerPanel(game.getPlayer1());
					BoardFrame.p2panel.updatePlayerPanel(game.getPlayer2());
				}
				undo = false;
			}
			if (game.isEmbuche_on()) {
				embush();
			}
			if (JKemik.settings_t.isAutoPass()
					&& game.getCurrentP().getPlay_flag() == 1) {
				JKemik.game.switchPlayTurns();
				Grid.setCcolor(JKemik.game.getCurrentP().getColor());
			}
		} catch (Exception e) {
			System.out.println("Error in paint: " + e.getMessage());
		}

	}

}
