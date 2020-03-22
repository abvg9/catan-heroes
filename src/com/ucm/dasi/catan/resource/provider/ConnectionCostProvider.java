package com.ucm.dasi.catan.resource.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class ConnectionCostProvider extends CostProvider<ConnectionType> {
    
    public ConnectionCostProvider(Map<ConnectionType, IResourceManager> costMap) {
	super(costMap);
    }
    
    public static ConnectionCostProvider buildDefaultProvider() {
	TreeMap<ConnectionType, IResourceManager> costMap = new TreeMap<ConnectionType, IResourceManager>();
	costMap.put(ConnectionType.Road, buildRoadCost());
	return new ConnectionCostProvider(costMap);
    }

    private static IResourceManager buildRoadCost() {
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Brick, 1);
	costMap.put(ResourceType.Lumber, 1);
	try {
	    return new ResourceManager(costMap);
	} catch (NegativeNumberException e) {
	    return new ResourceManager();
	}
    }
}
