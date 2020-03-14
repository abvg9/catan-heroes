package com.ucm.dasi.catan.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class PlayerTest {

    @Test
    public void itMustReturnItsId() throws NegativeNumberException {
	int id = 0;
	Player player = new Player(id, new ResourceManager());
	
	assertEquals(id, player.getId());
    }
    
    @Test
    public void itMustReturnItsWarehouse() throws NegativeNumberException {
	IResourceManager warehouse = new ResourceManager();
	Player player = new Player(0, warehouse);
	
	assertEquals(warehouse, player.getWarehouse());
    }
}
