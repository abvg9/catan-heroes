package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

public abstract class BaseRequest implements IRequest {
    private IPlayer player;
    
    public BaseRequest(IPlayer player) {
	this.player = player;
    }
    
    @Override
    public IPlayer getPlayer() {
	return this.player;
    }
}
