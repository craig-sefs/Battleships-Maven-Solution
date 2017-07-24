package com.fm.tdd;

import com.fm.tdd.domain.Ship;
import com.fm.tdd.domain.ShipObserver;
import com.fm.tdd.utils.Assert;
import java.io.PrintStream;

/**
 * Reports to standard out when a {@link Ship} has sunk
 */
public class ConsoleSunkenShipReporter {

	private PrintStream printStream;
	private ShipObserver shipObserver = new ShipObserver() {
		@Override
		public void sunk(Ship ship) {
			printStream.println("You sunk my " + ship.getName() + "!!!");
		}
	};

	public ConsoleSunkenShipReporter(PrintStream printStream) {
		Assert.notNull(printStream, "printStream cannot be null");

		this.printStream = printStream;
	}

	public void watchShip(Ship ship) {
		Assert.notNull(ship, "ship cannot be null");

		ship.addShipObserver(shipObserver);
	}

}
