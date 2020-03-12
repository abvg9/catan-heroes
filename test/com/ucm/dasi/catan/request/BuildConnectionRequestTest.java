package com.ucm.dasi.catan.request;

import static org.junit.Assert.assertSame;
import org.junit.Test;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class BuildConnectionRequestTest {
    
    @Test
    public void mustStoreConnectionType() throws NegativeNumberException {
	IPlayer player = new Player(0, new Warehouse());
	ConnectionType type = ConnectionType.Road;
	
	BuildConnectionRequest request = new BuildConnectionRequest(player, type, 0, 1);
    
	assertSame(type, request.getType());
    }
}
