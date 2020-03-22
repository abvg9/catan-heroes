package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

public interface IRequest {
    IPlayer getPlayer();
    
    RequestType getType();
}
