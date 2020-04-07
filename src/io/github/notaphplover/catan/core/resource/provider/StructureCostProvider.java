package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import java.util.Map;

public class StructureCostProvider extends ResourceManagerProvider<StructureType> {

  public StructureCostProvider(Map<StructureType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
