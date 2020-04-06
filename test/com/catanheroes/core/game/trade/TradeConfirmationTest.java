package com.catanheroes.core.game.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TradeConfirmationTest {

  @DisplayName("It must get its agreement")
  @Tag("TradeConfirmation")
  @Test
  public void itMustGetItsAgreement() {

    IReference agreement = new Reference(UUID.randomUUID());
    TradeConfirmation confirmation = new TradeConfirmation(UUID.randomUUID(), agreement);

    assertEquals(agreement.getId(), confirmation.getAgreement().getId());
  }
}
