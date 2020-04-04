package com.ucm.dasi.catan.game.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceStorage;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeManagerTest {

  @DisplayName("It starts a trade if no pending trade is found")
  @Tag("TradeManager")
  @Test
  public void itStartsATradeIfNoPendingTradeIsFound()
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
}