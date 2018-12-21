package main.panels.gamepanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.MoneyGame;
import main.Point;
import main.panels.Panel;

public class GamePanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private HexGrid hg;
	private ArrayList<Point> links;
	private ArrayList<Cell> cells;
	private Color linkColor;
	private int cellSize;
	
	private BufferedImage settingsImage;
	
	private BufferedImage image;
		
	public void init(int i) {
		
		hg = new HexGrid();
		links = new ArrayList<Point>();
		cells = new ArrayList<Cell>();
		linkColor = new Color(255, 255, 255, 70);
		cellSize = MoneyGame.SIZE/10;
		this.generateCells(i);
		this.addRandomMoneyToAll(5);
		
		image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		renderImage();
	}
	
	public GamePanel() {
		super();
		this.setSize(MoneyGame.WIDTH, MoneyGame.HEIGHT);
		this.addMouseListener(this);
		try {
			settingsImage = ImageIO.read(new File("src/settings-icon.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.init(10);
	}
		
	private void addCell(int gridIndex) {
		hg.getGrid()[gridIndex].setCellIndex(cells.size());
		cells.add(new Cell(hg.getGrid()[gridIndex].x, hg.getGrid()[gridIndex].y, cellSize));
		hg.getGrid()[gridIndex].setEmpty(false);
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
		//Adding the link if it doesn't exist
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
	
	public void renderImage() {
		Graphics2D g = image.createGraphics();
		
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
		//Display the settings button
		g.drawImage(settingsImage, 0, 0, 40, 40, null);
		this.repaint();
	}
	
	@Override
	public void paint(Graphics gr) {
		
		gr.drawImage(image, 0, 0, null);
		
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
				
				if(xDiff*xDiff + yDiff*yDiff < cellSize/2*cellSize/2) {
					
					int neighbourCount = cells.get(i).getNeighbours().size();
					
					//Decrement the clicked cell by the number of its neighbours
					cells.get(i).incrementMoney(-neighbourCount);
					
					for(int j = 0; j < neighbourCount; j++) {
						
						int cellIndex = cells.get(i).getNeighbours().get(j);
						cells.get(cellIndex).incrementMoney(1);
					
					}
					
					this.renderImage();
					
					//Check if any of the cells are below zero
					boolean isGameWon = true;
					for(int j = 0; j < cells.size(); j++) {
					
						if(cells.get(j).getMoney() < 0) {
							isGameWon = false;
							break;
						}
						
					}
					
					//If none of them are below zero then switch to the YOU'VE WON screen
					if(isGameWon) {
						MoneyGame.swap("win");
					}
					
					
				}
			}
			
			//Settings button
			if(MX < 40 && MY < 40) {
				MoneyGame.swap("settings");
			}
			
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
	
	public BufferedImage getImage() {
		return image;
	}
}