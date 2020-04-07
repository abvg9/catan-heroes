package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeConfirmation;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.Request;
import io.github.notaphplover.catan.core.request.RequestType;

public class TradeConfirmationRequest extends Request implements ITradeConfirmationRequest {

  private ITradeConfirmation tradeConfirmation;

  public TradeConfirmationRequest(IPlayer player, ITradeConfirmation tradeConfirmation) {
    super(player, RequestType.TRADE_CONFIRMATION);

    this.tradeConfirmation = tradeConfirmation;
  }

  @Override
  public ITradeConfirmation getConfirmation() {
    return tradeConfirmation;
  }
}
