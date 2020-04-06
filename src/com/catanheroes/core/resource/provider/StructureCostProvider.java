package com.catanheroes.core.resource.provider;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.resource.IResourceStorage;
import java.util.Map;

public class StructureCostProvider extends ResourceManagerProvider<StructureType> {

  public StructureCostProvider(Map<StructureType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
