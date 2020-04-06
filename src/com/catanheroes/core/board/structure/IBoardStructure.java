package com.catanheroes.core.board.structure;

import com.catanheroes.core.board.element.IOwnedElement;

public interface IBoardStructure extends IOwnedElement {
  StructureType getType();
}
