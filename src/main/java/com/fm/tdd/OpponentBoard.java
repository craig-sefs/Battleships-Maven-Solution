package com.fm.tdd;

import com.fm.tdd.domain.Position;
import com.fm.tdd.utils.Assert;
import com.fm.tdd.utils.BoardObserver;

/**
 * Represents the board a player has which holds hits and misses on their opponents board
 */
public class OpponentBoard extends AbstractBoard {

	private BoardObserver boardObserver = new BoardObserver() {
		@Override
		public void hit(Position position) {
			grid[position.getX()][position.getY()] = CoordinateState.HIT;
		}

		@Override
		public void miss(Position position) {
			grid[position.getX()][position.getY()] = CoordinateState.MISS;
		}
	};

	/**
	 * @throws IllegalArgumentException when null is passed
	 */
	public void register(ObservableBoard observableBoard) {
		Assert.notNull(observableBoard, "observableBoard cannot be null");

		observableBoard.addBoardObserver(boardObserver);
	}

}
