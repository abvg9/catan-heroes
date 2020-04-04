package com.ucm.dasi.catan.game.trade;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.AgreementAlreadyProposedException;
import com.ucm.dasi.catan.game.exception.InvalidReferenceException;
import com.ucm.dasi.catan.game.exception.NoCurrentTradeException;
import com.ucm.dasi.catan.game.exception.NotAnAcceptableExchangeException;
import com.ucm.dasi.catan.game.exception.PendingTradeException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceStorage;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeManagerTest {

  @DisplayName("It adds an agreement")
  @Tag("TradeManager")
  @Test
  public void itAddsAnAgreement()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException,
          NotAnAcceptableExchangeException, InvalidReferenceException, NoCurrentTradeException,
          AgreementAlreadyProposedException {

    TradeManager manager = createStandardTradeManager();

    ITrade trade = manager.getTrade();
    IResourceStorage exchange = trade.getAcceptableExchanges().iterator().next();

    IPlayer player = new Player(1, new ResourceManager(exchange));

    ITradeAgreement agreement = new TradeAgreement(UUID.randomUUID(), exchange, trade);

    manager.addAgreement(player, agreement);

    assertTrue(manager.getAgreements().contains(agreement));
  }

  @DisplayName("It does not add an agreement if the player is null")
  @Tag("TradeManager")
  @Test
  public void itDoesNotAddAnAgreementI()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException,
          NotAnAcceptableExchangeException, InvalidReferenceException, NoCurrentTradeException,
          AgreementAlreadyProposedException {

    TradeManager manager = createStandardTradeManager();

    ITrade trade = manager.getTrade();
    IResourceStorage exchange = trade.getAcceptableExchanges().iterator().next();

    ITradeAgreement agreement = new TradeAgreement(UUID.randomUUID(), exchange, trade);

    assertThrows(NonNullInputException.class, () -> manager.addAgreement(null, agreement));
  }

  @DisplayName("It does not add an agreement if the agreement is null")
  @Tag("TradeManager")
  @Test
  public void itDoesNotAddAnAgreementII()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException,
          NotAnAcceptableExchangeException, InvalidReferenceException, NoCurrentTradeException,
          AgreementAlreadyProposedException {

    TradeManager manager = createStandardTradeManager();

    ITrade trade = manager.getTrade();
    IResourceStorage exchange = trade.getAcceptableExchanges().iterator().next();

    IPlayer player = new Player(1, new ResourceManager(exchange));

    assertThrows(NonNullInputException.class, () -> manager.addAgreement(player, null));
  }

  @DisplayName("It does not start a trade if a pending trade is found")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeI()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);
    IPlayer player = new Player(0, new ResourceManager(resourcesMap));
    TradeManager manager = new TradeManager();

    manager.start(player, trade);

    assertThrows(PendingTradeException.class, () -> manager.start(player, trade));
  }

  @DisplayName("It does not start a trade if a null player is provided")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeII() {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);
    TradeManager manager = new TradeManager();

    assertThrows(NonNullInputException.class, () -> manager.start(null, trade));
  }

  @DisplayName("It does not start a trade if a null trade is provided")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeIII() {

    IPlayer player = new Player(0, new ResourceManager());
    TradeManager manager = new TradeManager();

    assertThrows(NonNullInputException.class, () -> manager.start(player, null));
  }

  @DisplayName("It does not start a trade if the trade has no acceptable exchanges")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeIV()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Trade trade = new Trade(UUID.randomUUID(), null, requestedResources);
    IPlayer player = new Player(0, new ResourceManager(resourcesMap));
    TradeManager manager = new TradeManager();

    assertThrows(NonNullInputException.class, () -> manager.start(player, trade));
  }

  @DisplayName(
      "It does not start a trade if the trade has a void collection of acceptable exchanges")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeV()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Trade trade =
        new Trade(UUID.randomUUID(), new ArrayList<IResourceStorage>(), requestedResources);
    IPlayer player = new Player(0, new ResourceManager(resourcesMap));
    TradeManager manager = new TradeManager();

    assertThrows(NonVoidCollectionException.class, () -> manager.start(player, trade));
  }

  @DisplayName("It does not start a trade if the buyer has not enought resources")
  @Tag("TradeManager")
  @Test
  public void itDoesNotStartATradeVI()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);
    IPlayer player = new Player(0, new ResourceManager());
    TradeManager manager = new TradeManager();

    assertThrows(NotEnoughtResourcesException.class, () -> manager.start(player, trade));
  }

  @DisplayName("It starts a trade if no pending trades are found")
  @Tag("TradeManager")
  @Test
  public void itStartsATradeIfNoPendingTradesAreFound()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);
    IPlayer player = new Player(0, new ResourceManager(resourcesMap));
    TradeManager manager = new TradeManager();

    manager.start(player, trade);

    assertSame(player, manager.getBuyer());
    assertSame(trade, manager.getTrade());
  }

  private TradeManager createStandardTradeManager()
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException {

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);
    IPlayer player = new Player(0, new ResourceManager(resourcesMap));
    TradeManager manager = new TradeManager();

    manager.start(player, trade);

    return manager;
  }
}
