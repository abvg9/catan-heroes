package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public abstract class OwnedElement extends BoardElement implements IOwnedElement {

    private IWarehouse cost;
    
    private IPlayer owner;
    
    public OwnedElement(BoardElementType type, IWarehouse cost, IPlayer owner) {
	super(type);
	
	this.cost = cost;
	this.owner = owner;
    }
    
    @Override
    public IWarehouse getCost() {
	try {
	    return new Warehouse(cost);
	} catch (NegativeNumberException e) {
	    return null;
	}
    }
    
    @Override
    public IPlayer getOwner() {
	return owner;
    }
}
