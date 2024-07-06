package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Game;
import view.Cell;

public class GameController extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		Game game = Game.getInstance();
		if (game.getTurn() == Game.PLAYER_TURN) {
			Cell cell = (Cell) e.getSource();
			if (cell.tick == Game.NON_TICK) {
				game.handlePlayerAction(cell.y, cell.x);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Game game = Game.getInstance();
		Cell cell = (Cell) e.getSource();
		if (game.getState() == Game.CONTINUE_STATE) {
			if (cell.tick == Game.NON_TICK) {
				cell.setText(Game.PLAYER_SIGN);
				cell.setForeground(Game.PLAYER_COLOR);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Game game = Game.getInstance();
		Cell cell = (Cell) e.getSource();
		if (game.getState() == Game.CONTINUE_STATE) {
			if (cell.tick == Game.NON_TICK) {
				cell.setText("");

			}
		}
	}

}
