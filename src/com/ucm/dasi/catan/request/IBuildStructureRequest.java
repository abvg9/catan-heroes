package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.structure.StructureType;

public interface IBuildStructureRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
