package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.board.group.IStructureTerrainTypesPair;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import java.util.Map;

public class TerrainProductionProvider extends ResourceManagerProvider<IStructureTerrainTypesPair>
    implements ITerrainProductionProvider {

  public TerrainProductionProvider(
      Map<IStructureTerrainTypesPair, ? extends IResourceStorage> productionMap) {
    super(productionMap);
  }
}
