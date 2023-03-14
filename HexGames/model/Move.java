package model;

public class Move {
	private int x, y;

	/*
	 * @param x the x coordinate
	 * @param y the y coordinate 
	*/
	public Move(int x, int y) {
		this.x = x;
		this.y = y;

	}
	/* 
	 * @return the x coordinate 
	*/
	public int getX() {
		return x;
	}

	/* 
	 * @return the y coordinate 
	*/
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Move [x=" + x + ", y=" + y + "]";
	}

}