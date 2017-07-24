package com.fm.tdd;

import com.fm.tdd.domain.Position;
import com.fm.tdd.domain.Ship;

public interface Board {

	void addShip(Ship ship);

	void fireMine(Position position);

	int getRemainingShipCount();
}
