package com.fm.tdd;

import com.fm.tdd.domain.Heading;
import com.fm.tdd.domain.Position;
import com.fm.tdd.domain.Ship;
import com.fm.tdd.exception.ShipOverlapException;
import com.fm.tdd.utils.BoardObserver;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class PlayerBoardTest {

	private PlayerBoard playerBoard;

	@Before
	public void setUp() throws Exception {
		playerBoard = new PlayerBoard();
	}

	@Test
	public void testAddShip() throws Exception {
		Position position = new Position(6, 2);
		Ship expectedShip = new Ship("Aircraft Carrier", 5, Heading.EAST, position);

		playerBoard.addShip(expectedShip);

		assertThat(playerBoard.getShips(), contains(sameInstance(expectedShip)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipYPositionGreaterThanBoardHeight() throws Exception {
		Position position = new Position(4, PlayerBoard.BOARD_HEIGHT + 1);
		Ship ship = new Ship("Submarine", 3, Heading.NORTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipWhenShipYPositionEqualToBoardHeight() throws Exception {
		Position position = new Position(4, PlayerBoard.BOARD_HEIGHT);
		Ship ship = new Ship("Submarine", 3, Heading.NORTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipPositionedOffBottomOfBoard() throws Exception {
		Position position = new Position(4, 1);
		Ship ship = new Ship("Submarine", 3, Heading.NORTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test
	public void testAddShipWhenShipSitsAtBottomOfBoard() throws Exception {
		Position position = new Position(4, 2);
		Ship ship = new Ship("Submarine", 3, Heading.NORTH, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipXPositionGreaterThanBoardWidth() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH + 1, 4);
		Ship ship = new Ship("Submarine", 3, Heading.EAST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipXPositionEqualToBoardWidth() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH, 4);
		Ship ship = new Ship("Submarine", 3, Heading.EAST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipPositionedOffLeftOfBoard() throws Exception {
		Position position = new Position(1, 4);
		Ship ship = new Ship("Submarine", 3, Heading.EAST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test
	public void testAddShipWhenShipSitsAtLeftOfBoard() throws Exception {
		Position position = new Position(2, 4);
		Ship ship = new Ship("Submarine", 3, Heading.EAST, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenHeadingEastAndShipPositionedOffLeftOfBoard() throws Exception {
		Position position = new Position(1, 4);
		Ship ship = new Ship("Submarine", 3, Heading.EAST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test
	public void testAddShipWhenShipHeadingSouthAndSitsAtTopOfBoard() throws Exception {
		Position position = new Position(2, PlayerBoard.BOARD_HEIGHT - 3);
		Ship ship = new Ship("Submarine", 3, Heading.SOUTH, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipHeadingSouthAndPositionedOffTopOfBoard() throws Exception {
		Position position = new Position(2, PlayerBoard.BOARD_HEIGHT - 2);
		Ship ship = new Ship("Submarine", 3, Heading.SOUTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipHeadingSouthAndYPositionEqualToBoardHeight() throws Exception {
		Position position = new Position(2, PlayerBoard.BOARD_HEIGHT);
		Ship ship = new Ship("Submarine", 3, Heading.SOUTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenShipHeadingSouthAndYPositionGreaterThanBoardHeight() throws Exception {
		Position position = new Position(2, PlayerBoard.BOARD_HEIGHT + 1);
		Ship ship = new Ship("Submarine", 3, Heading.SOUTH, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test
	public void testAddShipWhenShipHeadingSouthAndTouchesBottomOfBoard() throws Exception {
		Position position = new Position(2, 0);
		Ship ship = new Ship("Submarine", 3, Heading.SOUTH, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenHeadingWestAndShipPositionedOffRightOfBoard() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH - 2, 4);
		Ship ship = new Ship("Submarine", 3, Heading.WEST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenHeadingWestAndShipYPositionEqualToBoardWidth() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH, 4);
		Ship ship = new Ship("Submarine", 3, Heading.WEST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddShipThrowsIllegalArgumentExceptionWhenHeadingWestAndShipYPositionGreaterThanBoardWidth() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH + 1, 4);
		Ship ship = new Ship("Submarine", 3, Heading.WEST, position);

		try {
			playerBoard.addShip(ship);
		} finally {
			assertThat(playerBoard.getShips(), empty());
		}
	}

	@Test
	public void testAddShipWhenHeadingWestAndShipTouchesRightOfBoard() throws Exception {
		Position position = new Position(PlayerBoard.BOARD_WIDTH - 3, 4);
		Ship ship = new Ship("Submarine", 3, Heading.WEST, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test
	public void testAddShipWhenHeadingWestAndShipTouchesLeftOfBoard() throws Exception {
		Position position = new Position(0, 4);
		Ship ship = new Ship("Submarine", 3, Heading.WEST, position);

		playerBoard.addShip(ship);

		assertThat(playerBoard.getShips(), contains(ship));
	}

	@Test(expected = ShipOverlapException.class)
	public void testAddShipThrowsShipOverlapExceptionWhenProposedShipWillOverlapAnExistingShip() throws Exception {
		Position position = new Position(4, 4);
		Ship originalShip = new Ship("Submarine", 3, Heading.SOUTH, position);

		playerBoard.addShip(originalShip);

		Position proposedPosition = new Position(6, 5);
		Ship proposedShip = new Ship("Aircraft Carrier", 5, Heading.EAST, proposedPosition);

		try {
			playerBoard.addShip(proposedShip);
		} finally {
			assertThat(playerBoard.getShips(), contains(originalShip));
		}
	}

	@Test
	public void testFireMineDelegatesToShipsIsHit() throws Exception {
		Ship ship = new Ship("Submarine", 4, Heading.EAST, new Position(6, 6));
		ship = spy(ship);

		playerBoard.addShip(ship);

		Position minePosition = new Position(2, 5);

		playerBoard.fireMine(minePosition);

		ArgumentCaptor<Position> positionArgumentCaptor = ArgumentCaptor.forClass(Position.class);

		verify(ship).isHit(positionArgumentCaptor.capture());

		Position passedPosition = positionArgumentCaptor.getValue();

		assertEquals(minePosition.getX(), passedPosition.getX());
		assertEquals(minePosition.getY(), passedPosition.getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddBoardObserverThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		playerBoard.addBoardObserver(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBoardObserverThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		playerBoard.removeBoardObserver(null);
	}

	@Test
	public void testFireMineNotifiesObserversWhenShipHit() throws Exception {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.EAST, new Position(8, 1)));

		List<BoardObserver> boardObservers = Arrays.asList(mock(BoardObserver.class),
														   mock(BoardObserver.class));

		for (BoardObserver boardObserver : boardObservers) {
			playerBoard.addBoardObserver(boardObserver);
		}

		Position minePosition = new Position(6, 1);

		playerBoard.fireMine(minePosition);

		for (BoardObserver boardObserver : boardObservers) {
			verify(boardObserver).hit(same(minePosition));
		}
	}

	@Test
	public void testFireMineDoesNotNotifyRemovedObserversWhenShipHit() throws Exception {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.EAST, new Position(8, 1)));

		BoardObserver boardObserverToRemove = mock(BoardObserver.class);

		playerBoard.addBoardObserver(boardObserverToRemove);
		playerBoard.removeBoardObserver(boardObserverToRemove);

		Position minePosition = new Position(6, 1);

		playerBoard.fireMine(minePosition);

		verify(boardObserverToRemove, never()).hit(any(Position.class));
	}

	@Test
	public void testFireMineNotifiesObserversWhenNoShipHit() throws Exception {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.EAST, new Position(8, 1)));
		playerBoard.addShip(new Ship("Aircraft Carrier", 4, Heading.EAST, new Position(8, 2)));

		List<BoardObserver> boardObservers = Arrays.asList(mock(BoardObserver.class),
														   mock(BoardObserver.class));

		for (BoardObserver boardObserver : boardObservers) {
			playerBoard.addBoardObserver(boardObserver);
		}

		Position minePosition = new Position(6, 9);

		playerBoard.fireMine(minePosition);

		for (BoardObserver boardObserver : boardObservers) {
			verify(boardObserver).miss(same(minePosition));
		}
	}

	@Test
	public void testFireMineDoesNotNotifyRemovedObserversWhenNoShipHit() throws Exception {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.EAST, new Position(8, 1)));
		playerBoard.addShip(new Ship("Aircraft Carrier", 4, Heading.EAST, new Position(8, 2)));

		BoardObserver boardObserverToRemove = mock(BoardObserver.class);

		playerBoard.addBoardObserver(boardObserverToRemove);
		playerBoard.removeBoardObserver(boardObserverToRemove);

		Position minePosition = new Position(6, 9);

		playerBoard.fireMine(minePosition);

		verify(boardObserverToRemove, never()).miss(any(Position.class));
	}

	@Test
	public void testFireMineAddsHitToGridWhenPositionIsAHit() {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.WEST, new Position(1, 1)));

		Position firePosition = new Position(2, 1);
		playerBoard.fireMine(firePosition);

		CoordinateState[][] grid = playerBoard.getGrid();

		for (int x = 0; x < grid.length; x++) {
			CoordinateState[] coordinateStates = grid[x];

			for (int y = 0; y < coordinateStates.length; y++) {
				if (x == firePosition.getX() && y == firePosition.getY()) {
					assertEquals(CoordinateState.SHIP_HIT, coordinateStates[y]);
				} else if(x == 1 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else if(x == 3 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else if(x == 4 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else {
					assertEquals(CoordinateState.EMPTY, coordinateStates[y]);
				}
			}
		}
	}

	@Test
	public void testFireMineAddsHitToGridWhenPositionIsAMiss() {
		playerBoard.addShip(new Ship("Submarine", 4, Heading.WEST, new Position(1, 1)));

		Position firePosition = new Position(7, 8);
		playerBoard.fireMine(firePosition);

		CoordinateState[][] grid = playerBoard.getGrid();

		for (int x = 0; x < grid.length; x++) {
			CoordinateState[] coordinateStates = grid[x];

			for (int y = 0; y < coordinateStates.length; y++) {
				if (x == firePosition.getX() && y == firePosition.getY()) {
					assertEquals(CoordinateState.MISS, coordinateStates[y]);
				} else if(x == 1 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else if(x == 2 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else if(x == 3 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else if(x == 4 && y == 1) {
					assertEquals(CoordinateState.SHIP_UNHIT, coordinateStates[y]);
				} else {
					assertEquals(CoordinateState.EMPTY, coordinateStates[y]);
				}
			}
		}
	}

	@Test
	public void testGetRemainingShipCountWhenNoShipsHaveBeenSunk() {
		playerBoard = new PlayerBoard();

		playerBoard.addShip(new Ship("Submarine", 4, Heading.NORTH, new Position(5, 7)));
		playerBoard.addShip(new Ship("Aircraft Carrier", 5, Heading.WEST, new Position(1, 3)));

		// Hit each of the ships
		playerBoard.fireMine(new Position(2, 3));
		playerBoard.fireMine(new Position(5, 5));

		assertThat(playerBoard.getRemainingShipCount(), equalTo(2));
	}

	@Test
	public void testGetRemainingShipCountWhenOneShipHasBeenSunk() {
		playerBoard = new PlayerBoard();

		playerBoard.addShip(new Ship("Submarine", 4, Heading.NORTH, new Position(5, 7)));
		playerBoard.addShip(new Ship("Aircraft Carrier", 5, Heading.WEST, new Position(1, 3)));

		// Hit the Aircraft Carrier once and sink Submarine
		playerBoard.fireMine(new Position(2, 3));

		playerBoard.fireMine(new Position(5, 7));
		playerBoard.fireMine(new Position(5, 6));
		playerBoard.fireMine(new Position(5, 5));
		playerBoard.fireMine(new Position(5, 4));

		assertThat(playerBoard.getRemainingShipCount(), equalTo(1));
	}

	@Test
	public void testGetRemainingShipCountWhenAllShipsHaveBeenSunk() {
		playerBoard = new PlayerBoard();

		playerBoard.addShip(new Ship("Submarine", 4, Heading.NORTH, new Position(5, 7)));
		playerBoard.addShip(new Ship("Aircraft Carrier", 5, Heading.WEST, new Position(1, 3)));

		// Sink both the Aircraft Carrier and the Submarine
		playerBoard.fireMine(new Position(1, 3));
		playerBoard.fireMine(new Position(2, 3));
		playerBoard.fireMine(new Position(3, 3));
		playerBoard.fireMine(new Position(4, 3));
		playerBoard.fireMine(new Position(5, 3));

		playerBoard.fireMine(new Position(5, 7));
		playerBoard.fireMine(new Position(5, 6));
		playerBoard.fireMine(new Position(5, 5));
		playerBoard.fireMine(new Position(5, 4));

		assertThat(playerBoard.getRemainingShipCount(), equalTo(0));
	}
}
