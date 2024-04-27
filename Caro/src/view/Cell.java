package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Game;

public class Cell extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int tick;
	public int y;
	public int x;

	public Cell(int y, int x) {
		this.y = y;
		this.x = x;
		this.tick = Game.nonTick;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public Cell(int y, int x, int tick) {
		this.y = y;
		this.x = x;
		this.tick = tick;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public void setBorderColor(Color color) {
		this.setBorder(BorderFactory.createLineBorder(color, 2));
	}

}
