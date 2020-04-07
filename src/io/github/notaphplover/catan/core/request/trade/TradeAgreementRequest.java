package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.Request;
import io.github.notaphplover.catan.core.request.RequestType;

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
