package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;

public abstract class BoardElement implements IBoardElement {
    private BoardElementType type;
    
    public BoardElement(BoardElementType type) {
	this.type = type;
    }
    
    public BoardElementType getElementType() {
	return this.type;
    }
}
