package com.fm.tdd;

import java.util.Arrays;

public abstract class AbstractBoard implements DrawableBoard {

	protected CoordinateState[][] grid = new CoordinateState[PlayerBoard.BOARD_WIDTH][PlayerBoard.BOARD_HEIGHT];

	public AbstractBoard() {
		initialiseBoard();
	}

	@Override
	public CoordinateState[][] getGrid() {
		CoordinateState[][] copy = new CoordinateState[PlayerBoard.BOARD_WIDTH][PlayerBoard.BOARD_HEIGHT];

		for (int i = 0; i < grid.length; i++) {
			CoordinateState[] columnCopy = Arrays.copyOf(grid[i], grid[i].length);

			copy[i] = columnCopy;
		}

		return copy;
	}

	private void initialiseBoard() {
		for (int x = 0; x < PlayerBoard.BOARD_HEIGHT; x++) {
			for (int y = 0; y < PlayerBoard.BOARD_WIDTH; y++) {
				grid[x][y] = CoordinateState.EMPTY;
			}
		}
	}
}
