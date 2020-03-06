package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;

public abstract class OwnedElement extends BoardElement implements IOwnedElement {

    private IPlayer owner;
    
    public OwnedElement(BoardElementType type, IPlayer owner) {
	super(type);
	
	this.owner = owner;
    }
    
    public IPlayer getOwner() {
	return this.owner;
    }
}
