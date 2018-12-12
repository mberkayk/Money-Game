package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.panels.WinPanel;
import main.panels.gamepanel.GamePanel;
import main.panels.settings.SettingsPanel;

public class MoneyGame {
	
	public final static int SIZE = 600, WIDTH = SIZE, HEIGHT = SIZE;
	
	static JFrame frame;
	
	static GamePanel gp;
	static SettingsPanel sp;
	static WinPanel wp;

	static JPanel panelManager;
	
	static CardLayout cl;
	
	Graphics g;
	public static void main(String[] args) {
		
		frame = new JFrame();
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		cl = new CardLayout();
		
		panelManager = new JPanel(cl);
		frame.add(panelManager);

		sp = new SettingsPanel();
		gp = new GamePanel();
		wp = new WinPanel();

		panelManager.add(sp, "settings");
		panelManager.add(gp, "game");
		panelManager.add(wp, "win");
		
		cl.show(panelManager, "settings");
		
		frame.setVisible(true);
	}
	
	public static void swap(String s) {
		cl.show(panelManager, s);
	}
	
}
