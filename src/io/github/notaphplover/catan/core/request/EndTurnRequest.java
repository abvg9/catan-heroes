package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.player.IPlayer;

public class EndTurnRequest extends Request implements IEndTurnRequest {

  public EndTurnRequest(IPlayer player) {
    super(player, RequestType.END_TURN);
  }
}
