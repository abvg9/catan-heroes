package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeDiscard;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.request.Request;
import com.catanheroes.core.request.RequestType;

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
