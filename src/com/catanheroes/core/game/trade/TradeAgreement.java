package com.catanheroes.core.game.trade;

import com.catanheroes.core.resource.IResourceStorage;
import java.util.UUID;

public class TradeAgreement extends Reference implements ITradeAgreement {

  private IResourceStorage exchange;

  private IReference trade;

  public TradeAgreement(UUID id, IResourceStorage exchange, IReference trade) {
    super(id);

    this.exchange = exchange;
    this.trade = trade;
  }

  @Override
  public IResourceStorage getExchange() {
    return exchange;
  }

  @Override
  public IReference getTrade() {
    return trade;
  }
}
