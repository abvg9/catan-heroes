package com.catanheroes.core.request;

import com.catanheroes.core.player.IPlayer;

public abstract class Request implements IRequest {
  private IPlayer player;

  private RequestType type;

  public Request(IPlayer player, RequestType type) {
    this.player = player;
    this.type = type;
  }

  @Override
  public IPlayer getPlayer() {
    return player;
  }

  @Override
  public RequestType getType() {
    return type;
  }
}
