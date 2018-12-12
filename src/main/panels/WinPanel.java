package main.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import main.MoneyGame;

public class WinPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public WinPanel() {
		super();
		this.addMouseListener(this);
		this.setSize(MoneyGame.WIDTH, MoneyGame.HEIGHT);
	}
	
	@Override
	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		g.setColor(new Color(100, 0, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MoneyGame.swap("game");
	}
	
	
}
