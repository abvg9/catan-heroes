package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeDiscard;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;

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
