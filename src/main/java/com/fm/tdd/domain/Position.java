package com.fm.tdd.domain;

/**
 * Simple immutable X, Y coordinate pair expressed with integers
 */
public class Position {

	private int x;
	private int y;

	/**
	 * @throws IllegalArgumentException when x or y are less than 0
	 */
	public Position(int x, int y) {
		validatePosition(x, "X");
		validatePosition(y, "Y");

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private void validatePosition(int value, String coordinateName) {
		if (value < 0) {
			throw new IllegalArgumentException(coordinateName + " position must be greater than or equal to 0");
		}
	}
}
