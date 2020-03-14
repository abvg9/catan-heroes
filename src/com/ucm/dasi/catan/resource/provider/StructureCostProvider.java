package com.ucm.dasi.catan.warehouse.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class StructureCostProvider extends CostProvider<StructureType> {

    public StructureCostProvider(Map<StructureType, IWarehouse> costMap) {
	super(costMap);
    }

    public static StructureCostProvider buildDefaultProvider() {
	TreeMap<StructureType, IWarehouse> costMap = new TreeMap<StructureType, IWarehouse>();
	costMap.put(StructureType.City, buildCityCost());
	costMap.put(StructureType.Settlement, buildSettlementCost());
	return new StructureCostProvider(costMap);
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
