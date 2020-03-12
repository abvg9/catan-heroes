package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildElementRequest extends BaseRequest {

    protected BoardElementType type;

    protected int x;

    protected int y;

    public BuildElementRequest(IPlayer player, BoardElementType type, int x, int y) {
	super(player);

	this.type = type;
	this.x = x;
	this.y = y;
    }

    public BoardElementType getElementType() {
	return this.type;
    }

    public int getX() {
	return this.x;
    }

    public int getY() {
	return this.y;
    }
}
