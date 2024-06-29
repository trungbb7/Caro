package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import model.Game;

public class Statistics extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color textColor = new Color(203, 207, 212);
	private Color backgroundColorBtn = new Color(24, 188, 156);
	private JLabel totalMatchL;
	private JLabel winL;
	private JLabel rateL;

	public Statistics() {
		this.setLayout(new BorderLayout(0, 30));
		setBackground(Game.BACKGROUND_COLOR);
		JLabel titleL = new JLabel("Thống kê", SwingConstants.CENTER);
		titleL.setForeground(textColor);
		titleL.setFont(new Font("Arial", Font.BOLD, 50));
		this.add(titleL, BorderLayout.NORTH);

		JPanel wrapPanel = new JPanel();
		wrapPanel.setBackground(Game.BACKGROUND_COLOR);
		wrapPanel.setLayout(new BoxLayout(wrapPanel, BoxLayout.Y_AXIS));
		JPanel innerWrap = new JPanel();
		innerWrap.setBackground(Game.BACKGROUND_COLOR);
		innerWrap.setLayout(new GridLayout(3, 1, 10, 10));
		totalMatchL = new JLabel("Tổng số trận: ...", SwingConstants.LEFT);
		totalMatchL.setForeground(textColor);
		winL = new JLabel("Số trận thắng: ...", SwingConstants.LEFT);
		winL.setForeground(textColor);
		rateL = new JLabel("Tỉ lệ thắng: ...", SwingConstants.LEFT);
		rateL.setForeground(textColor);
		totalMatchL.setFont(new Font("Arial", Font.BOLD, 24));
		winL.setFont(new Font("Arial", Font.BOLD, 24));
		rateL.setFont(new Font("Arial", Font.BOLD, 24));
		innerWrap.add(totalMatchL);
		innerWrap.add(winL);
		innerWrap.add(rateL);
//		innerWrap.setPreferredSize(new Dimension(120, 120));
//		innerWrap.setMaximumSize(new Dimension(120, 120));
		wrapPanel.add(Box.createHorizontalGlue());
//		innerWrap.setBorder(new TitledBorder("inner"));
		wrapPanel.add(innerWrap);
		JButton homeBtn = new JButton("Home");
		homeBtn.setOpaque(true);
		homeBtn.setContentAreaFilled(true);
		homeBtn.setBorderPainted(false);
		homeBtn.setBackground(backgroundColorBtn);
		homeBtn.setForeground(Color.WHITE);
		homeBtn.setFont(new Font("roboto", Font.BOLD, 12));
		homeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UI ui = UI.getInstance();
				ui.backHome();
				super.mouseClicked(e);
			}
		});
		wrapPanel.add(homeBtn);
		wrapPanel.add(Box.createHorizontalGlue());
		wrapPanel.setBorder(new TitledBorder(".........."));
		this.add(wrapPanel);
		loadData();
	}

	public void loadData() {
		try {
			File file = new File("src\\data");
			Scanner sc = new Scanner(file);
			float win = 0;
			float lose = 0;
			float draw = 0;
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1) {
					win = Integer.parseInt(sc.nextLine().split(":")[1]);
				} else if (count == 2) {
					lose = Integer.parseInt(sc.nextLine().split(":")[1]);
				} else if (count == 3) {
					draw = Integer.parseInt(sc.nextLine().split(":")[1]);
				}
				count++;
			}
			float total = win + lose + draw;
			totalMatchL.setText("Tổng số trận: " + (int) total);
			float winRate = win / total;
			winL.setText("Số trận thắng: " + (int) win);
			rateL.setText("Tỉ lệ thắng: " + Math.round(winRate * 100) + "%");

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
