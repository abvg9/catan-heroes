package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Map;

public class StructureCostProvider extends ResourceManagerProvider<StructureType> {

  public StructureCostProvider(Map<StructureType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
