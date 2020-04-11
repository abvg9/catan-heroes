package io.github.notaphplover.catan.core.game.trade;

import java.util.UUID;

public class TradeDiscard extends Reference implements ITradeDiscard {

  private IReference trade;

  public TradeDiscard(UUID id, IReference trade) {
    super(id);

    this.trade = trade;
  }

  @Override
  public IReference getTrade() {
    return trade;
  }
}
