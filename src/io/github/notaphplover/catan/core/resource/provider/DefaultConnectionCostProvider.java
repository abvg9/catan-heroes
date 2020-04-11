package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
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
