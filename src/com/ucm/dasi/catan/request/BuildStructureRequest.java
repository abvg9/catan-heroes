package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildStructureRequest extends BuildElementRequest implements IBuildStructureRequest {

    private StructureType type;

    public BuildStructureRequest(IPlayer player, StructureType type, int x, int y) {
	super(player, BoardElementType.Structure, x, y);

	this.type = type;
    }

    @Override
    public StructureType getType() {
	return type;
    }

}
