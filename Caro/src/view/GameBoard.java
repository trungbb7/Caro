package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.GameController;
import model.Game;
import model.Observer;

public class GameBoard extends JFrame implements Observer {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JPanel board;
	private JPanel wrapPanel;
	public GameBoard() throws CloneNotSupportedException {
		Game game = Game.getInstance();
		int size = game.getBoardType();
		game.registerObserver(this);
		this.setLayout(new BorderLayout());
		wrapPanel = new JPanel();
		wrapPanel.setLayout(new BorderLayout());
		board = new JPanel();
		board.setBackground(new Color(12, 20, 29));
		board.setLayout(new GridLayout(size, size, 0, 0));
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Cell cell = game.getCell(y, x);
				Cell cloneCell = (Cell) cell.clone();
				cloneCell.setFontSize(20);
				board.add(cloneCell);
			}
		}
		wrapPanel.add(board, BorderLayout.CENTER);
		this.getContentPane().add(wrapPanel);
		this.setSize(300, 300);
		this.setTitle("Board Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void update() {
		getContentPane().remove(wrapPanel);
		Game game = Game.getInstance();
		int size = game.getBoardType();

		board = new JPanel();
		board.setBackground(new Color(12, 20, 29));
		board.setLayout(new GridLayout(size, size, 0, 0));
		for (int y = 0; y < game.getBoardType(); y++) {
			for (int x = 0; x < game.getBoardType(); x++) {
				Cell cell = game.getBoard().getCell(y, x);
				Cell cloneCell;
				try {
					cloneCell = (Cell) cell.clone();
					cloneCell.setFontSize(12);
					board.add(cloneCell);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		wrapPanel = new JPanel();
		wrapPanel.setLayout(new BorderLayout());
		wrapPanel.add(board);
		getContentPane().add(wrapPanel);
		SwingUtilities.updateComponentTreeUI(this);
	}

}
