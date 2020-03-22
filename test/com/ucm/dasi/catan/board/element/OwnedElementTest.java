package com.ucm.dasi.catan.board.element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class OwnedElementTest {

    @Test
    public void itMustReturnItsCost() throws NegativeNumberException {
	IPlayer player = new Player(0, new ResourceManager());
	
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Wool, 4);
	
	IResourceManager cost = new ResourceManager(costMap);
	OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, cost, player);
	
	assertEquals(cost, element.getCost());
    }
    
    @Test
    public void itMustReturnItsType() throws NegativeNumberException {
	IPlayer player = new Player(0, new ResourceManager());
	OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, new ResourceManager(), player);
	
	assertSame(player, element.getOwner());
    }
}
