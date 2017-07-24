package com.fm.tdd.domain;

import com.fm.tdd.utils.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>A Ship has a {@link com.fm.tdd.domain.Position}, {@link com.fm.tdd.domain.Heading}, length and name</p>
 *
 * <p>A Ship's {@link com.fm.tdd.domain.Heading} and {@link com.fm.tdd.domain.Position} are defined as the direction the ship is heading
 * and the coordinate of the front (or bow) of the Ship. Below details {@link com.fm.tdd.domain.Heading}'s meaning</p>
 *
 * <table summary="Description of Ship Heading">
 *     <tr>
 *         <td>{@link com.fm.tdd.domain.Heading#NORTH}</td>
 *         <td>Heading towards Y = MAX_VALUE</td>
 *     </tr>
 *     <tr>
 *         <td>{@link com.fm.tdd.domain.Heading#EAST}</td>
 *         <td>Heading towards X = MAX_VALUE</td>
 *     </tr>
 *     <tr>
 *         <td>{@link com.fm.tdd.domain.Heading#SOUTH}</td>
 *         <td>Heading towards Y = 0</td>
 *     </tr>
 *     <tr>
 *         <td>{@link com.fm.tdd.domain.Heading#WEST}</td>
 *         <td>Heading towards X = 0</td>
 *     </tr>
 * </table>
 */
public class Ship {

	private String name;
	private int length;
	private Heading heading;
	private Position position;
	private List<Position> positions;
	private int lives;
	private Set<ShipObserver> shipObservers = new HashSet<ShipObserver>();

	/**
	 * @param name name of the Ship (must not be null, empty or whitespace)
	 * @param length length of the Ship (must be between 1 and 5)
	 * @param heading {@link Heading} of Ship (must not be null)
	 * @param position {@link Position} of Ship (must not be null)
	 * @throws IllegalArgumentException if any of the above constraints are not met
	 */
	public Ship(String name, int length, Heading heading, Position position) {
		Assert.notBlank(name, "Ship's name cannot be null or empty");
		Assert.notNull(heading, "Ship's heading cannot be null");
		assertLengthValid(length);

		this.name = name;
		this.length = length;
		this.lives = length;
		this.heading = heading;
		this.position = position;
		this.positions = generatePositions(length, heading, position);
	}

	private List<Position> generatePositions(int length, Heading heading, Position startPosition) {
		List<Position> positions = new ArrayList<Position>(length);
		positions.add(startPosition);

		Position previousPosition = startPosition;
		for (int i = 1; i < length; i++) {
			Position nextPosition = null;

			switch (heading) {
				case NORTH:
					nextPosition = new Position(previousPosition.getX(), previousPosition.getY() - 1);
					break;
				case EAST:
					nextPosition = new Position(previousPosition.getX() - 1, previousPosition.getY());
					break;
				case SOUTH:
					nextPosition = new Position(previousPosition.getX(), previousPosition.getY() + 1);
					break;
				case WEST:
					nextPosition = new Position(previousPosition.getX() + 1, previousPosition.getY());
					break;
			}

			positions.add(nextPosition);

			previousPosition = nextPosition;
		}

		return positions;
	}

	private static void assertLengthValid(int length) {
		if (length < 1 || length > 5) {
			throw new IllegalArgumentException("Ship's length must be between 1 and 5");
		}
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public Heading getHeading() {
		return heading;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isHit(Position explosionLocation) {
		for (Position position : positions) {
			if (position.getX() == explosionLocation.getX() && position.getY() == explosionLocation.getY()) {
				lives--;

				if (isSunk()) {
					notifySunk();
				}

				return true;
			}
		}

		return false;
	}

	public boolean isSunk() {
		return 0 == lives;
	}

	/**
	 * @throws IllegalArgumentException when null is passed
	 */
	public void addShipObserver(ShipObserver shipObserver) {
		Assert.notNull(shipObserver, "shipObserver cannot be null");

		shipObservers.add(shipObserver);
	}

	/**
	 * @throws IllegalArgumentException when null is passed
	 */
	public void removeShipObserver(ShipObserver shipObserver) {
		Assert.notNull(shipObserver, "shipObserver cannot be null");

		shipObservers.remove(shipObserver);
	}

	private void notifySunk() {
		for (ShipObserver shipObserver : shipObservers) {
			shipObserver.sunk(this);
		}
	}

	public boolean overlaps(Ship otherShip) {
		List<Position> otherShipsPositions = otherShip.positions;

		for (Position otherShipsPosition : otherShipsPositions) {
			for (Position thisPosition : this.positions) {
				if (otherShipsPosition.getX() == thisPosition.getX() && otherShipsPosition.getY() == thisPosition.getY()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @return immutable {@link List} of {@link Position}s
	 */
	public List<Position> getPositions() {
		return Collections.unmodifiableList(positions);
	}

}
