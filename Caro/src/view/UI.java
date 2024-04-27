package view;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.HomeController;

public class UI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UI uniqueInstance;
	public CardLayout card;
	private Container container;
	private JPanel home;
	private JPanel match;
	private JPanel option;
	private JPanel statistics;

	private UI() {
		this.setTitle("Caro");
		this.setSize(620, 620);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		this.setVisible(true);
	}

	public static UI getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new UI();
		}

		return uniqueInstance;
	}

	public void init() {
		container = this.getContentPane();
		card = new CardLayout();
		container.setLayout(card);
		match = new Match();
		home = new Home(new HomeController());
		option = Option.getInstance();
		statistics = new Statistics();
		container.add("home", home);
		container.add("statistics", statistics);
		container.add("option", option);
	}

	public void newGame() {
		this.card.removeLayoutComponent(this.match);
		this.match = new Match();
		this.container.add("match", match);
		this.card.show(container, "match");
	}

	public void goStatistics() {
		this.card.removeLayoutComponent(this.statistics);
		this.statistics = new Statistics();
		this.container.add("statistics", statistics);
		this.card.show(container, "statistics");
	}

	public void backHome() {
		this.card.show(container, "home");
	}

	public void goOption() {
		this.card.show(container, "option");
	}

	public CardLayout getCard() {
		return card;
	}

	public void setCard(CardLayout card) {
		this.card = card;
	}

	public JPanel getHome() {
		return home;
	}

	public void setHome(JPanel home) {
		this.home = home;
	}

	public JPanel getMatch() {
		return match;
	}

	public void setMatch(JPanel match) {
		this.match = match;
	}

}
