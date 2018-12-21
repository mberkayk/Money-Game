package main.panels.gamepanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Cell {

	
	private int x, y, money, size;
	private Color cellColor, textColor;
	private ArrayList<Integer> neighbours;
	
	public Cell(int x, int y, int cellSize) {
		cellColor = new Color(255, 255, 255);
		textColor = new Color(0, 20, 40);
		size = cellSize;
		neighbours = new ArrayList<Integer>();
		this.x = x;
		this.y = y;
		this.money = 0;
	}
	
	public Cell(int x, int y, int money, int cellSize) {
		cellColor = new Color(255, 255, 255);
		textColor = new Color(0, 20, 40);
		size = cellSize;
		neighbours = new ArrayList<Integer>();
		this.x = x;
		this.y = y;
		this.money = money;
	}
	
	public void incrementMoney(int par1) {
		this.money += par1;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void display(Graphics g) {
		g.setColor(cellColor);
		g.fillOval(x-size/2, y-size/2, size, size);
		g.setColor(textColor);
		g.setFont(new Font(null, Font.BOLD, 14));
		g.drawString(String.valueOf(money), x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void addNeighbour(int par1) {
		neighbours.add(par1);
	}
	
	public ArrayList<Integer> getNeighbours() {
		return neighbours;
	}
	

}
