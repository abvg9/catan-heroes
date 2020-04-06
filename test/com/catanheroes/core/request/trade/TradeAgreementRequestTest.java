package com.catanheroes.core.request.trade;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.game.trade.Reference;
import com.catanheroes.core.game.trade.TradeAgreement;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeAgreementRequestTest {

  @DisplayName("It must get its trade")
  @Tag("TradeAgreement")
  @Test
  public void itMustGetItsAgreement() {

    TradeAgreement agreement =
        new TradeAgreement(
            UUID.randomUUID(), new ResourceManager(), new Reference(UUID.randomUUID()));
    TradeAgreementRequest request =
        new TradeAgreementRequest(new Player(0, new ResourceManager()), agreement);

    assertSame(agreement, request.getTradeAgreement());
  }
}
