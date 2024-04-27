package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Game;
import view.Cell;

public class GameController implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Game game = Game.getInstance();
		if (game.getTurn() == Game.playerTurn) {
			Cell cell = (Cell) e.getSource();
			if (cell.tick == Game.nonTick) {
				game.getBoard().signCell(cell.y, cell.x, Game.playerTick);
				game.setTurn(Game.botTurn);
			}
			game.notifyObserver();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Game game = Game.getInstance();
		Cell cell = (Cell) e.getSource();
		if (game.getState() == Game.continueState) {
			if (cell.tick == Game.nonTick) {
				cell.setText(Game.playerSign);
				cell.setForeground(Game.playerColor);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Game game = Game.getInstance();
		Cell cell = (Cell) e.getSource();
		if (game.getState() == Game.continueState) {
			if (cell.tick == Game.nonTick) {
				cell.setText("");

			}
		}
	}

}
