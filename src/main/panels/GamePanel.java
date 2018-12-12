package main.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.MoneyGame;
import main.Point;
import main.grid.HexGrid;

public class GamePanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private HexGrid hg;
	private ArrayList<Point> links;
	private ArrayList<Cell> cells;
	private Color linkColor;
	private int cellSize;
	private BufferedImage reInitImage;
		
	private void init() {
		hg = new HexGrid();
		links = new ArrayList<Point>();
		cells = new ArrayList<Cell>();
		linkColor = new Color(255, 255, 255, 70);
		cellSize = MoneyGame.SIZE/10;
		this.generateCells(10);
		this.addRandomMoneyToAll(5);
		try {
			reInitImage = ImageIO.read(new File("src/re-init.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public GamePanel() {
		super();
		this.addMouseListener(this);
		this.init();
	}
		
	private void addCell(int gridIndex) {
		hg.getGrid()[gridIndex].setCellIndex(cells.size());
		cells.add(new Cell(hg.getGrid()[gridIndex].x, hg.getGrid()[gridIndex].y, cellSize));
		hg.getGrid()[gridIndex].setEmpty(false);
		this.repaint();
	}
	
	private void addLink(int par1, int par2) {
		//Checking if the link already exists
		boolean flag = true;
		ArrayList<Integer> n = cells.get(par1).getNeighbours();
		for(int i = 0; i < n.size(); i++) {
			
			if(n.get(i) == par2) {
				flag = false;
				break;
			}
		}
		//Adding the link doesn't exist
		if(flag) {
			links.add(new Point(par1, par2));
			cells.get(par1).addNeighbour(par2);
			cells.get(par2).addNeighbour(par1);
		}
	}
	
	private void generateCells(int cellCount) {
		//Generate a grid
		hg.generateGrid(cellSize);
		
		//Select a random grid point
		int randomPointIndex = hg.randomGridPointIndex();
		// Add a cell to that point
		addCell(randomPointIndex);
		////////////////////////////////////////////////////
		int selectedGridIndex = randomPointIndex;
		int selectedCellIndex = 0;
		int preCellIndex = 0;
		for(int i = 0; i < cellCount-1;) {
			//
			int randomNeighbourIndex = hg.randomNeighborIndex(selectedGridIndex);
			if(hg.getGrid()[randomNeighbourIndex].isEmpty()) {
				addCell(randomNeighbourIndex);
				preCellIndex = selectedCellIndex;
				selectedCellIndex = hg.getGrid()[randomNeighbourIndex].getCellIndex();
				addLink(preCellIndex, selectedCellIndex);
				
				selectedGridIndex = randomNeighbourIndex;
				i++;
			}else {
				preCellIndex = selectedCellIndex;
				selectedCellIndex = hg.getGrid()[randomNeighbourIndex].getCellIndex();
				addLink(preCellIndex, selectedCellIndex);
				selectedGridIndex = randomNeighbourIndex;		
			}
		}
	}
	
	private void addRandomMoneyToAll(int sum) {
		for(int i = 0; i < this.cells.size() * 5; i++) {
			int index = (int)(Math.random() * cells.size());
			int index2 = (int)(Math.random() * cells.size());
			cells.get(index).incrementMoney(1);
			cells.get(index2).incrementMoney(-1);
		}
		for(int i = 0; i != sum; i += Math.signum(sum)) {
			int index = (int)(Math.random() * cells.size());
			cells.get(index).incrementMoney((int)Math.signum(sum));
		}
	}
	
	@Override
	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		//Background
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		//Display grid points
//		for(int i = 0; i < hg.getGrid().length; i++) {
//			g.setColor(Color.WHITE);
//			g.fillOval(hg.getGrid()[i].x, hg.getGrid()[i].y, 5, 5);
//		}
		
		//Display cells
		for(int i = 0; i < cells.size(); i++) {
			cells.get(i).display(g);
		}
		
		//Display links
		for(int i = 0; i < links.size(); i++) {
			g.setColor(linkColor);
			int x1 = cells.get((int)(links.get(i).getX())).getX();
			int y1 = cells.get((int)(links.get(i).getX())).getY();
			int x2 = cells.get((int)(links.get(i).getY())).getX();
			int y2 = cells.get((int)(links.get(i).getY())).getY();
			g.setStroke(new BasicStroke(10));
			g.drawLine(x1, y1, x2, y2);
		}
		//Display the re-initiate button
		g.drawImage(reInitImage, 0, 0, 40, 40, null);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == 1) {
			int MX = e.getX();
			int MY = e.getY();
			//Check if any of the cells have been clicked
			for(int i = 0; i < cells.size(); i++) {
				int CX = cells.get(i).getX();
				int CY = cells.get(i).getY();
				int xDiff = MX - CX;
				int yDiff = MY - CY;
				if(xDiff*xDiff + yDiff*yDiff < cellSize*cellSize) {
					int neighbourCount = cells.get(i).getNeighbours().size();
					cells.get(i).incrementMoney(-neighbourCount);
					for(int j = 0; j < neighbourCount; j++) {
						int cellIndex = cells.get(i).getNeighbours().get(j);
						cells.get(cellIndex).incrementMoney(1);
					}
				}
			}
			
// TODO:After adding a panel manager check if any of the cells are under zero if not activate a panel that says you've won
//			for(int i = 0; i < cells.size(); i++) {
//				if(cells.get(i).getMoney() < 0) {
//					return;
//				}
//				
//			}
			
			if(MX < 40 && MY < 40) {
				init();
			}
			
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}