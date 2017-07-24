package com.fm.tdd;

import com.fm.tdd.domain.Position;
import com.fm.tdd.domain.Ship;
import com.fm.tdd.exception.ShipOverlapException;
import com.fm.tdd.utils.Assert;
import com.fm.tdd.utils.BoardObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Represents the players own {@link com.fm.tdd.Board} including the {@link com.fm.tdd.domain.Ship}s</p>
 *
 * <p>Note that we are hard coding to be a board of size 10 X 10
 *
 * </p>
 */
public class PlayerBoard extends AbstractBoard implements Board, ObservableBoard {

	public static final int BOARD_HEIGHT = 10;
	public static final int BOARD_WIDTH = 10;

	private List<Ship> ships = new ArrayList<Ship>();
	private Set<BoardObserver> boardObservers = new HashSet<BoardObserver>();

	/**
	 * @throws IllegalArgumentException if the {@link Ship} is not fully on the board
	 */
	@Override
	public void addShip(Ship ship) {
		validateShipPlacement(ship);

		for (Position shipPosition : ship.getPositions()) {
			grid[shipPosition.getX()][shipPosition.getY()] = CoordinateState.SHIP_UNHIT;
		}

		ships.add(ship);
	}

	/**
	 * @return an immutable {@link List} of {@link Ship}s
	 */
	public List<Ship> getShips() {
		return Collections.unmodifiableList(ships);
	}

	@Override
	public void fireMine(Position position) {
		boolean hit = false;

		for (Ship ship : ships) {
			if (ship.isHit(position)) {
				grid[position.getX()][position.getY()] = CoordinateState.SHIP_HIT;

				notifyHit(position);

				hit = true;
			}
		}

		if (!hit) {
			notifyMiss(position);

			grid[position.getX()][position.getY()] = CoordinateState.MISS;
		}
	}

	@Override
	public int getRemainingShipCount() {
		int remainingShipsCount = 0;

		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				remainingShipsCount++;
			}
		}

		return remainingShipsCount;
	}

	private void validateShipPlacement(Ship ship) {
		Position position = ship.getPosition();

		if (position.getY() >= BOARD_HEIGHT || position.getX() >= BOARD_WIDTH) {
			throw new IllegalArgumentException("Ship must be entirely on the board");
		}

		switch (ship.getHeading()) {
			case NORTH:
				if (position.getY() - ship.getLength() < -1) {
					throw new IllegalArgumentException("Ship must be entirely on the board");
				}

				break;
			case EAST:
				if (position.getX() - ship.getLength() < -1) {
					throw new IllegalArgumentException("Ship must be entirely on the board");
				}

				break;
			case SOUTH:
				if (position.getY() > PlayerBoard.BOARD_HEIGHT - ship.getLength()) {
					throw new IllegalArgumentException("Ship must be entirely on the board");
				}

				break;
			case WEST:
				if (position.getX() > PlayerBoard.BOARD_WIDTH - ship.getLength()) {
					throw new IllegalArgumentException("Ship must be entirely on the board");
				}

				break;
		}

		for (Ship existingShip : ships) {
			if (existingShip.overlaps(ship)) {
				throw new ShipOverlapException(ship.getName() + " will overlap " + existingShip);
			}
		}
	}

	/**
	 * @throws IllegalArgumentException when null is passed
	 */
	@Override
	public void addBoardObserver(BoardObserver boardObserver) {
		Assert.notNull(boardObserver, "boardObserver cannot be null");

		boardObservers.add(boardObserver);
	}

	/**
	 * @throws IllegalArgumentException when null is passed
	 */
	@Override
	public void removeBoardObserver(BoardObserver boardObserver) {
		Assert.notNull(boardObserver, "boardObserver cannot be null");

		boardObservers.remove(boardObserver);
	}

	private void notifyHit(Position position) {
		for (BoardObserver boardObserver : boardObservers) {
			boardObserver.hit(position);
		}
	}

	private void notifyMiss(Position position) {
		for (BoardObserver boardObserver : boardObservers) {
			boardObserver.miss(position);
		}
	}
}
