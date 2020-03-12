package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.warehouse.Warehouse;

public class MinimunOwnedElement extends OwnedElement {

    public MinimunOwnedElement(BoardElementType type, IPlayer owner) {
	super(type, new Warehouse(), owner);
    }
}
