package com.ucm.dasi.catan.request;

import static org.junit.Assert.assertSame;
import org.junit.Test;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class RequestTest {

    @Test()
    public void itMustStoreItsPlayer() throws NegativeNumberException {
	IPlayer player = new Player(0, new ResourceManager());
	
	MinimunRequest request = new MinimunRequest(player);
	
	assertSame(player, request.getPlayer());
    }
}
