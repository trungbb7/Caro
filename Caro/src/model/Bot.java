package model;

import java.util.Random;

public class Bot implements Observer {

	private Subject game;

	public Bot(Subject game) {
		this.game = game;
		this.game.registerObserver(this);
	}

	public int[] play() {
		Game game = (Game) this.game;
		Board board = game.getBoard();
		int[] result = new int[2];
		Random rd = new Random();
		while (true) {
			int y = rd.nextInt(0, board.getSize());
			int x = rd.nextInt(0, board.getSize());
			if (board.getCell(y, x).tick == Game.nonTick) {
				result[0] = x;
				result[1] = y;
				break;
			}
		}
		return result;
	}

	@Override
	public void update() {
		Game game = (Game) this.game;
		if (game.getState() == Game.continueState) {
			if (game.getTurn() == Game.botTurn) {
				int[] position = play();
				int y = position[0];
				int x = position[1];
				game.getBoard().signCell(y, x, Game.botTick);
				game.setTurn(Game.playerTurn);
				game.notifyObserver();
			}
		}
	}

}
