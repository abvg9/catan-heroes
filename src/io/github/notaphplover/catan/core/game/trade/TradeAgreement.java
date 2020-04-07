package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
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
