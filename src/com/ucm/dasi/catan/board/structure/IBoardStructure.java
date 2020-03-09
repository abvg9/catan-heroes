package com.ucm.dasi.catan.board.structure;

import com.ucm.dasi.catan.board.element.IOwnedElement;

public interface IBoardStructure extends IOwnedElement {
    StructureType getType();
}
