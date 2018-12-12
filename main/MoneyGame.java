package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

import main.panels.GamePanel;
import main.panels.SettingsPanel;

public class MoneyGame {
	
	public final static int SIZE = 600, WIDTH = SIZE, HEIGHT = SIZE;
	
	static JFrame frame;
	static SettingsPanel sp;
	static GamePanel gp;
	Graphics g;
	public static void main(String[] args) {
		frame = new JFrame();
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		gp = new GamePanel();
		gp.setSize(new Dimension(WIDTH, HEIGHT));
		frame.add(gp);
		
		frame.setVisible(true);
		gp.setVisible(true);
	}
	
}
