package com.ucm.dasi.catan.request;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class BuildStructureRequestTest {
    @Test
    public void mustStoreConnectionType() throws NegativeNumberException {
	IPlayer player = new Player(0, new Warehouse());
	StructureType type = StructureType.Settlement;
	
	BuildStructureRequest request = new BuildStructureRequest(player, type, 0, 1);
    
	assertSame(type, request.getType());
    }
}
