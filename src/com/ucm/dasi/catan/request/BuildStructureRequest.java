package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildStructureRequest extends BuildElementRequest implements IBuildStructureRequest {

    private StructureType type;

    public BuildStructureRequest(IPlayer player, BoardElementType elementType, StructureType type, int x, int y) {
	super(player, elementType, x, y);

	this.type = type;
    }

    @Override
    public StructureType getType() {
	return type;
    }

}
