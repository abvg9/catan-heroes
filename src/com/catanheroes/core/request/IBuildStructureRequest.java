package com.catanheroes.core.request;

import com.catanheroes.core.board.structure.StructureType;

public interface IBuildStructureRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
