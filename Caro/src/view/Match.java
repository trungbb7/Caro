package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MatchButtonController;
import controller.GameController;
import model.Game;
import model.Observer;

public class Match extends JPanel implements Observer {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public Match() {
		Game.resetInstance();
		Game game = Game.getInstance();
		int size = game.getBoardType();
		game.registerObserver(this);
		this.setLayout(new BorderLayout());
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(new BorderLayout());
		JPanel state = new State();
		wrapPanel.add(state, BorderLayout.NORTH);
		JPanel board = new JPanel();
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

		public Top() {
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
			Game game = Game.getInstance();
			String level = "Easy";
			if (game.getLevel() == 2) {
				level = "Hard";
			}
			levelL.setText("Level: " + level);
			typeL.setText("Board type: " + game.getBoardType() + "x" + game.getBoardType());
			this.add(levelL);
			this.add(typeL);
		}
	}

	@Override
	public void update() {
		Game game = Game.getInstance();
		if (game.getState() == Game.continueState) {
			for (int y = 0; y < game.getBoardType(); y++) {
				for (int x = 0; x < game.getBoardType(); x++) {
					Cell cell = game.getBoard().getCell(y, x);
					if (cell.tick == Game.playerTick) {
						cell.setText(Game.playerSign);
						cell.setForeground(Game.playerColor);
					} else if (cell.tick == Game.botTick) {
						cell.setText(Game.botSign);
						cell.setForeground(Game.botColor);
					}
				}
			}

			if (game.checkFinish()) {
				UI ui = UI.getInstance();
				showWinningPositions();
				String[] options = { "Close", "Home", "Replay" };
				int selection = 0;
				if (game.getWinner() == Game.player) {
					selection = JOptionPane.showOptionDialog(null, "You won", "Congratulations!",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else if (game.getWinner() == Game.bot) {
					selection = JOptionPane.showOptionDialog(null, "You lost", "Opps!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				} else if (game.getWinner() == Game.draw) {
					selection = JOptionPane.showOptionDialog(null, "Draw", "Opps!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
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
		Color color = Game.playerColor;
		if (game.getWinner() == Game.bot) {
			color = Game.botColor;
		}
		for (int i = 0; i < game.getCompletedCell().length; i++) {
			int[] position = game.getCompletedCell()[i];
			game.getBoard().getCell(position[0], position[1]).setBorderColor(color);
			;
		}
	}
}