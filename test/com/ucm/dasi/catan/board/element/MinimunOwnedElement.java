package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;

public class MinimunOwnedElement extends OwnedElement {

    public MinimunOwnedElement(BoardElementType type, IResourceManager cost,  IPlayer owner) {
	super(type, cost, owner);
    }
}
