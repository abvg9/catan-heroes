package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

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
