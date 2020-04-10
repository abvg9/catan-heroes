package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.structure.StructureType;

public interface IStructureRelatedRequest extends IBuildElementRequest {
  StructureType getStructureType();
}
