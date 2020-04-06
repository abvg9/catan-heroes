package com.catanheroes.core.game.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.catanheroes.core.resource.IResourceStorage;
import com.catanheroes.core.resource.ResourceStorage;
import com.catanheroes.core.resource.ResourceType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeTest {

  @DisplayName("It must get its acceptable trades")
  @Tag("Trade")
  @Test
  public void itMustGetItsAcceptableExchanges() {

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    assertEquals(acceptableExchanges, trade.getAcceptableExchanges());
  }

  @DisplayName("It must get its requested resources")
  @Tag("Trade")
  @Test
  public void itMustGetItsRequestedResources() {

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    assertEquals(requestedResources, trade.getRequestedResources());
  }
}
