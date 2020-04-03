package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.InvalidReferenceException;
import com.ucm.dasi.catan.game.exception.NoCurrentTradeException;
import com.ucm.dasi.catan.game.exception.NotAnAcceptableExchangeException;
import com.ucm.dasi.catan.game.exception.PendingTradeException;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

public class TradeManager implements ITradeManager {

  private ITrade trade;

  private TreeMap<UUID, ITradeAgreement> tradeAgreements;

  private TreeSet<IResourceStorage> tradeExchangesSet;

  public TradeManager() {
    tradeAgreements = new TreeMap<UUID, ITradeAgreement>();
    tradeExchangesSet = new TreeSet<IResourceStorage>();
  }

  @Override
  public void addAgreement(ITradeAgreement agreement)
      throws NonNullInputException, NotAnAcceptableExchangeException, InvalidReferenceException,
          NoCurrentTradeException {

    if (agreement == null) {
      throw new NonNullInputException();
    }

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    if (!trade.getId().equals(agreement.getTrade().getId())) {
      throw new InvalidReferenceException(trade, agreement.getTrade());
    }

    if (!tradeExchangesSet.contains(agreement.getExchange())) {
      throw new NotAnAcceptableExchangeException(trade);
    }

    tradeAgreements.put(agreement.getId(), agreement);
  }

  @Override
  public void confirm(ITradeConfirmation confirmation)
      throws InvalidReferenceException, NoCurrentTradeException {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    if (!tradeAgreements.containsKey(confirmation.getAgreement().getId())) {
      throw new InvalidReferenceException(confirmation.getAgreement());
    }

    clear();
  }

  @Override
  public void discard() throws NoCurrentTradeException {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    clear();
  }

  @Override
  public Collection<ITradeAgreement> getAgreements() throws NoCurrentTradeException {

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
  public void start(ITrade trade) throws NonNullInputException, NonVoidCollectionException {

    if (this.trade != null) {
      throw new PendingTradeException(this.trade);
    }

    this.trade = trade;
    clearMaps();
    processTradeExchanges();
  }

  private void clear() {

    trade = null;
    clearMaps();
  }

  private void clearMaps() {
    tradeAgreements.clear();
    tradeExchangesSet.clear();
  }

  private void processTradeExchanges() throws NonNullInputException, NonVoidCollectionException {
    Collection<IResourceStorage> acceptableExchanges = trade.getAcceptableExchanges();
    if (acceptableExchanges == null) {
      throw new NonNullInputException();
    }
    if (acceptableExchanges.isEmpty()) {
      throw new NonVoidCollectionException();
    }
    for (IResourceStorage exchange : trade.getAcceptableExchanges()) {
      tradeExchangesSet.add(exchange);
    }
  }
}
