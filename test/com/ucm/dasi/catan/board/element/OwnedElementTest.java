package com.ucm.dasi.catan.board.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class OwnedElementTest {

    @Test
    public void itMustReturnItsCost() throws NegativeNumberException {
	IPlayer player = new Player(0, new Warehouse());
	
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Wool, 4);
	
	IWarehouse cost = new Warehouse(costMap);
	OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, cost, player);
	
	assertEquals(cost, element.getCost());
    }
    
    @Test
    public void itMustReturnItsType() throws NegativeNumberException {
	IPlayer player = new Player(0, new Warehouse());
	OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, new Warehouse(), player);
	
	assertSame(player, element.getOwner());
    }
}
