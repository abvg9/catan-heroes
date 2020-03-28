package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.player.IPlayer;

public class StartTurnRequest extends Request implements IStartTurnRequest {

  public StartTurnRequest(IPlayer player) {
    super(player, RequestType.START_TURN);
  }
}
