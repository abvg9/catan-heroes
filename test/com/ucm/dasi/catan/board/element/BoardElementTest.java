package com.ucm.dasi.catan.board.element;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardElementTest {

    @Test
    public void itMustReturnItsType() {
	BoardElementType type = BoardElementType.Structure;
	BoardElement boardElement = new MinimunBoardElement(type);
	
	assertSame(type, boardElement.getElementType());
    }
}
