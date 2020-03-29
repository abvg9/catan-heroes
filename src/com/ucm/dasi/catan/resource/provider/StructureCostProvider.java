package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;

public class StructureCostProvider extends ResourceManagerProvider<StructureType> {

  public StructureCostProvider(Map<StructureType, IResourceManager> costMap) {
    super(costMap);
  }

  public static StructureCostProvider buildDefaultProvider() {

    TreeMap<StructureType, IResourceManager> costMap =
        new TreeMap<StructureType, IResourceManager>();

    costMap.put(StructureType.CITY, buildCityCost());
    costMap.put(StructureType.SETTLEMENT, buildSettlementCost());

    return new StructureCostProvider(costMap);
  }

  private static IResourceManager buildCityCost() {

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();

    costMap.put(ResourceType.GRAIN, 2);
    costMap.put(ResourceType.ORE, 3);

    return new ResourceManager(costMap);
  }

  private static IResourceManager buildSettlementCost() {

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();

    costMap.put(ResourceType.BRICK, 1);
    costMap.put(ResourceType.GRAIN, 1);
    costMap.put(ResourceType.LUMBER, 1);
    costMap.put(ResourceType.WOOL, 1);

    return new ResourceManager(costMap);
  }
}
