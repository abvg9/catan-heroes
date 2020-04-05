package com.ucm.dasi.catan.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.game.trade.ITradeConfirmation;
import com.ucm.dasi.catan.game.trade.Reference;
import com.ucm.dasi.catan.game.trade.TradeConfirmation;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeConfirmationRequestTest {

  @DisplayName("It must get its confirmation")
  @Tag("TradeConfirmation")
  @Test
  public void itMustGetItsConfirmation() {

    ITradeConfirmation confirmation =
        new TradeConfirmation(UUID.randomUUID(), new Reference(UUID.randomUUID()));
    TradeConfirmationRequest request =
        new TradeConfirmationRequest(new Player(0, new ResourceManager()), confirmation);

    assertSame(confirmation, request.getConfirmation());
  }
}
