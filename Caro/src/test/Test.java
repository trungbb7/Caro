package test;

import javax.swing.UIManager;

import view.UI;

public class Test {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UI.getInstance();
			// new GameBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
