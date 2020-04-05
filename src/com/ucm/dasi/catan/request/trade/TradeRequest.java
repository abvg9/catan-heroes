package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITrade;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;

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
