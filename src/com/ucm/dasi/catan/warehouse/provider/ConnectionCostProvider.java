package com.ucm.dasi.catan.warehouse.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class ConnectionCostProvider {
    public static IWarehouse buildCostFromType(ConnectionType type) {
	switch (type) {
	case Road: {
	    return buildRoadCost();
	}
	default:
	    return new Warehouse();
	}
    }

    private static IWarehouse buildRoadCost() {
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Brick, 1);
	costMap.put(ResourceType.Lumber, 1);
	try {
	    return new Warehouse(costMap);
	} catch (NegativeNumberException e) {
	    return new Warehouse();
	}
    }
}
