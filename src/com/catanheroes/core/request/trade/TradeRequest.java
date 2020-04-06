package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITrade;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.request.Request;
import com.catanheroes.core.request.RequestType;

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
