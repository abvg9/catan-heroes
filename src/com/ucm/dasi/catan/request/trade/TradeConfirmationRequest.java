package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeConfirmation;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;

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
