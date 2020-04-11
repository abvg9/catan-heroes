package io.github.notaphplover.catan.core.command;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.trade.ITrade;
import io.github.notaphplover.catan.core.game.trade.Trade;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SendTradeAgreementRequestCommandTest {

  @DisplayName("It must get its trade")
  @Tag("SendTradeAgreementRequestCommand")
  @Test
  public void itMustGetItsTrade() {
    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 2);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 2);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    SendTradeAgreementRequestCommand command =
        new SendTradeAgreementRequestCommand(new Player(0, new ResourceManager()), trade);

    assertSame(trade, command.getTrade());
  }
}
