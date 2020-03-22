package com.ucm.dasi.catan.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class RequestTest {

    @Test
    public void itMustStoreItsPlayer() throws NegativeNumberException {
	IPlayer player = new Player(0, new ResourceManager());
	
	MinimunRequest request = new MinimunRequest(player, RequestType.BuildConnection);
	
	assertSame(player, request.getPlayer());
    }
    
    @Test()
    public void itMustStoreItsRequestType() throws NegativeNumberException {
	RequestType requestType = RequestType.BuildConnection;
	
	MinimunRequest request = new MinimunRequest(new Player(0, new ResourceManager()), requestType);
	
	assertSame(requestType, request.getType());
    }
}
