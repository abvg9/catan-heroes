package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildConnectionRequest extends BuildElementRequest implements IBuildConnectionRequest {

    private ConnectionType type;
    
    public BuildConnectionRequest(IPlayer player, ConnectionType type,  int x, int y) {
	super(player, BoardElementType.Connection, x, y);
	
	this.type = type;
    }

    @Override
    public ConnectionType getType() {
	return type;
    }

}
