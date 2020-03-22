package com.ucm.dasi.catan.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class PlayerTest {

    @Test
    public void itMustReturnItsId() throws NegativeNumberException {
	int id = 0;
	Player player = new Player(id, new Warehouse());
	
	assertEquals(id, player.getId());
    }
    
    @Test
    public void itMustReturnItsWarehouse() throws NegativeNumberException {
	IWarehouse warehouse = new Warehouse();
	Player player = new Player(0, warehouse);
	
	assertEquals(warehouse, player.getWarehouse());
    }
}
