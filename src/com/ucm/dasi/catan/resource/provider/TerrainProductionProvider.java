package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.group.IStructureTerrainTypesPair;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Map;

public class TerrainProductionProvider extends ResourceManagerProvider<IStructureTerrainTypesPair>
    implements ITerrainProductionProvider {

  public TerrainProductionProvider(
      Map<IStructureTerrainTypesPair, ? extends IResourceStorage> productionMap) {
    super(productionMap);
  }
}
