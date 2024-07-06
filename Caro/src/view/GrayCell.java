package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Game;

public class GrayCell extends Cell implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrayCell(int y, int x) {
		super(y, x);
		setBackground(new Color(12, 20, 29));
	}

	// public GrayCell(int y, int x, int tick) {
	// this.y = y;
	// this.x = x;
	// this.tick = tick;
	// this.setLayout(new FlowLayout(FlowLayout.CENTER));
	// this.setFont(new Font("Bauhaus 93", Font.PLAIN, fontSize));
	// this.setHorizontalAlignment(SwingConstants.CENTER);
	// setBorder(BorderFactory.createLineBorder(borderColor, 1));
	// }

}
