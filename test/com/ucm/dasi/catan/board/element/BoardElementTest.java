package com.ucm.dasi.catan.board.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardElementTest {

    @Test
    public void itMustReturnItsType() {
	BoardElementType type = BoardElementType.Structure;
	BoardElement boardElement = new MinimunBoardElement(type);
	
	assertEquals(type, boardElement.getElementType());
    }
}
