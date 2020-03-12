package com.ucm.dasi.catan.board.structure;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.warehouse.provider.StructureCostProvider;

public class BoardStructure extends OwnedElement implements IBoardStructure {

    private StructureType type;

    public BoardStructure(IPlayer owner, StructureType type) {
	super(BoardElementType.Structure, StructureCostProvider.buildCostFromType(type), owner);

	this.type = type;
    }

    @Override
    public StructureType getType() {
	return type;
    }
}
