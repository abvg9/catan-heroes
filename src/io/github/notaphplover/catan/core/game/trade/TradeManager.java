package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.exception.UnexpectedException;
import io.github.notaphplover.catan.core.game.exception.AgreementAlreadyProposedException;
import io.github.notaphplover.catan.core.game.exception.InvalidReferenceException;
import io.github.notaphplover.catan.core.game.exception.NoCurrentTradeException;
import io.github.notaphplover.catan.core.game.exception.NotAnAcceptableExchangeException;
import io.github.notaphplover.catan.core.game.exception.PendingTradeException;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

public class TradeManager implements ITradeManager {

  private TreeMap<UUID, IPlayer> agreementToPlayerMap;

  private TreeMap<IResourceStorage, Collection<IPlayer>> exchangeToPlayersMap;

  private IPlayer buyer;

  private ITrade trade;

  private TreeMap<UUID, ITradeAgreement> tradeAgreements;

  private TreeSet<IResourceStorage> tradeExchangesSet;

  public TradeManager() {

    agreementToPlayerMap = new TreeMap<UUID, IPlayer>();
    exchangeToPlayersMap = new TreeMap<IResourceStorage, Collection<IPlayer>>();
    tradeAgreements = new TreeMap<UUID, ITradeAgreement>();
    tradeExchangesSet = new TreeSet<IResourceStorage>();
  }

  @Override
  public void addAgreement(IPlayer player, ITradeAgreement agreement)
      throws NonNullInputException, NotAnAcceptableExchangeException, InvalidReferenceException,
          NoCurrentTradeException, NotEnoughtResourcesException, AgreementAlreadyProposedException {

    if (player == null || agreement == null) {
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

    Collection<IPlayer> exchangePlayers = exchangeToPlayersMap.get(agreement.getExchange());
    if (exchangePlayers.contains(player)) {
      throw new AgreementAlreadyProposedException(agreement, player);
    }

    if (!player.getResourceManager().canSubstract(trade.getRequestedResources())) {
      throw new NotEnoughtResourcesException();
    }

    agreementToPlayerMap.put(agreement.getId(), player);
    exchangePlayers.add(player);
    tradeAgreements.put(agreement.getId(), agreement);
  }

  @Override
  public ITradeAgreement confirm(ITradeConfirmation confirmation)
      throws InvalidReferenceException, NoCurrentTradeException {

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    ITradeAgreement agreement = tradeAgreements.get(confirmation.getAgreement().getId());

    if (agreement == null) {
      throw new InvalidReferenceException(confirmation.getAgreement());
    }

    IPlayer seller = agreementToPlayerMap.get(agreement.getId());

    try {
      buyer.getResourceManager().substract(agreement.getExchange());
      seller.getResourceManager().substract(trade.getRequestedResources());
    } catch (NotEnoughtResourcesException e) {
      throw new UnexpectedException(e);
    }

    buyer.getResourceManager().add(trade.getRequestedResources());
    seller.getResourceManager().add(agreement.getExchange());

    clear();

    return agreement;
  }

  @Override
  public void discard(ITradeDiscard discard)
      throws NoCurrentTradeException, NonNullInputException, InvalidReferenceException {

    if (discard == null) {
      throw new NonNullInputException();
    }

    if (trade == null) {
      throw new NoCurrentTradeException();
    }

    if (!trade.getId().equals(discard.getTrade().getId())) {
      throw new InvalidReferenceException(discard.getTrade());
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
  public IPlayer getBuyer() {
    return buyer;
  }

  @Override
  public ITrade getTrade() {
    return trade;
  }

  @Override
  public void start(IPlayer player, ITrade trade)
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    if (player == null || trade == null) {
      throw new NonNullInputException();
    }

    if (this.trade != null) {
      throw new PendingTradeException(this.trade);
    }

    buyer = player;
    this.trade = trade;

    clearMaps();
    processTradeExchanges();
  }

  private void clear() {

    buyer = null;
    trade = null;
    clearMaps();
  }

  private void clearMaps() {

    agreementToPlayerMap.clear();
    exchangeToPlayersMap.clear();
    tradeAgreements.clear();
    tradeExchangesSet.clear();
  }

  private void processTradeExchanges()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    IResourceStorage buyerResources = buyer.getResourceManager();

    Collection<IResourceStorage> acceptableExchanges = trade.getAcceptableExchanges();
    if (acceptableExchanges == null) {
      throw new NonNullInputException();
    }
    if (acceptableExchanges.isEmpty()) {
      throw new NonVoidCollectionException();
    }

    for (IResourceStorage exchange : trade.getAcceptableExchanges()) {

      if (!buyerResources.canSubstract(exchange)) {
        clear();
        throw new NotEnoughtResourcesException();
      }

      exchangeToPlayersMap.put(exchange, new ArrayList<IPlayer>());
      tradeExchangesSet.add(exchange);
    }
  }
}
