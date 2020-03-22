package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;

public interface ICatanEditableBoard extends ICatanBoard {
    void build(IBoardElement element, int x, int y) throws InvalidBoardElementException;
}
