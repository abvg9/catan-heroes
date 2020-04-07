package io.github.notaphplover.catan.core.game.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeAgreementTest {

  @DisplayName("It must get its exchange")
  @Tag("TradeAgreement")
  @Test
  public void itMustGetItsExchange() {

    Map<ResourceType, Integer> exchangeResourcesMap = new TreeMap<ResourceType, Integer>();
    exchangeResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage exchange = new ResourceStorage(exchangeResourcesMap);

    TradeAgreement agreement =
        new TradeAgreement(UUID.randomUUID(), exchange, new Reference(UUID.randomUUID()));

    assertEquals(exchange, agreement.getExchange());
  }

  @DisplayName("It must get its trade")
  @Tag("TradeAgreement")
  @Test
  public void itMustGetItsTrade() {

    Map<ResourceType, Integer> exchangeResourcesMap = new TreeMap<ResourceType, Integer>();
    exchangeResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage exchange = new ResourceStorage(exchangeResourcesMap);
    IReference trade = new Reference(UUID.randomUUID());

    TradeAgreement agreement = new TradeAgreement(UUID.randomUUID(), exchange, trade);

    assertEquals(trade, agreement.getTrade());
  }
}
