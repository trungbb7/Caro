package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class Option extends JPanel {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static Option uniqueInstance;
	private JButton applyBtn;
	private ButtonGroup levelBG;
	private ButtonGroup typeBG;

	private Option() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel levelP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		levelBG = new ButtonGroup();

		JRadioButton easyBtn = new JRadioButton("Easy");
		easyBtn.setActionCommand("easy");
		JRadioButton hardBtn = new JRadioButton("Hard");
		hardBtn.setActionCommand("hard");
		levelP.add(easyBtn);
		levelP.add(hardBtn);
		levelBG.add(easyBtn);
		levelBG.add(hardBtn);
		levelP.setBorder(new TitledBorder("Level"));

		JPanel typeP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		typeBG = new ButtonGroup();
		JRadioButton type10Btn = new JRadioButton("10x10");
		type10Btn.setActionCommand("10");
		JRadioButton type15Btn = new JRadioButton("15x15");
		type15Btn.setActionCommand("15");
		typeP.add(type10Btn);
		typeP.add(type15Btn);
		typeBG.add(type10Btn);
		typeBG.add(type15Btn);
		typeP.setBorder(new TitledBorder("Board type"));

		JPanel wrapPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		wrapPanel.add(levelP);
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
			File file = new File("src\\config");
			Scanner sc = new Scanner(file);
			int count = 1;
			while (sc.hasNextLine()) {
				if (count == 1) {
					levelConfig = sc.nextLine().split(":")[1];
				} else if (count == 2) {
					typeConfig = sc.nextLine().split(":")[1];
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
			FileWriter fileWriter = new FileWriter("src\\config");
			fileWriter.write("level:" + levelConfig + "\ntype:" + typeConfig);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
