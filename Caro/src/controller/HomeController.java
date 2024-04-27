package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import view.UI;

public class HomeController implements MouseListener {

	public HomeController() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		String command = btn.getActionCommand();
		UI ui = UI.getInstance();
		switch (command) {
		case "New game":
			ui.newGame();
			break;
		case "Option":
			ui.goOption();
			break;
		case "Statistics":
			ui.goStatistics();
			break;
		case "Exit":
			System.exit(0);
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
