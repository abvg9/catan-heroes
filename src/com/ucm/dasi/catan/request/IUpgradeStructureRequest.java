package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.structure.StructureType;

public interface IUpgradeStructureRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
