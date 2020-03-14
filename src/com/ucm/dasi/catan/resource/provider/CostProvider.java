package com.ucm.dasi.catan.warehouse.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class CostProvider<TType extends Comparable<TType>> implements ICostProvider<TType> {

    protected TreeMap<TType, IWarehouse> costMap;
    
    public CostProvider(Map<TType, IWarehouse> costMap) {
	this.costMap = new TreeMap<TType, IWarehouse>(costMap);
    }
    
    @Override
    public IWarehouse getCost(TType type) {
	IWarehouse storedCost = this.costMap.get(type);
	
	try {
	    return storedCost == null ? new Warehouse() : new Warehouse(storedCost);
	} catch (NegativeNumberException e) {
	    return new Warehouse();
	}
    }
}
