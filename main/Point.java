package main;

public class Point extends java.awt.Point {
	private static final long serialVersionUID = 1L;

	private boolean isEmpty;
	
	private int cellIndex;
	
	public Point() {
		super();
		isEmpty = true;
	}
	public Point(int a, int b) {
		super(a,b);
		isEmpty = true;
		cellIndex = -1;
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setEmpty(boolean b) {
		isEmpty = b;
	}
	public int getCellIndex() {
		return cellIndex;
	}
	
	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}
}
