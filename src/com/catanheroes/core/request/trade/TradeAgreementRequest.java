package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeAgreement;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.request.Request;
import com.catanheroes.core.request.RequestType;

public class TradeAgreementRequest extends Request implements ITradeAgreementRequest {

  private ITradeAgreement tradeAgreement;

  public TradeAgreementRequest(IPlayer player, ITradeAgreement tradeAgreement) {
    super(player, RequestType.TRADE_AGREEMENT);

    this.tradeAgreement = tradeAgreement;
  }

  @Override
  public ITradeAgreement getTradeAgreement() {
    return tradeAgreement;
  }
}
