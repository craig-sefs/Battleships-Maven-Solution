package com.fm.tdd;

import com.fm.tdd.domain.Position;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PositionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testXCoordinateLessThan0ThrowsIllegalArgumentException() throws Exception {
		new Position(-43, 7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testYCoordinateLessThan0ThrowsIllegalArgumentException() throws Exception {
		new Position(7, -67);
	}

	@Test
	public void testConstructingPosition() throws Exception {
		Position position = new Position(3, 5);

		assertThat(position.getX(), equalTo(3));
		assertThat(position.getY(), equalTo(5));
	}
}