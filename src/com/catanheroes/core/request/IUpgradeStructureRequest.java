package com.catanheroes.core.request;

import com.catanheroes.core.board.structure.StructureType;

public interface IUpgradeStructureRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
