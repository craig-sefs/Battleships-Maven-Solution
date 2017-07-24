package com.fm.tdd;

import com.fm.tdd.domain.Ship;
import com.fm.tdd.domain.ShipObserver;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsoleSunkenShipReporterTest {

	private ConsoleSunkenShipReporter sunkenShipReporter;
	private PrintStream printStream;

	@Before
	public void setUp() throws Exception {
		printStream = mock(PrintStream.class);

		sunkenShipReporter = new ConsoleSunkenShipReporter(printStream);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsIllegalArgumentExceptionWhenNullIsPassed() {
		new ConsoleSunkenShipReporter(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWatchShipThrowsIllegalArgumentExceptionWhenNullIsPassed() throws Exception {
		sunkenShipReporter.watchShip(null);
	}

	@Test
	public void testSunkenShipIsReported() {
		Ship ship = mock(Ship.class);
		when(ship.getName()).thenReturn("Battleship");

		sunkenShipReporter.watchShip(ship);

		ArgumentCaptor<ShipObserver> shipObserverArgumentCaptor = ArgumentCaptor.forClass(ShipObserver.class);

		verify(ship).addShipObserver(shipObserverArgumentCaptor.capture());

		ShipObserver shipObserver = shipObserverArgumentCaptor.getValue();

		shipObserver.sunk(ship);

		verify(printStream).println(eq("You sunk my Battleship!!!"));
	}
}