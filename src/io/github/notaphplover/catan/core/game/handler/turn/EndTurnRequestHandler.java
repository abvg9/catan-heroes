package io.github.notaphplover.catan.core.game.handler.turn;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class EndTurnRequestHandler extends StandardRequestHandler<IEndTurnRequest> {

  public EndTurnRequestHandler() {
    super(getBuilder());
  }

  public static EndTurnRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IEndTurnRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IEndTurnRequest request) -> hearth.getState() != GameState.ENDED);

    preconditionsList.add(
        (ICatanGameHearth hearth, IEndTurnRequest request) ->
            hearth.getTradeManager().getTrade() == null);

    LinkedList<BiConsumer<ICatanGameHearth, IEndTurnRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IEndTurnRequest request) -> {
          hearth.getCommandSender().send(new Command(request.getPlayer(), CommandType.START_TURN));
        });

    EndTurnRequestHandlerBuilder builder =
        new EndTurnRequestHandlerBuilder()
            .setAfterFailureActions(new LinkedList<>())
            .setAfterSuccessActions(afterSuccessActions)
            .setLogRequestAfterAction(false)
            .setPreconditionRejectedAction(null)
            .setPreconditionsList(preconditionsList)
            .setRejectActivePlayer(false)
            .setRejectIfTurnNotStarted(true)
            .setRejectIfTurnStarted(false)
            .setRejectUnactivePlayers(true)
            .setStateAllowed(null);

    BiFunction<ICatanGameHearth, IEndTurnRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, IEndTurnRequest request) -> {
          hearth.getPlayerManager().switchTurnStarted();

          hearth.getGameLog().get(hearth.getPlayerManager().getTurnNumber()).add(request);

          switchStateIfNeeded(hearth);

          if (hearth.getState() != GameState.ENDED) {
            hearth.getPlayerManager().passTurn();
          }

          return true;
        };

    builder.setPreconditionFullfilledAction(preconditionsFullfilledAction);

    return builder;
  }

  private static boolean hasActivePlayerWon(ICatanGameHearth hearth) {
    return hearth.getPointsCalculator().getPoints().get(hearth.getPlayerManager().getActivePlayer())
        >= hearth.getPointsToWin();
  }

  private static boolean isLastFoundationPhaseTurn(ICatanGameHearth hearth) {
    return hearth.getPlayerManager().getTurnNumber()
        == 4 * hearth.getPlayerManager().getPlayers().length - 1;
  }

  private static void switchStateIfNeeded(ICatanGameHearth hearth) {
    switch (hearth.getState()) {
      case ENDED:
        return;
      case FOUNDATION:
        if (isLastFoundationPhaseTurn(hearth)) {
          hearth.setState(GameState.NORMAL);
        }
        return;
      case NORMAL:
        if (hasActivePlayerWon(hearth)) {
          hearth.setState(GameState.ENDED);
        }
        return;
    }
  }
}
