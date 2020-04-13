package io.github.notaphplover.catan.core.game.handler.turn;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.game.log.LogEntry;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class StartTurnRequestHandler extends StandardRequestHandler<IStartTurnRequest> {

  public StartTurnRequestHandler() {
    super(getBuilder());
  }

  public static StartTurnRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IStartTurnRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IStartTurnRequest request) ->
            hearth.getState() != GameState.ENDED);

    LinkedList<BiConsumer<ICatanGameHearth, IStartTurnRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IStartTurnRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));

          request.getPlayer().emptyMissing();
        });

    StartTurnRequestHandlerBuilder builder =
        new StartTurnRequestHandlerBuilder()
            .setAfterFailureActions(new LinkedList<>())
            .setAfterSuccessActions(afterSuccessActions)
            .setLogRequestAfterAction(true)
            .setPreconditionRejectedAction(null)
            .setPreconditionsList(preconditionsList)
            .setRejectActivePlayer(false)
            .setRejectIfTurnNotStarted(false)
            .setRejectIfTurnStarted(true)
            .setRejectUnactivePlayers(true)
            .setStateAllowed(null);

    BiFunction<ICatanGameHearth, IStartTurnRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, IStartTurnRequest request) -> {
          hearth.getPlayerManager().switchTurnStarted();
          int productionNumber = hearth.getNumberGenerator().getNextProductionNumber();

          if (hearth.getState() == GameState.NORMAL) {
            IResourceProduction production = hearth.getBoard().getProduction(productionNumber);

            for (IPlayer player : hearth.getPlayerManager().getPlayers()) {
              player.getResourceManager().add(production.getProduction(player));
            }
          }

          ArrayList<IRequest> requestList = new ArrayList<IRequest>();

          hearth
              .getGameLog()
              .set(
                  hearth.getPlayerManager().getTurnNumber(),
                  new LogEntry(productionNumber, requestList));

          return true;
        };

    builder.setPreconditionFullfilledAction(preconditionsFullfilledAction);

    return builder;
  }
}
