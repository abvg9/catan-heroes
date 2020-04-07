package io.github.notaphplover.catan.core.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.trade.ITradeConfirmation;
import io.github.notaphplover.catan.core.game.trade.Reference;
import io.github.notaphplover.catan.core.game.trade.TradeConfirmation;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
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
