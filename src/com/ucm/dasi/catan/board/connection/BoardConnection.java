package com.ucm.dasi.catan.board.connection;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;

public class BoardConnection extends OwnedElement implements IBoardConnection {

    protected ConnectionType type;

    public BoardConnection(IPlayer owner, IResourceManager cost, ConnectionType type) {
	super(BoardElementType.Connection, cost, owner);

	this.type = type;
    }

    @Override
    public ConnectionType getType() {
	return type;
    }
}
