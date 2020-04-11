package io.github.notaphplover.catan.core.game.handler;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Standard Request handler builder tests")
public class StandardRequestHandlerBuilderTest {

  @Nested
  @DisplayName("StandardRequestHandlerBuilder.constructor")
  class Constructor {

    @Test
    @DisplayName("It should store builder attributes")
    void itShouldStoreBuilderAttr() {

      boolean logRequestAfterAction = true;
      boolean rejectActivePlayer = true;
      boolean rejectIfTurnNotStarted = true;
      boolean rejectIfTurnStarted = true;
      boolean rejectUnactivePlayers = true;

      GameState state = GameState.FOUNDATION;

      MinimunStandardRequestHandlerBuilder builder =
          new MinimunStandardRequestHandlerBuilder()
              .setLogRequestAfterAction(logRequestAfterAction)
              .setRejectActivePlayer(rejectActivePlayer)
              .setRejectIfTurnNotStarted(rejectIfTurnNotStarted)
              .setRejectIfTurnStarted(rejectIfTurnStarted)
              .setRejectUnactivePlayers(rejectUnactivePlayers)
              .setStateAllowed(state);

      assertSame(logRequestAfterAction, builder.isLogRequestAfterAction());
      assertSame(rejectActivePlayer, builder.isRejectActivePlayer());
      assertSame(rejectIfTurnNotStarted, builder.isRejectIfTurnNotStarted());
      assertSame(rejectIfTurnStarted, builder.isRejectIfTurnStarted());
      assertSame(state, builder.getStateAllowed());
    }
  }
}
