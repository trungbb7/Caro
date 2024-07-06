// StrategyEasyBot.java
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyEasyBot implements StrategyBot, Observer {

	private Subject game;

	public StrategyEasyBot(Subject game) {
		this.game = game;
		this.game.registerObserver(this);
	}

	@Override
	public int[] makeMove(int[][] board) {
		List<int[]> possibleMoves = generateMoves(board);
		Random random = new Random();
		return possibleMoves.get(random.nextInt(possibleMoves.size()));
	}

	private List<int[]> generateMoves(int[][] board) {
		List<int[]> possibleMoves = new ArrayList<>();
		int size = board.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					possibleMoves.add(new int[]{i, j});
				}
			}
		}
		return possibleMoves;
	}

	@Override
	public void update() {
		Game game = (Game) this.game;
		if (game.getState() == Game.CONTINUE_STATE) {
			if (game.getTurn() == Game.BOT_TURN) {
				int[] position = makeMove(game.getBoard().getMatrixCells());
				if (position[0] != -1) {
					int y = position[0];
					int x = position[1];
					game.handleBotAction(y, x);
				}
			}
		}
	}
}
