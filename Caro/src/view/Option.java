package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import controller.ApplyButtonController;
import model.Game;

public class Option extends JPanel {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Color textColor = new Color(203, 207, 212);

	private static Option uniqueInstance;
	private JButton applyBtn;
	private ButtonGroup levelBG;
	private ButtonGroup typeBG;
	private ButtonGroup cellBG;

	private Option() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Game.TRANSPARENT_COLOR);
		JPanel levelP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		levelP.setBackground(Game.TRANSPARENT_COLOR);
		levelBG = new ButtonGroup();

		JRadioButton easyBtn = new JRadioButton("Easy");
		easyBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		easyBtn.setActionCommand("easy");

		JRadioButton mediumBtn = new JRadioButton("Medium");
		mediumBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		mediumBtn.setActionCommand("medium");

		JRadioButton hardBtn = new JRadioButton("Hard");
		hardBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		hardBtn.setActionCommand("hard");

		levelP.add(easyBtn);
		levelP.add(mediumBtn);
		levelP.add(hardBtn);

		levelBG.add(easyBtn);
		levelBG.add(mediumBtn);
		levelBG.add(hardBtn);

		levelP.setBorder(new TitledBorder(getBorder(), "Level",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("roboto", Font.BOLD, 16), textColor));

		JPanel cellP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellP.setBackground(Game.TRANSPARENT_COLOR);
		cellBG = new ButtonGroup();

		JRadioButton normalBtn = new JRadioButton("Normal");
		normalBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		normalBtn.setActionCommand("Normal");

		JRadioButton darkBtn = new JRadioButton("Dark");
		darkBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		darkBtn.setActionCommand("Dark");

		JRadioButton grayBtn = new JRadioButton("Gray");
		grayBtn.setFont(new Font("roboto", Font.PLAIN, 14));
		grayBtn.setActionCommand("Gray");

		cellP.add(normalBtn);
		cellP.add(darkBtn);
		cellP.add(grayBtn);

		cellBG.add(normalBtn);
		cellBG.add(darkBtn);
		cellBG.add(grayBtn);

		cellP.setBorder(new TitledBorder(getBorder(), "Board Theme",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("roboto", Font.BOLD, 16), textColor));

		JPanel typeP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		typeP.setBackground(Game.TRANSPARENT_COLOR);
		typeBG = new ButtonGroup();
		JRadioButton type10Btn = new JRadioButton("10x10");
		type10Btn.setFont(new Font("roboto", Font.PLAIN, 14));
		type10Btn.setActionCommand("10");
		JRadioButton type15Btn = new JRadioButton("15x15");
		type15Btn.setFont(new Font("roboto", Font.PLAIN, 14));
		type15Btn.setActionCommand("15");
		typeP.add(type10Btn);
		typeP.add(type15Btn);
		typeBG.add(type10Btn);
		typeBG.add(type15Btn);
		typeP.setBorder(new TitledBorder(getBorder(), "Board type",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				new Font("roboto", Font.BOLD, 16), textColor));

		JPanel wrapPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		wrapPanel.setBackground(Game.TRANSPARENT_COLOR);
		wrapPanel.add(levelP);
		wrapPanel.add(cellP);
		wrapPanel.add(typeP);
		wrapPanel.setPreferredSize(new Dimension(620, 200));
		wrapPanel.setMaximumSize(new Dimension(620, 200));
		this.add(wrapPanel);
		applyBtn = new JButton("Apply");
		ApplyButtonController abc = new ApplyButtonController();
		applyBtn.addMouseListener(abc);
		this.add(applyBtn);
		this.add(Box.createVerticalGlue());
		updateSelected();

	}

	public static Option getInstance() {
		if (uniqueInstance == null) {
			synchronized (Option.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Option();
				}
			}
		}

		return uniqueInstance;
	}

	private void updateSelected() {
		try {
			String levelConfig = "";
			String typeConfig = "";
			String cellConfig = "";
			File file = new File("src\\config");
			Scanner sc = new Scanner(file);
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1) {
					levelConfig = sc.nextLine().split(":")[1];
				} else if (count == 2) {
					typeConfig = sc.nextLine().split(":")[1];
				} else if (count == 3) {
					cellConfig = sc.nextLine().split(":")[1];
				}
				count++;
			}
			Enumeration<AbstractButton> levelBtns = levelBG.getElements();
			while (levelBtns.hasMoreElements()) {
				JRadioButton btn = (JRadioButton) levelBtns.nextElement();
				if (btn.getActionCommand().equals(levelConfig)) {
					btn.setSelected(true);
				} else {
					btn.setSelected(false);
				}
			}

			Enumeration<AbstractButton> cellBtns = cellBG.getElements();
			while (cellBtns.hasMoreElements()) {
				JRadioButton btn = (JRadioButton) cellBtns.nextElement();
				if (btn.getActionCommand().equals(cellConfig)) {
					btn.setSelected(true);
				} else {
					btn.setSelected(false);
				}
			}

			Enumeration<AbstractButton> typeBtns = typeBG.getElements();
			while (typeBtns.hasMoreElements()) {
				JRadioButton btn = (JRadioButton) typeBtns.nextElement();
				if (btn.getActionCommand().equals(typeConfig)) {
					btn.setSelected(true);
				} else {
					btn.setSelected(false);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void applyHandler() {
		try {
			String levelConfig = levelBG.getSelection().getActionCommand();
			String typeConfig = typeBG.getSelection().getActionCommand();
			String cellConfig = cellBG.getSelection().getActionCommand();
			FileWriter fileWriter = new FileWriter("src\\config");
			fileWriter.write("level:" + levelConfig + "\ntype:" + typeConfig
					+ "\ncell:" + cellConfig);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
