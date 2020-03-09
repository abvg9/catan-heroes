package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class Player implements IPlayer {

    protected int id;
    
    protected IWarehouse warehouse;
    
    public Player(int id, IWarehouse warehouse) throws NegativeNumberException {
	this.id = id;
	this.warehouse = new Warehouse(warehouse);
    }
    
    @Override
    public int getId() {
	return id;
    }

    @Override
    public IWarehouse getWarehouse() {
	return warehouse;
    }

    
}
