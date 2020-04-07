package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.structure.StructureType;

public interface IBuildStructureRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
