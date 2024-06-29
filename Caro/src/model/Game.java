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

	public static final int PLAYER_TICK = 1;
	public static final int NON_TICK = 0;
	public static final int BOT_TICK = -1;

	public static final int PLAYER_TURN = 1;
	public static final int BOT_TURN = 0;

	public static final int GAME_OVER_STATE = 0;
	public static final int CONTINUE_STATE = 1;

	public static final int DRAW = 0;
	public static final int BOT = -1;
	public static final int PLAYER = 1;

	public static final String PLAYER_SIGN = "X";
	public static final String BOT_SIGN = "O";

	public static final Color PLAYER_COLOR = new Color(24, 188, 156);
	public static final Color BOT_COLOR = new Color(238, 102, 119);
	
	public static final Color BACKGROUND_COLOR = new Color(16, 27, 39);
	public static final Color TRANSPARENT_COLOR = new Color(0,0,0,0);
	
	public static final int HARD_BOT = 2;
	public static final int EASY_BOT = 1;

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
		this.turn = PLAYER_TURN;
		this.state = CONTINUE_STATE;
		this.winner = DRAW;
		this.completedCell = new int[5][2];
		// new Bot(this);
		new Bot(this, Game.BOT_TICK, level);

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

	public void handlePlayerAction(int y, int x) {
		this.board.signCell(y, x, Game.PLAYER_TICK);
		setTurn(Game.BOT_TURN);
		notifyObserver();
	}
	public void handleBotAction(int y, int x) {
		this.board.signCell(y, x, Game.BOT_TICK);
		setTurn(Game.PLAYER_TURN);
		notifyObserver();
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
		this.state = Game.GAME_OVER_STATE;
		this.winner = winner;
		String data = "";
		try {
			File file = new File("src\\data");
			Scanner sc = new Scanner(file);
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1 && winner == Game.PLAYER) {
					int win = Integer.parseInt(sc.nextLine().split(":")[1]);
					data += "win:" + (win + 1) + "\n";
				} else if (count == 2 && winner == Game.BOT) {
					int draw = Integer.parseInt(sc.nextLine().split(":")[1]);
					data += "lose:" + (draw + 1) + "\n";
				} else if (count == 3 && winner == Game.DRAW) {
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
				if (board.getCell(y, x).tick == PLAYER_TICK
						|| board.getCell(y, x).tick == BOT_TICK) {
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
							if (currentTick == BOT_TICK) {
								finishHandler(Game.BOT);
							} else {
								finishHandler(Game.PLAYER);
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
							if (currentTick == BOT_TICK) {
								finishHandler(Game.BOT);
							} else {
								finishHandler(Game.PLAYER);
							}
							return true;
						}
					}

					if ((x + 4) < board.getSize()
							&& (y + 4) < board.getSize()) {
						boolean possible = true;
						int[][] completedC = new int[5][2];
						completedC[0][0] = y;
						completedC[0][1] = x;
						int count = 1;
						for (int i = 1; i < 5; i++) {
							if (board.getCell(y + i,
									x + i).tick != currentTick) {
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
							if (currentTick == BOT_TICK) {
								finishHandler(Game.BOT);
							} else {
								finishHandler(Game.PLAYER);
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
							if (board.getCell(y + i,
									x - i).tick != currentTick) {
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
							if (currentTick == BOT_TICK) {
								finishHandler(Game.BOT);
							} else {
								finishHandler(Game.PLAYER);
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
			finishHandler(Game.DRAW);
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
