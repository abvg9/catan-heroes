package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.game.exception.InvalidReferenceException;
import com.ucm.dasi.catan.game.exception.NoCurrentTradeException;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;

public class TradeManager implements ITradeManager {

  private ITrade trade;

  private TreeMap<UUID, ITradeAgreement> tradeAgreements;

  public TradeManager() {
    tradeAgreements = new TreeMap<UUID, ITradeAgreement>();
  }

  @Override
  public void addAgreement(ITradeAgreement agreement) {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    if (!trade.getId().equals(agreement.getTrade().getId())) {
      throw new InvalidReferenceException(trade, agreement.getTrade());
    }

    tradeAgreements.put(agreement.getId(), agreement);
  }

  @Override
  public void confirm(ITradeConfirmation confirmation) {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    if (!tradeAgreements.containsKey(confirmation.getAgreement().getId())) {
      throw new InvalidReferenceException(confirmation.getAgreement());
    }

    clear();
  }

  @Override
  public void discard() {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    clear();
  }

  @Override
  public Collection<ITradeAgreement> getAgreements() {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    return tradeAgreements.values();
  }

  @Override
  public ITrade getTrade() {
    return trade;
  }

  @Override
  public void start(ITrade trade) {
    this.trade = trade;
    tradeAgreements.clear();
  }

  private void clear() {

    trade = null;
    tradeAgreements.clear();
  }
}
