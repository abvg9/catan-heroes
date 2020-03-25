package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.group.IStructureTerrainTypesPair;
import com.ucm.dasi.catan.board.group.StructureTerrainTypesPair;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;

public class TerrainProductionProvider extends ResourceManagerProvider<IStructureTerrainTypesPair>
    implements ITerrainProductionProvider {

  private static final int RESOURCES_PER_CITY = 2;

  private static final int RESOURCES_PER_SETTLEMENT = 1;

  public TerrainProductionProvider(Map<IStructureTerrainTypesPair, IResourceManager> costMap) {
    super(costMap);
  }

  public static TerrainProductionProvider buildDefaultProvider() {

    Map<IStructureTerrainTypesPair, IResourceManager> productionMap =
        new TreeMap<IStructureTerrainTypesPair, IResourceManager>();

    for (TerrainType terrainType : TerrainType.values()) {
      for (StructureType structureType : StructureType.values()) {
        productionMap.put(
            new StructureTerrainTypesPair(structureType, terrainType),
            buildProduction(terrainType, structureType));
      }
    }

    return new TerrainProductionProvider(productionMap);
  }

  private static IResourceManager buildCityProduction(TerrainType type) {
    return buildProduction(type, RESOURCES_PER_CITY);
  }

  private static IResourceManager buildProduction(
      TerrainType terrainType, StructureType structureType) {

    switch (structureType) {
      case City:
        return buildCityProduction(terrainType);
      case Settlement:
        return buildSettlementProduction(terrainType);
      default:
        return new ResourceManager();
    }
  }

  private static IResourceManager buildProduction(TerrainType type, int production) {

    ResourceType resourceType = getResourceAssociatedTo(type);

    Map<ResourceType, Integer> resourceMap = new TreeMap<ResourceType, Integer>();

    if (resourceType != null) {
      resourceMap.put(resourceType, production);
    }

    return new ResourceManager(resourceMap);
  }

  private static IResourceManager buildSettlementProduction(TerrainType type) {
    return buildProduction(type, RESOURCES_PER_SETTLEMENT);
  }

  private static ResourceType getResourceAssociatedTo(TerrainType type) {

    switch (type) {
      case Fields:
        return ResourceType.Grain;
      case Forest:
        return ResourceType.Lumber;
      case Hills:
        return ResourceType.Brick;
      case Mountains:
        return ResourceType.Ore;
      case Pasture:
        return ResourceType.Wool;
      default:
        return null;
    }
  }
}
