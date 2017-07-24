package com.fm.tdd;

/**
 * <p>Enum for the different values that can be stored in a 2D array grid</p>
 *
 * <p>Each value has a hitCharacter which is the character that should be printed when printing the grid</p>
 */
public enum CoordinateState {

	HIT("X"),
	MISS("0"),
	EMPTY("."),
	SHIP_UNHIT("S"),
	SHIP_HIT(HIT.getHitCharacter());

	private final String hitCharacter;

	CoordinateState(String hitCharacter) {
		this.hitCharacter = hitCharacter;
	}

	public String getHitCharacter() {
		return hitCharacter;
	}
}
