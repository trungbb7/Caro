package model;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import view.Cell;

public class Game implements Subject {

	public static final int playerTick = 1;
	public static final int nonTick = 0;
	public static final int botTick = -1;

	public static final int playerTurn = 1;
	public static final int botTurn = 0;

	public static final int gameOverState = 0;
	public static final int continueState = 1;

	public static final int draw = 0;
	public static final int bot = -1;
	public static final int player = 1;

	public static final String playerSign = "X";
	public static final String botSign = "O";

	public static final Color playerColor = Color.RED;
	public static final Color botColor = Color.BLUE;

	public static final int hardBot = 2;
	public static final int easyBot = 1;

	private int boardType;
	private int level;

	private static Game uniqueInstance;
	private List<Observer> observers;
	private int turn;
	private int state;
	private int[][] completedCell;
	private int winner;
	private Board board;

	private Game() {
		loadConfig();
		this.board = new Board(boardType);
		this.observers = new ArrayList<>();
		this.turn = playerTurn;
		this.state = continueState;
		this.winner = draw;
		this.completedCell = new int[5][2];
//		new Bot(this);
		new HardBot(this, Game.botTick, level);

	}

	private void loadConfig() {
		try {
			File file = new File("src\\config");
			Scanner sc = new Scanner(file);
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1) {
					String level = sc.nextLine().split(":")[1];
					if (level.equals("easy")) {
						this.level = 1;
					} else if (level.equals("hard")) {
						this.level = 2;
					}
				} else if (count == 2) {
					String type = sc.nextLine().split(":")[1];
					this.boardType = Integer.parseInt(type);
				}
				count++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Game getInstance() {
		if (uniqueInstance == null) {
			synchronized (Game.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Game();
				}
			}
		}
		return uniqueInstance;
	}

	public static void resetInstance() {
		uniqueInstance = new Game();
	}

	public Cell getCell(int y, int x) {
		return this.board.getCell(y, x);
	}

	public Board getBoard() {
		return this.board;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int[][] getCompletedCell() {
		return completedCell;
	}

	public void setCompletedCell(int[][] completedCell) {
		this.completedCell = completedCell;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	private void finishHandler(int winner) {
		this.state = Game.gameOverState;
		this.winner = winner;
		String data = "";
		try {
			File file = new File("src\\data");
			Scanner sc = new Scanner(file);
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1 && winner == Game.player) {
					int win = Integer.parseInt(sc.nextLine().split(":")[1]);
					data += "win:" + (win + 1) + "\n";
				} else if (count == 2 && winner == Game.bot) {
					int draw = Integer.parseInt(sc.nextLine().split(":")[1]);
					data += "lose:" + (draw + 1) + "\n";
				} else if (count == 3 && winner == Game.draw) {
					int draw = Integer.parseInt(sc.nextLine().split(":")[1]);
					data += "daw:" + (draw + 1) + "\n";
				} else {
					data += sc.nextLine() + "\n";
				}
				count++;
			}
			sc.close();
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(data);
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkFinish() {
		for (int y = 0; y < board.getSize(); y++) {
			for (int x = 0; x < board.getSize(); x++) {
				if (board.getCell(y, x).tick == playerTick || board.getCell(y, x).tick == botTick) {
					int currentTick = board.getCell(y, x).tick;
					if ((x + 4) < board.getSize()) {
						boolean possible = true;
						int[][] completedC = new int[5][2];
						completedC[0][0] = y;
						completedC[0][1] = x;
						int count = 1;
						for (int i = x + 1; i < x + 5; i++) {
							if (board.getCell(y, i).tick != currentTick) {
								possible = false;
								break;
							} else {
								completedC[count][0] = y;
								completedC[count][1] = i;
								count++;
							}

						}
						if (possible) {
							completedCell = completedC;
							if (currentTick == botTick) {
								finishHandler(Game.bot);
							} else {
								finishHandler(Game.player);
							}
							return true;
						}
					}

					if ((y + 4) < board.getSize()) {
						boolean possible = true;
						int[][] completedC = new int[5][2];
						completedC[0][0] = y;
						completedC[0][1] = x;
						int count = 1;
						for (int i = y + 1; i < y + 5; i++) {
							if (board.getCell(i, x).tick != currentTick) {
								possible = false;
								break;
							} else {
								completedC[count][0] = i;
								completedC[count][1] = x;
								count++;
							}
						}
						if (possible) {
							completedCell = completedC;
							completedCell = completedC;
							if (currentTick == botTick) {
								finishHandler(Game.bot);
							} else {
								finishHandler(Game.player);
							}
							return true;
						}
					}

					if ((x + 4) < board.getSize() && (y + 4) < board.getSize()) {
						boolean possible = true;
						int[][] completedC = new int[5][2];
						completedC[0][0] = y;
						completedC[0][1] = x;
						int count = 1;
						for (int i = 1; i < 5; i++) {
							if (board.getCell(y + i, x + i).tick != currentTick) {
								possible = false;
								break;
							} else {
								completedC[count][0] = y + i;
								completedC[count][1] = x + i;
								count++;
							}
						}
						if (possible) {
							completedCell = completedC;
							if (currentTick == botTick) {
								finishHandler(Game.bot);
							} else {
								finishHandler(Game.player);
							}
							return true;
						}
					}

					if ((y + 4) < board.getSize() && (x - 4) >= 0) {
						boolean possible = true;
						int[][] completedC = new int[5][2];
						completedC[0][0] = y;
						completedC[0][1] = x;
						int count = 1;
						for (int i = 1; i < 5; i++) {
							if (board.getCell(y + i, x - i).tick != currentTick) {
								possible = false;
								break;
							} else {
								completedC[count][0] = y + i;
								completedC[count][1] = x - i;
								count++;
							}
						}
						if (possible) {
							completedCell = completedC;
							completedCell = completedC;
							if (currentTick == botTick) {
								finishHandler(Game.bot);
							} else {
								finishHandler(Game.player);
							}
							return true;
						}
					}

				}
			}
		}

		boolean isFull = true;
		for (int i = 0; i < boardType; i++) {
			for (int j = 0; j < boardType; j++) {
				if (this.board.getMatrixCells()[i][j] == 0) {
					isFull = false;
					break;
				}
			}
		}
		if (isFull) {
			finishHandler(Game.draw);
			return true;
		}

		return false;
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObserver() {
		for (Observer o : this.observers) {
			o.update();
		}

	}

}
