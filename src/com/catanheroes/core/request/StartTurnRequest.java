package com.catanheroes.core.request;

import com.catanheroes.core.player.IPlayer;

public class StartTurnRequest extends Request implements IStartTurnRequest {

  public StartTurnRequest(IPlayer player) {
    super(player, RequestType.START_TURN);
  }
}
