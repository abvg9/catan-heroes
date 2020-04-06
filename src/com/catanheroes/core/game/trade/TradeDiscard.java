package com.catanheroes.core.game.trade;

import java.util.UUID;

public class TradeDiscard extends Reference implements ITradeDiscard {

  IReference trade;

  public TradeDiscard(UUID id, IReference trade) {
    super(id);

    this.trade = trade;
  }

  @Override
  public IReference getTrade() {
    return trade;
  }
}
