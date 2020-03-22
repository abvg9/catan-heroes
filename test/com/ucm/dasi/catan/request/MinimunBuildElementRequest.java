package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;

public class MinimunBuildElementRequest extends BuildElementRequest {

    public MinimunBuildElementRequest(IPlayer player, BoardElementType type, int x, int y) {
	super(player, type, x, y);
    }

}
