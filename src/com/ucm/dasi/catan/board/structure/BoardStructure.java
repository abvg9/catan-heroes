package com.ucm.dasi.catan.board.structure;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardStructure implements IBoardStructure {

    private StructureType type;

    public BoardStructure(StructureType type) {
	this.type = type;
    }

    @Override
    public BoardElementType getElementType() {
	return BoardElementType.Structure;
    }

    @Override
    public StructureType getType() {
	return type;
    }

}
