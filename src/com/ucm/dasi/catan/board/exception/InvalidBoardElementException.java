package com.ucm.dasi.catan.board.exception;

import com.ucm.dasi.catan.board.BoardElementType;

public class InvalidBoardElementException extends Exception {

    private static final long serialVersionUID = -514121800286703032L;

    private BoardElementType wrongType;
    
    public InvalidBoardElementException(BoardElementType wrongType) {
	super(composeMessage(wrongType));
	
	this.wrongType = wrongType;
    }
    
    public BoardElementType getWrongType() {
	return this.wrongType;
    }
    
    private static String composeMessage(BoardElementType wrongType) {
	return String.format("Invalid \"%s\" element.", wrongType == null ? "null" : wrongType.toString());
    }
}
