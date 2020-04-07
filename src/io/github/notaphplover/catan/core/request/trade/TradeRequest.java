package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITrade;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.Request;
import io.github.notaphplover.catan.core.request.RequestType;

public class TradeRequest extends Request implements ITradeRequest {

  private ITrade trade;

  public TradeRequest(IPlayer player, ITrade trade) {
    super(player, RequestType.TRADE);

    this.trade = trade;
  }

  @Override
  public ITrade getTrade() {
    return trade;
  }
}
