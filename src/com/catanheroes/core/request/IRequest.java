package com.catanheroes.core.request;

import com.catanheroes.core.player.IPlayer;

public interface IRequest {
  IPlayer getPlayer();

  RequestType getType();
}
