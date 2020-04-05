package com.ucm.dasi.catan.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.game.trade.Trade;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceStorage;
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
