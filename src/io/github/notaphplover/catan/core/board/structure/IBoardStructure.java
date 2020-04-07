package io.github.notaphplover.catan.core.board.structure;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;

public interface IBoardStructure extends IOwnedElement {
  StructureType getType();
}
