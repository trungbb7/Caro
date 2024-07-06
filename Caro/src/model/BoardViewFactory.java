package model;

import view.BoardView;
import view.DarkBoardView;
import view.GrayBoardView;
import view.NormalBoardView;

public class BoardViewFactory {

	public static BoardView createBoardView(String type) {
		if (type.equals("Normal")) {
			return new NormalBoardView();
		} else if (type.equals("Gray")) {
			return new GrayBoardView();
		} else if (type.equals("Dark")) {
			return new DarkBoardView();
		} else {
			return null;
		}
	}
}
