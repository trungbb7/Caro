package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.Option;
import view.UI;

public class ApplyButtonController extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		Option op = Option.getInstance();
		op.applyHandler();
		UI ui = UI.getInstance();
		ui.backHome();
		super.mouseClicked(e);
	}
}
