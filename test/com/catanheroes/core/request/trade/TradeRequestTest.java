package com.catanheroes.core.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.game.trade.Trade;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.IResourceStorage;
import com.catanheroes.core.resource.ResourceManager;
import com.catanheroes.core.resource.ResourceStorage;
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
