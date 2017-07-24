package com.fm.tdd.drawer;

import com.fm.tdd.CoordinateState;
import com.fm.tdd.DrawableBoard;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsoleBoardDrawerTest {

	private ConsoleBoardDrawer opponentBoardDrawer;
	private PrintStream printStream;

	@Before
	public void setUp() throws Exception {
		printStream = mock(PrintStream.class);

		opponentBoardDrawer = new ConsoleBoardDrawer(printStream);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPassingNullThrowsIllegalArgumentException() {
		new ConsoleBoardDrawer(null);
	}

	@Test
	public void testDrawInOpponentStyle() {
		CoordinateState[][] coordinateStates = new CoordinateState[][]{
				{CoordinateState.MISS, CoordinateState.EMPTY, CoordinateState.EMPTY, CoordinateState.EMPTY},
				{CoordinateState.HIT, CoordinateState.EMPTY, CoordinateState.MISS, CoordinateState.MISS},
				{CoordinateState.HIT, CoordinateState.MISS, CoordinateState.EMPTY, CoordinateState.EMPTY},
				{CoordinateState.HIT, CoordinateState.EMPTY, CoordinateState.EMPTY, CoordinateState.EMPTY}
		};

		DrawableBoard drawableBoard = mock(DrawableBoard.class);
		when(drawableBoard.getGrid()).thenReturn(coordinateStates);

		opponentBoardDrawer.draw(drawableBoard);

		InOrder inOrder = inOrder(printStream);
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.MISS.getHitCharacter(), CoordinateState.HIT.getHitCharacter(), CoordinateState.HIT.getHitCharacter(), CoordinateState.HIT.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
	}

	@Test
	public void testDrawInOwnerStyle() {
		CoordinateState[][] coordinateStates = new CoordinateState[][]{
				{CoordinateState.MISS, CoordinateState.EMPTY, CoordinateState.EMPTY, CoordinateState.EMPTY},
				{CoordinateState.SHIP_HIT, CoordinateState.EMPTY, CoordinateState.MISS, CoordinateState.MISS},
				{CoordinateState.SHIP_HIT, CoordinateState.MISS, CoordinateState.EMPTY, CoordinateState.EMPTY},
				{CoordinateState.SHIP_HIT, CoordinateState.EMPTY, CoordinateState.SHIP_UNHIT, CoordinateState.SHIP_UNHIT}
		};

		DrawableBoard drawableBoard = mock(DrawableBoard.class);
		when(drawableBoard.getGrid()).thenReturn(coordinateStates);

		opponentBoardDrawer.draw(drawableBoard);

		InOrder inOrder = inOrder(printStream);
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.SHIP_UNHIT.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.SHIP_UNHIT.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.EMPTY.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter(), CoordinateState.MISS.getHitCharacter(), CoordinateState.EMPTY.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
		inOrder.verify(printStream).println(eq(String.format("|%s|%s|%s|%s|", CoordinateState.MISS.getHitCharacter(), CoordinateState.SHIP_HIT.getHitCharacter(), CoordinateState.SHIP_HIT.getHitCharacter(), CoordinateState.SHIP_HIT.getHitCharacter())));
		inOrder.verify(printStream).println(eq("---------"));
	}
}