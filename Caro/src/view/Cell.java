package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Game;

public class Cell extends JLabel implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color borderColor = new Color(44, 62, 80);
	public int tick;
	public int y;
	public int x;
	private int fontSize = 28;

	public Cell(int y, int x) {
		this.y = y;
		this.x = x;
		this.tick = Game.NON_TICK;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setFont(new Font("Bauhaus 93", Font.PLAIN, fontSize));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(borderColor, 1));
		setBackground(new Color(12, 20, 29));
	}

	public Cell(int y, int x, int tick) {
		this.y = y;
		this.x = x;
		this.tick = tick;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setFont(new Font("Bauhaus 93", Font.PLAIN, fontSize));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(borderColor, 1));
	}

	public void setBorderColor(Color color) {
		this.setBorder(BorderFactory.createLineBorder(color, 2));
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int size) {
		this.fontSize = size;
		this.setFont(new Font("Bauhaus 93", Font.PLAIN, size));
	}
}
