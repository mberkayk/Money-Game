package main.panels.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import main.MoneyGame;
import main.panels.Panel;

public class SettingsPanel extends Panel {

	private static final long serialVersionUID = 1L;
	JTextField tf = new JTextField();
	
	public SettingsPanel() {
		
		super();
		this.addMouseListener(this);
		this.setSize(MoneyGame.WIDTH, MoneyGame.HEIGHT);
		this.setLayout(null);
		
		tf.setPreferredSize(new Dimension(50, 50));
		tf.setBounds(300, 250, 50, 50);
		this.add(tf);
		tf.setFont(new Font(null, Font.BOLD, 16));
		tf.setText("10");
		tf.setCaretPosition(tf.getText().length());
		tf.setVisible(true);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics p) {
		Graphics2D g = (Graphics2D) p;
		
		//Background
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, MoneyGame.WIDTH, MoneyGame.HEIGHT);
		
		//Settings Panel Title
		g.setColor(Color.white);
		g.setFont(new Font(null, Font.BOLD, 24));
		g.drawString("Settings Panel", 200, 50);
		
		//Enter the number of cells
		g.setColor(Color.white);
		g.drawString("Number of cells: ", 50, 280);
		
		//Generate Button
		g.setColor(Color.white);
		g.fillRect(200, 350, 200, 70);
		g.setColor(Color.black);
		g.drawString("GENERATE", 230, 400);
		
		this.paintComponents(g);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int MX = e.getX();
		int MY = e.getY();
		
		//Generate Button
		if(MX < 400 && MX > 200 && MY < 420 && MY > 350) {
			int numberOfCells = Integer.valueOf(tf.getText());
			MoneyGame.gp.init(numberOfCells);
			MoneyGame.swap("game");
		}
	}
	
}
