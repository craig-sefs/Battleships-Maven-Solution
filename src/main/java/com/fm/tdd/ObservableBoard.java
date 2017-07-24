package com.fm.tdd;

import com.fm.tdd.utils.BoardObserver;

public interface ObservableBoard {

	void addBoardObserver(BoardObserver boardObserver);

	void removeBoardObserver(BoardObserver boardObserver);
}
