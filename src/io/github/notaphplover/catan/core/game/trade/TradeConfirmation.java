package io.github.notaphplover.catan.core.game.trade;

import java.util.UUID;

public class TradeConfirmation extends Reference implements ITradeConfirmation {

  IReference agreement;

  public TradeConfirmation(UUID id, IReference agreement) {
    super(id);

    this.agreement = agreement;
  }

  @Override
  public IReference getAgreement() {
    return agreement;
  }
}
