package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

public abstract class Request implements IRequest {
    private IPlayer player;
    
    public Request(IPlayer player) {
	this.player = player;
    }
    
    @Override
    public IPlayer getPlayer() {
	return this.player;
    }
}
