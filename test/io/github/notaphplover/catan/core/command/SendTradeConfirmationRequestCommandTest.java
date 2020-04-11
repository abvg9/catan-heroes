package io.github.notaphplover.catan.core.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.game.trade.Reference;
import io.github.notaphplover.catan.core.game.trade.TradeAgreement;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SendTradeConfirmationRequestCommandTest {

  @DisplayName("It must get its agreements")
  @Tag("SendTradeConfirmationRequestCommand")
  @Test
  public void itMustGetItsAgreements() {

    Collection<ITradeAgreement> agreements = new ArrayList<>();
    agreements.add(
        new TradeAgreement(
            UUID.randomUUID(), new ResourceManager(), new Reference(UUID.randomUUID())));

    SendTradeConfirmationRequestCommand command =
        new SendTradeConfirmationRequestCommand(new Player(0, new ResourceManager()), agreements);

    assertEquals(agreements, command.getAgreements());
  }
}
