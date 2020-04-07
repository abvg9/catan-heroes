package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeDiscard;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.Request;
import io.github.notaphplover.catan.core.request.RequestType;

public class TradeDiscardRequest extends Request implements ITradeDiscardRequest {

  private ITradeDiscard discard;

  public TradeDiscardRequest(IPlayer player, ITradeDiscard discard) {
    super(player, RequestType.TRADE_DISCARD);

    this.discard = discard;
  }

  @Override
  public ITradeDiscard getDiscard() {
    return discard;
  }
}
