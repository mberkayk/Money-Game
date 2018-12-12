package main.grid;

import main.MoneyGame;
import main.Point;

public class HexGrid {

	private int gridWidth, gridHeight;
	private Point[] gridPoints;
	
	public Point[] generateGrid(int cellSize) {
		//Generate a grid for the cells
			//Initialize variables
		float k = cellSize*4/9; 				// Coefficient of the size of the lines
		float ySpace = k * (float)Math.sqrt(3);	// Space between lines
		float xSpace = k * 6;					// Space between columns
		gridWidth = (int)Math.floor(MoneyGame.WIDTH / xSpace);	//Number of points in a line
		gridHeight = (int) Math.floor(MoneyGame.HEIGHT / ySpace) - 2;//Number of points in a column; - 2 to get rid of excess
		gridPoints = new Point[gridWidth * gridHeight];
	
		//Compute the points	
		for(int j = 0; j < gridHeight; j++) {
			for(int i = 0; i < gridWidth; i++) {
				float x = (xSpace*i+(j%2)*xSpace/2) + 2*k;//Compute the base X coordinate+offset the points by xSpace/2 every two lines
														  // + offset all the points so that all cells will be on screen
				float y = ySpace*(j+1);
				gridPoints[j*gridWidth + i] = new Point((int)x, (int)y);
			}
		}
		return gridPoints;
	}
	
	public int randomGridPointIndex() {
		int randomPointIndex = (int)(Math.floor(gridPoints.length * Math.random()));
		return randomPointIndex;
	}

	public int randomNeighborIndex(int pIndex) {
		
		while(true) {
			int px = pIndex % gridWidth;				 	// gridX of p
			int py = (int) Math.floor(pIndex / gridWidth);	// gridY of p
			
			Point[] neighbors = {new Point(0, -2), new Point(0, -1), new Point(0, 1),
					new Point(0, 2), new Point((int)Math.pow(-1, py%2+1), -1), new Point((int)Math.pow(-1, py%2+1), 1)};
			
			int randomNeighborIndex = (int)(Math.floor(neighbors.length * Math.random()));
			int rny = neighbors[randomNeighborIndex].y; // rny = random neighbor grid y
			int rnx = neighbors[randomNeighborIndex].x;
			int randomNeighborGridIndex = (py+rny)*gridWidth + (px+rnx);

			if(px + rnx > -1 && px + rnx < gridWidth && py + rny > -1 && py + rny < gridHeight) {
				return randomNeighborGridIndex;
			}else {
				Point[] temp = neighbors.clone();					//
				neighbors = new Point[neighbors.length - 1];//
				int c = 0;									// REMOVE
				for(int i = 0; i < temp.length; i++) {		//  THE
					if(i != randomNeighborIndex) {			// INVALID
						neighbors[c] = temp[i];				//NEIGHBORS
						c++;								// ELEMENT
					}else {									//
						continue;							//
					}										//
				}											//			
			}
		}
	}
	
	public Point[] getGrid() {
		return gridPoints;
	}
}
