package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MatchButtonController;
import controller.GameController;
import model.BoardViewFactory;
import model.Game;
import model.Observer;

public class Match extends JPanel implements Observer {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Color textColor = new Color(203, 207, 212);
	public Match() {
		List<Observer> gameBoardList = new ArrayList<>();
		for (Observer observer : Game.getInstance().getObservers()) {
			if (observer instanceof GameBoard) {
				gameBoardList.add(observer);
			}
		}
		Game.resetInstance();
		Game game = Game.getInstance();
		for (Observer o : gameBoardList) {
			game.registerObserver(o);
		}
		int size = game.getBoardType();
		game.registerObserver(this);
		this.setLayout(new BorderLayout());
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(new BorderLayout());
		JPanel state = new State();
		wrapPanel.add(state, BorderLayout.NORTH);

		// simple factory
		JPanel board = BoardViewFactory.createBoardView(game.boardTheme);

		board.setLayout(new GridLayout(size, size, 0, 0));
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Cell cell = game.getCell(y, x);
				GameController gc = new GameController();
				cell.addMouseListener(gc);
				board.add(cell);
			}
		}
		wrapPanel.add(board, BorderLayout.CENTER);
		this.add(wrapPanel);
	}

	private void addTop() {
		JPanel top = new Top();
		this.add(top, BorderLayout.NORTH);
		UI ui = UI.getInstance();
		ui.setVisible(false);
		ui.setVisible(true);
	}

	private class Top extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Top() {
			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			MouseListener btnController = new MatchButtonController();
			JButton homeBtn = new JButton("Home");
			homeBtn.setActionCommand("Home");
			homeBtn.addMouseListener(btnController);
			JButton replayBtn = new JButton("Replay");
			replayBtn.setActionCommand("Replay");
			replayBtn.addMouseListener(btnController);
			this.add(homeBtn);
			this.add(replayBtn);
			this.setBackground(new Color(16, 27, 39));
		}
	}

	private class State extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private State() {
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
			JLabel levelL = new JLabel();
			JLabel typeL = new JLabel();
			levelL.setForeground(textColor);
			levelL.setFont(new Font("roboto", Font.BOLD, 16));
			typeL.setForeground(textColor);
			typeL.setFont(new Font("roboto", Font.BOLD, 16));

			Game game = Game.getInstance();
			String level = "Easy";

			if (game.getLevel() == 3) {
				level = "Hard";
			} else if (game.getLevel() == 2) {
				level = "Medium";
			}
			levelL.setText("Level: " + level);
			typeL.setText("Board type: " + game.getBoardType() + "x"
					+ game.getBoardType());
			this.add(levelL);
			this.add(typeL);
			this.setBackground(new Color(16, 27, 39));
		}
	}

	@Override
	public void update() {
		Game game = Game.getInstance();
		if (game.getState() == Game.CONTINUE_STATE) {
			for (int y = 0; y < game.getBoardType(); y++) {
				for (int x = 0; x < game.getBoardType(); x++) {
					Cell cell = game.getBoard().getCell(y, x);
					if (cell.tick == Game.PLAYER_TICK) {
						cell.setText(Game.PLAYER_SIGN);
						cell.setForeground(Game.PLAYER_COLOR);
					} else if (cell.tick == Game.BOT_TICK) {
						cell.setText(Game.BOT_SIGN);
						cell.setForeground(Game.BOT_COLOR);
					}
				}
			}

			if (game.checkFinish()) {
				UI ui = UI.getInstance();
				showWinningPositions();
				String[] options = {"Close", "Home", "Replay"};
				int selection = 0;
				if (game.getWinner() == Game.PLAYER) {
					selection = JOptionPane.showOptionDialog(null, "You won",
							"Congratulations!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else if (game.getWinner() == Game.BOT) {
					selection = JOptionPane.showOptionDialog(null, "You lost",
							"Opps!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				} else if (game.getWinner() == Game.DRAW) {
					selection = JOptionPane.showOptionDialog(null, "Draw",
							"Opps!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[0]);
				}
				if (selection == 1) {
					ui.backHome();
				} else if (selection == 2) {
					ui.newGame();
				} else {
					addTop();
				}
			}

		}
	}

	private void showWinningPositions() {
		Game game = Game.getInstance();
		Color color = Game.PLAYER_COLOR;
		if (game.getWinner() == Game.BOT) {
			color = Game.BOT_COLOR;
		}
		for (int i = 0; i < game.getCompletedCell().length; i++) {
			int[] position = game.getCompletedCell()[i];
			game.getBoard().getCell(position[0], position[1])
					.setBorderColor(color);
		}
	}
}
