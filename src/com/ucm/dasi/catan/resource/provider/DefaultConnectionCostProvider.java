package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceStorage;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;

public class DefaultConnectionCostProvider extends ConnectionCostProvider {

  public DefaultConnectionCostProvider() {
    super(buildResourcesMap());
  }

  private static TreeMap<ConnectionType, IResourceStorage> buildResourcesMap() {

    TreeMap<ConnectionType, IResourceStorage> costMap =
        new TreeMap<ConnectionType, IResourceStorage>();

    costMap.put(ConnectionType.ROAD, buildRoadCost());

    return costMap;
  }

  private static IResourceStorage buildRoadCost() {

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();

    costMap.put(ResourceType.BRICK, 1);
    costMap.put(ResourceType.LUMBER, 1);

    return new ResourceStorage(costMap);
  }
}
