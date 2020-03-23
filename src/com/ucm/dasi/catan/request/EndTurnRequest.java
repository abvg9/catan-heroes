package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

public class EndTurnRequest extends Request implements IEndTurnRequest {

  public EndTurnRequest(IPlayer player) {
    super(player, RequestType.EndTurn);
  }
}
