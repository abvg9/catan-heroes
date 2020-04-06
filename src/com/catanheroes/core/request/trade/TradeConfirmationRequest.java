package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeConfirmation;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.request.Request;
import com.catanheroes.core.request.RequestType;

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
