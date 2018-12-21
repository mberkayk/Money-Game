package main.panels;

import java.awt.Color;
import java.awt.Font;
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
		
		g.drawImage(MoneyGame.gp.getImage(), 0, 0, null);

		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, this.getWidth(), getHeight());
		
		g.setColor(new Color(200, 0, 0));
		g.setFont(new Font(null, Font.BOLD, 24));
		g.drawString("YOU'VE WON", 200, 50);
		g.drawString("click anywhere to go back to settings", 70, 100);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MoneyGame.swap("settings");
	}
	
	
}
