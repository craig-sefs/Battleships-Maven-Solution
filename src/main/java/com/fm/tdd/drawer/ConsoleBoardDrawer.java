package com.fm.tdd.drawer;

import com.fm.tdd.CoordinateState;
import com.fm.tdd.DrawableBoard;
import com.fm.tdd.utils.Assert;
import java.io.PrintStream;

/**
 * Draws a {@link DrawableBoard} to standard out
 */
public class ConsoleBoardDrawer implements BoardDrawer {

	private PrintStream printStream;

	public ConsoleBoardDrawer(PrintStream printStream) {
		Assert.notNull(printStream, "printStream cannot be null");

		this.printStream = printStream;
	}

	@Override
	public void draw(DrawableBoard drawableBoard) {
		CoordinateState[][] grid = flipGrid(drawableBoard.getGrid());

		String line = "";
		for (int j = 0; j < (grid.length * 2) + 1; j++) {
			line += '-';
		}

		for (CoordinateState[] coordinateStates : grid) {
			String format = "";
			for (int y = 0; y < (coordinateStates.length * 2) + 1; y++) {
				format += y % 2 == 0 ? "|" : "%s";
			}

			printStream.println(line);
			String[] markers = new String[coordinateStates.length];

			for (int y = 0; y < coordinateStates.length; y++) {
				CoordinateState coordinateState = coordinateStates[y];

				markers[y] = coordinateState.getHitCharacter();
			}

			printStream.println(String.format(format, markers));
		}

		printStream.println(line);
	}

	private CoordinateState[][] flipGrid(CoordinateState[][] originalGrid) {
		CoordinateState[][] drawGrid = null;

		for (int x = 0; x < originalGrid.length; x++) {
			CoordinateState[] originalColumn = originalGrid[x];
			if (null == drawGrid) {
				drawGrid = new CoordinateState[originalColumn.length][originalGrid.length];
			}

			for (int y = originalColumn.length - 1; y >= 0; y--) {
				drawGrid[originalGrid.length - 1 - y][x] = originalColumn[y];
			}
		}

		return drawGrid;
	}
}
