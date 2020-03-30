package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceStorage;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;

public class DefaultStructureCostProvider extends StructureCostProvider {

  public DefaultStructureCostProvider() {
    super(buildResourcesMap());
  }

  public static TreeMap<StructureType, IResourceStorage> buildResourcesMap() {

    TreeMap<StructureType, IResourceStorage> resourcesMap =
        new TreeMap<StructureType, IResourceStorage>();

    resourcesMap.put(StructureType.CITY, buildCityCost());
    resourcesMap.put(StructureType.SETTLEMENT, buildSettlementCost());

    return resourcesMap;
  }

  private static IResourceStorage buildCityCost() {

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();

    costMap.put(ResourceType.GRAIN, 2);
    costMap.put(ResourceType.ORE, 3);

    return new ResourceStorage(costMap);
  }

  private static IResourceStorage buildSettlementCost() {

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();

    costMap.put(ResourceType.BRICK, 1);
    costMap.put(ResourceType.GRAIN, 1);
    costMap.put(ResourceType.LUMBER, 1);
    costMap.put(ResourceType.WOOL, 1);

    return new ResourceStorage(costMap);
  }
}
