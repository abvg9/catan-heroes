package com.catanheroes.core.resource.provider;

import com.catanheroes.core.board.group.IStructureTerrainTypesPair;
import com.catanheroes.core.resource.IResourceStorage;
import java.util.Map;

public class TerrainProductionProvider extends ResourceManagerProvider<IStructureTerrainTypesPair>
    implements ITerrainProductionProvider {

  public TerrainProductionProvider(
      Map<IStructureTerrainTypesPair, ? extends IResourceStorage> productionMap) {
    super(productionMap);
  }
}
