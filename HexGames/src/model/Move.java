package src.model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Move move = (Move) o;

		if (x != move.x) return false;
		return y == move.y;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
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