package com.fm.tdd;

import com.fm.tdd.domain.Position;
import com.fm.tdd.utils.BoardObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OpponentBoardTest {

	private OpponentBoard opponentBoard;

	@Before
	public void setUp() {
		opponentBoard = new OpponentBoard();
	}

	@Test
	public void testOpponentBoardInitialisedWithEmptyGrid() {
		CoordinateState[][] grid = opponentBoard.getGrid();

		for (CoordinateState[] coordinateStates : grid) {
			for (CoordinateState coordinateState : coordinateStates) {
				assertThat(coordinateState, equalTo(CoordinateState.EMPTY));
			}
		}
	}

	@Test
	public void testOpponentBoardInitialisedWithEmptyGridWithCorrectDimensions() {
		CoordinateState[][] grid = opponentBoard.getGrid();

		assertThat(grid.length, equalTo(PlayerBoard.BOARD_WIDTH));

		for (CoordinateState[] coordinateStates : grid) {
			assertThat(coordinateStates.length, equalTo(PlayerBoard.BOARD_HEIGHT));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		opponentBoard.register(null);
	}

	@Test
	public void testGridUpdatedWithHitWhenObservedBoardNotifiesOfHit() {
		BoardObserver boardObserver = retrieveBoardObserver();

		boardObserver.hit(new Position(4, 4));

		CoordinateState[][] grid = opponentBoard.getGrid();

		for (int i = 0; i < grid.length; i++) {
			CoordinateState[] coordinateStates = grid[i];
			for (int j = 0; j < coordinateStates.length; j++) {
				CoordinateState coordinateState = coordinateStates[j];

				if (i == 4 && j == 4) {
					assertThat(coordinateState, equalTo(CoordinateState.HIT));
				} else {
					assertThat(coordinateState, equalTo(CoordinateState.EMPTY));
				}
			}
		}
	}

	@Test
	public void testGridUpdatedWithMissWhenObservedBoardNotifiesOfMiss() {
		BoardObserver boardObserver = retrieveBoardObserver();

		boardObserver.miss(new Position(5, 5));

		CoordinateState[][] grid = opponentBoard.getGrid();

		for (int i = 0; i < grid.length; i++) {
			CoordinateState[] coordinateStates = grid[i];
			for (int j = 0; j < coordinateStates.length; j++) {
				CoordinateState coordinateState = coordinateStates[j];

				if (i == 5 && j == 5) {
					assertThat(coordinateState, equalTo(CoordinateState.MISS));
				} else {
					assertThat(coordinateState, equalTo(CoordinateState.EMPTY));
				}
			}
		}
	}

	private BoardObserver retrieveBoardObserver() {
		ObservableBoard observableBoard = mock(ObservableBoard.class);

		opponentBoard.register(observableBoard);

		ArgumentCaptor<BoardObserver> boardObserverArgumentCaptor = ArgumentCaptor.forClass(BoardObserver.class);

		verify(observableBoard).addBoardObserver(boardObserverArgumentCaptor.capture());
		return boardObserverArgumentCaptor.getValue();
	}

}
