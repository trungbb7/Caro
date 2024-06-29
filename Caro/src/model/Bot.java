package model;

import java.util.ArrayList;
import java.util.List;

public class Bot implements Observer {
	private int player; // Mã số người chơi của bot (1 hoặc -1)
	private int opponent;
	private Subject game;
	private int level;

	public Bot(Subject game, int player, int level) {
		this.player = player;
		this.opponent = -player;
		this.game = game;
		this.level = level;
		this.game.registerObserver(this);

	}

	public int[] makeMove(int[][] board) {
		return minimax(board, this.level, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
	}

	private int[] minimax(int[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
		List<int[]> possibleMoves = generateMoves(board);

		if (depth == 0 || isGameOver(board)) {
			int score = evaluate(board);
			return new int[] { -1, -1, score };
		}

		int[] bestMove = new int[3];
		int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		for (int[] move : possibleMoves) {
			int row = move[0];
			int col = move[1];

			board[row][col] = maximizingPlayer ? player : opponent;
			int[] currentMoveScore = minimax(board, depth - 1, alpha, beta, !maximizingPlayer);
			board[row][col] = 0; // Undo move

			if (maximizingPlayer) {
				if (currentMoveScore[2] > bestScore) {
					bestScore = currentMoveScore[2];
					bestMove[0] = move[0];
					bestMove[1] = move[1];
					alpha = Math.max(alpha, bestScore);
				}
			} else {
				if (currentMoveScore[2] < bestScore) {
					bestScore = currentMoveScore[2];
					bestMove[0] = move[0];
					bestMove[1] = move[1];
					beta = Math.min(beta, bestScore);
				}
			}

			if (beta <= alpha) {
				break; // Alpha-beta pruning
			}
		}

		bestMove[2] = bestScore;
		return bestMove;
	}

	private boolean isGameOver(int[][] board) {

		boolean result = hasPlayerWon(board, player) || hasPlayerWon(board, opponent) || isBoardFull(board);
		if (result) {
		}
		return result;
	}

	private boolean hasPlayerWon(int[][] board, int player) {
		int size = board.length;

		// Kiểm tra các hàng và cột
		for (int i = 0; i < size; i++) {
			for (int j = 0; j <= size - 5; j++) { // chỉ cần xét đến cột size - 5 để tránh vượt quá phạm vi
				boolean rowWin = true;
				boolean colWin = true;
				for (int k = 0; k < 5; k++) {
					if (board[i][j + k] != player) { // Kiểm tra hàng ngang
						rowWin = false;
					}
					if (board[j + k][i] != player) { // Kiểm tra cột dọc
						colWin = false;
					}
				}
				if (rowWin || colWin) {
					return true;
				}
			}
		}

		// Kiểm tra các đường chéo chính (\)
		for (int i = 0; i <= size - 5; i++) { // chỉ cần xét đến hàng size - 5 để tránh vượt quá phạm vi
			for (int j = 0; j <= size - 5; j++) { // chỉ cần xét đến cột size - 5 để tránh vượt quá phạm vi
				boolean diagonalWin = true;
				for (int k = 0; k < 5; k++) {
					if (board[i + k][j + k] != player) { // Kiểm tra đường chéo chính
						diagonalWin = false;
					}
				}
				if (diagonalWin) {
					return true;
				}
			}
		}

		// Kiểm tra các đường chéo phụ (/)
		for (int i = 0; i <= size - 5; i++) { // chỉ cần xét đến hàng size - 5 để tránh vượt quá phạm vi
			for (int j = 4; j < size; j++) { // bắt đầu từ cột 4 để đảm bảo không vượt quá phạm vi
				boolean diagonalWin = true;
				for (int k = 0; k < 5; k++) {
					if (board[i + k][j - k] != player) { // Kiểm tra đường chéo phụ
						diagonalWin = false;
					}
				}
				if (diagonalWin) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isBoardFull(int[][] board) {
		int size = board.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private List<int[]> generateMoves(int[][] board) {
		List<int[]> possibleMoves = new ArrayList<>();
		int size = board.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					possibleMoves.add(new int[] { i, j });
				}
			}
		}
		return possibleMoves;
	}

	private int evaluate(int[][] board) {
		int score = 0;
		score += evaluateLines(board, player); // Điểm của người chơi
		score -= evaluateLines(board, opponent); // Điểm của đối thủ
		return score;
	}

	private int evaluateLines(int[][] board, int player) {
		int size = board.length;
		int[] lineWeights = { 0, 1, 10, 100, 1000 }; // Độ ưu tiên theo độ dài đường ăn

		int totalScore = 0;

		// Các hướng kiểm tra đường ăn (hàng ngang, cột dọc, đường chéo chính, đường
		// chéo phụ)
		int[][] directions = { { 0, 1 }, // Right
				{ 1, 0 }, // Down
				{ 1, 1 }, // Diagonal (\)
				{ 1, -1 } // Diagonal (/)
		};

		// Duyệt qua từng ô trên bàn cờ để kiểm tra các đường ăn
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int[] dir : directions) {
					int dRow = dir[0];
					int dCol = dir[1];

					// Đếm độ dài đường ăn và các ô trống trước/sau đường ăn
					int length = 0;
					int emptyBefore = 0;
					int emptyAfter = 0;
					boolean blockedBefore = false;
					boolean blockedAfter = false;

					// Kiểm tra từ ô hiện tại theo hướng dir
					int r = i;
					int c = j;
					while (r >= 0 && r < size && c >= 0 && c < size && (board[r][c] == player || board[r][c] == 0)) {
						if (board[r][c] == player) {
							length++;
						} else if (length == 0) {
							emptyBefore++;
						} else {
							emptyAfter++;
							if (board[r][c] == -player) {
								blockedAfter = true; // Đường bị chặn sau
							}
						}
						r += dRow;
						c += dCol;
					}

					// Đánh giá độ lợi hại của đường ăn
					if (length > 0) {
						int lineScore = 0;
						if (length >= 5) {
							lineScore = lineWeights[4]; // Đường ăn 5
						} else {
							lineScore = lineWeights[length - 1]; // Đường ăn từ 1 đến 4
							if (emptyBefore == 0 || blockedBefore) {
								lineScore /= 2; // Đường bị chặn hoặc không có ô trống trước
							}
							if (emptyAfter == 0 || blockedAfter) {
								lineScore /= 2; // Đường bị chặn hoặc không có ô trống sau
							}
						}
						totalScore += lineScore;
					}
				}
			}
		}

		return totalScore;
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
					game.getBoard().signCell(y, x, Game.BOT_TICK);
					game.setTurn(Game.PLAYER_TURN);
					game.notifyObserver();
				}
			}
		}
	}
}
