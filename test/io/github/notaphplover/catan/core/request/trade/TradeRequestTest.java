package io.github.notaphplover.catan.core.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.trade.Trade;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeRequestTest {

  @DisplayName("It must get its trade")
  @Tag("TradeRequest")
  @Test
  public void itMustGetItsTrade() {

    Trade trade =
        new Trade(UUID.randomUUID(), new ArrayList<IResourceStorage>(), new ResourceStorage());

    TradeRequest request = new TradeRequest(new Player(1, new ResourceManager()), trade);

    assertSame(trade, request.getTrade());
  }
}
