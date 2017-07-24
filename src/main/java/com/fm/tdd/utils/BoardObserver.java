package com.fm.tdd.utils;

import com.fm.tdd.domain.Position;

public interface BoardObserver {

	void hit(Position position);

	void miss(Position position);

}
