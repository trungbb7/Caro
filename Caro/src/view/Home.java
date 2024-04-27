package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Home extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Home(MouseListener mouseListener) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1, 0, 10));
		JButton newGameBtn = new JButton("New game");
		newGameBtn.setActionCommand(newGameBtn.getText());
		JButton optionBtn = new JButton("Option");
		optionBtn.setActionCommand(optionBtn.getText());
		JButton statisticsBtn = new JButton("Statistics");
		statisticsBtn.setActionCommand(statisticsBtn.getText());
		JButton exitBtn = new JButton("Exit");
		exitBtn.setActionCommand(exitBtn.getText());

		newGameBtn.addMouseListener(mouseListener);
		optionBtn.addMouseListener(mouseListener);
		statisticsBtn.addMouseListener(mouseListener);
		exitBtn.addMouseListener(mouseListener);
		panel.add(newGameBtn);
		panel.add(optionBtn);
		panel.add(statisticsBtn);
		panel.add(exitBtn);
		panel.setPreferredSize(new Dimension(100, 200));
		panel.setMaximumSize(new Dimension(100, 200));
		this.add(Box.createVerticalGlue());
		this.add(panel);
		this.add(Box.createVerticalGlue());

	}

}
