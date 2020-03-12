package com.ucm.dasi.catan.board.connection;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.warehouse.provider.ConnectionCostProvider;

public class BoardConnection extends OwnedElement implements IBoardConnection {

    protected ConnectionType type;

    public BoardConnection(IPlayer owner, ConnectionType type) {
	super(BoardElementType.Connection, ConnectionCostProvider.buildCostFromType(type), owner);

	this.type = type;
    }

    @Override
    public ConnectionType getType() {
	return type;
    }
}
