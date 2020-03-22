package com.ucm.dasi.catan.board.element;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardElementTest {

    @Test
    public void itMustReturnItsType() {
	BoardElementType type = BoardElementType.Structure;
	BoardElement boardElement = new MinimunBoardElement(type);
	
	assertSame(type, boardElement.getElementType());
    }
}
