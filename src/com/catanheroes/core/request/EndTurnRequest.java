package com.catanheroes.core.request;

import com.catanheroes.core.player.IPlayer;

public class EndTurnRequest extends Request implements IEndTurnRequest {

  public EndTurnRequest(IPlayer player) {
    super(player, RequestType.END_TURN);
  }
}
