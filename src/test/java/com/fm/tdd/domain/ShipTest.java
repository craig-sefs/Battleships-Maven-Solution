package com.fm.tdd.domain;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ShipTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenNullNamePassed() throws Exception {
		new Ship(null, 3, Heading.EAST, new Position(1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenEmptyNamePassed() throws Exception {
		new Ship("", 3, Heading.EAST, new Position(1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenWhitespaceNamePassed() throws Exception {
		new Ship("   			 	", 3, Heading.EAST, new Position(1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenLengthLessThan1Passed() throws Exception {
		new Ship("Submarine", 0, Heading.EAST, new Position(1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenLengthGreaterThan5Passed() throws Exception {
		new Ship("Submarine", 6, Heading.EAST, new Position(1, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenNullHeadingPassed() throws Exception {
		new Ship("Submarine", 3, null, new Position(1, 1));
	}

	@Test
	public void testConstructingShip() throws Exception {
		String expectedName = "Submarine";
		int expectedLength = 3;
		Heading expectedHeading = Heading.EAST;

		Ship ship = new Ship(expectedName, expectedLength, expectedHeading, new Position(4, 1));

		assertThat(ship.getName(), equalTo(expectedName));
		assertThat(ship.getLength(), equalTo(expectedLength));
		assertThat(ship.getHeading(), equalTo(expectedHeading));
	}

	@Test
	public void testIsHitWhenShipIsHit() throws Exception {
		Ship ship;

		ship = new Ship("Submarine", 4, Heading.NORTH, new Position(1, 6));
		assertTrue(ship.isHit(new Position(1, 6)));
		assertTrue(ship.isHit(new Position(1, 5)));
		assertTrue(ship.isHit(new Position(1, 4)));
		assertTrue(ship.isHit(new Position(1, 3)));

		ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 1));
		assertTrue(ship.isHit(new Position(6, 1)));
		assertTrue(ship.isHit(new Position(5, 1)));
		assertTrue(ship.isHit(new Position(4, 1)));
		assertTrue(ship.isHit(new Position(3, 1)));

		ship = new Ship("Submarine", 4, Heading.SOUTH, new Position(1, 4));
		assertTrue(ship.isHit(new Position(1, 7)));
		assertTrue(ship.isHit(new Position(1, 6)));
		assertTrue(ship.isHit(new Position(1, 5)));
		assertTrue(ship.isHit(new Position(1, 4)));

		ship = new Ship("Submarine", 4, Heading.WEST, new Position(1, 6));
		assertTrue(ship.isHit(new Position(1, 6)));
		assertTrue(ship.isHit(new Position(2, 6)));
		assertTrue(ship.isHit(new Position(3, 6)));
		assertTrue(ship.isHit(new Position(4, 6)));
	}

	@Test
	public void testIsHitWhenShipIsNotHit() throws Exception {
		Ship ship;

		ship = new Ship("Submarine", 4, Heading.NORTH, new Position(1, 6));

		assertFalse(ship.isHit(new Position(1, 7)));

		ship = new Ship("Submarine", 4, Heading.NORTH, new Position(1, 6));

		assertFalse(ship.isHit(new Position(2, 4)));

		ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 8));

		assertFalse(ship.isHit(new Position(8, 8)));

		ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 8));

		assertFalse(ship.isHit(new Position(4, 2)));

		ship = new Ship("Submarine", 4, Heading.SOUTH, new Position(1, 4));

		assertFalse(ship.isHit(new Position(1, 2)));

		ship = new Ship("Submarine", 4, Heading.SOUTH, new Position(1, 4));

		assertFalse(ship.isHit(new Position(2, 5)));

		ship = new Ship("Submarine", 4, Heading.WEST, new Position(3, 6));

		assertFalse(ship.isHit(new Position(1, 0)));

		ship = new Ship("Submarine", 4, Heading.WEST, new Position(3, 6));

		assertFalse(ship.isHit(new Position(4, 7)));
	}

	@Test
	public void testIsSunkWhenNoShotsFired() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 2));

		assertFalse(ship.isSunk());
	}

	@Test
	public void testIsSunkReturnsFalseWhenOnlyOneHit() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 2));

		ship.isHit(new Position(4, 2));

		assertFalse(ship.isSunk());
	}

	@Test
	public void testIsSunkReturnsTrueWhenAllLivesLost() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 2));

		ship.isHit(new Position(6, 2));
		ship.isHit(new Position(5, 2));
		ship.isHit(new Position(4, 2));
		ship.isHit(new Position(3, 2));

		assertTrue(ship.isSunk());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipObserverThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 2));

		ship.addShipObserver(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveShipObserverThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 2));

		ship.removeShipObserver(null);
	}

	@Test
	public void testObserversNotifiedWhenShipSunk() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(5, 6));

		List<ShipObserver> shipObservers = Arrays.asList(mock(ShipObserver.class),
														 mock(ShipObserver.class));

		for (ShipObserver shipObserver : shipObservers) {
			ship.addShipObserver(shipObserver);
		}

		ship.isHit(new Position(5, 6));
		ship.isHit(new Position(4, 6));
		ship.isHit(new Position(3, 6));
		ship.isHit(new Position(2, 6));

		for (ShipObserver shipObserver : shipObservers) {
			verify(shipObserver).sunk(eq(ship));
		}
	}

	@Test
	public void testObserversDoesNotNotifyRemovedObserversWhenShipSunk() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(5, 6));

		ShipObserver observerToRemove = mock(ShipObserver.class);

		ship.addShipObserver(observerToRemove);
		ship.removeShipObserver(observerToRemove);

		ship.isHit(new Position(5, 6));
		ship.isHit(new Position(4, 6));
		ship.isHit(new Position(3, 6));
		ship.isHit(new Position(2, 6));

		verify(observerToRemove, never()).sunk(any(Ship.class));
	}

	@Test
	public void testOverlapsReturnsTrueWhenShipOverlaps() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(3, 1));

		assertTrue(originalShip.overlaps(proposedShip));
	}

	@Test
	public void testOverlapsReturnsTrueWhenShipOverlapAtFront() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(5, 1));

		assertTrue(originalShip.overlaps(proposedShip));
	}

	@Test
	public void testOverlapsReturnsTrueWhenShipOverlapAtBack() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(2, 1));

		assertTrue(originalShip.overlaps(proposedShip));
	}

	@Test
	public void testOverlapsReturnsFalseWhenDoNotShipOverlaps() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(7, 1));

		assertFalse(originalShip.overlaps(proposedShip));
	}

	@Test
	public void testOverlapsReturnsFalseWhenDoNotShipOverlapsButTouchAtFront() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(6, 1));

		assertFalse(originalShip.overlaps(proposedShip));
	}

	@Test
	public void testOverlapsReturnsFalseWhenDoNotShipOverlapsButTouchAtBack() {
		Ship originalShip = new Ship("Submarine", 4, Heading.EAST, new Position(5, 2));
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.SOUTH, new Position(1, 1));

		assertFalse(originalShip.overlaps(proposedShip));
	}

}