package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.player.IPlayer;

public class StartTurnRequest extends Request implements IStartTurnRequest {

  public StartTurnRequest(IPlayer player) {
    super(player, RequestType.START_TURN);
  }
}
