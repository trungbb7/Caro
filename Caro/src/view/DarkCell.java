package view;

import java.awt.Color;

public class DarkCell extends Cell implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DarkCell(int y, int x) {
		super(y, x);
		setBackground(Color.DARK_GRAY);
	}

	public DarkCell(int y, int x, int tick) {
		super(y, x);
	}

}
