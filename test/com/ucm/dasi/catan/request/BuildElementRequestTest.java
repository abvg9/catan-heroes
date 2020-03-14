package com.ucm.dasi.catan.request;

import static org.junit.Assert.assertSame;
import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class BuildElementRequestTest {
    
    @Test
    public void mustStoreInitialValues() throws NegativeNumberException {
	IPlayer player = new Player(0, new ResourceManager());
	BoardElementType type = BoardElementType.Connection;
	int x = 0;
	int y = 1;
	
	MinimunBuildElementRequest request = new MinimunBuildElementRequest(player, type, x, y);
    
	assertSame(type, request.getElementType());
	assertSame(x, request.getX());
	assertSame(y, request.getY());
    }
}
