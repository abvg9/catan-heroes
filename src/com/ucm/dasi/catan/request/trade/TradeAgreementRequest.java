package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeAgreement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;

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
