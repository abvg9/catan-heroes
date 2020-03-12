package com.ucm.dasi.catan.warehouse.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class StructureCostProvider {
    
    public static IWarehouse buildCostFromType(StructureType type) {
	switch (type) {
	case City:
	    return buildCityCost();
	case Settlement:
	    return buildSettlementCost();
	default:
	    return new Warehouse();
	}
    }
    
    private static IWarehouse buildCityCost() {
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Grain, 2);
	costMap.put(ResourceType.Ore, 3);

	try {
	    return new Warehouse(costMap);
	} catch (NegativeNumberException e) {
	    return new Warehouse();
	}
    }

    private static IWarehouse buildSettlementCost() {
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Brick, 1);
	costMap.put(ResourceType.Grain, 1);
	costMap.put(ResourceType.Lumber, 1);
	costMap.put(ResourceType.Wool, 1);

	try {
	    return new Warehouse(costMap);
	} catch (NegativeNumberException e) {
	    return new Warehouse();
	}
    }
}
