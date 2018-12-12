package main.panels.settings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import main.MoneyGame;
import main.panels.Panel;

public class SettingsPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public SettingsPanel() {
		super();
		this.addMouseListener(this);
		this.setSize(MoneyGame.WIDTH, MoneyGame.HEIGHT);
		repaint();
	}
	
	@Override
	public void paint(Graphics p) {
		Graphics2D g = (Graphics2D) p;
		
		//Background
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, MoneyGame.WIDTH, MoneyGame.HEIGHT);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MoneyGame.swap("game");
	}
	
}
