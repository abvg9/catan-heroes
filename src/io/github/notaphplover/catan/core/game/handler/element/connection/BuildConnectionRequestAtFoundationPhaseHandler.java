package io.github.notaphplover.catan.core.game.handler.element.connection;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BuildConnectionRequestAtFoundationPhaseHandler extends BuildConnectionRequestHandler {

  public BuildConnectionRequestAtFoundationPhaseHandler() {
    super(getBuilder());
  }

  private static BuildConnectionRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IBuildConnectionRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildConnectionRequest request) ->
            isTurnToBuildInitialConnection(hearth));

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildConnectionRequest request) ->
            !hearth
                .getGameLog()
                .isRequestPerformedAt(
                    hearth.getPlayerManager().getTurnNumber(),
                    RequestType.BUILD_INITIAL_CONNECTION));

    LinkedList<BiConsumer<ICatanGameHearth, IBuildConnectionRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IBuildConnectionRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_FOUNDATION_REQUEST));
        });

    return new BuildConnectionRequestHandlerBuilder()
        .setAfterFailureActions(new LinkedList<>())
        .setAfterSuccessActions(afterSuccessActions)
        .setLogRequestAfterAction(true)
        .setNotifyToPlayers(true)
        .setPreconditionFullfilledAction(null)
        .setPreconditionRejectedAction(null)
        .setPreconditionsList(preconditionsList)
        .setRejectActivePlayer(false)
        .setRejectIfTurnNotStarted(true)
        .setRejectIfTurnStarted(false)
        .setRejectUnactivePlayers(true)
        .setStateAllowed(GameState.FOUNDATION)
        .setSubstractResources(false)
        .setUpgradeHandler(false);
  }

  private static boolean isTurnToBuildInitialConnection(ICatanGameHearth hearth) {
    return hearth.getState() == GameState.FOUNDATION
        && Math.floor(
                    hearth.getPlayerManager().getTurnNumber()
                        / hearth.getPlayerManager().getPlayers().length)
                % 2
            == 1;
  }
}
