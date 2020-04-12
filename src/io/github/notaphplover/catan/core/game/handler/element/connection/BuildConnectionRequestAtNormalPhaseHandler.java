package io.github.notaphplover.catan.core.game.handler.element.connection;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BuildConnectionRequestAtNormalPhaseHandler extends BuildConnectionRequestHandler {

  public BuildConnectionRequestAtNormalPhaseHandler() {
    super(getBuilder());
  }

  private static BuildConnectionRequestHandlerBuilder getBuilder() {
    LinkedList<BiFunction<ICatanGameHearth, IBuildConnectionRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildConnectionRequest request) ->
            hearth
                .getBoard()
                .isConnectionConnected(request.getPlayer(), request.getX(), request.getY()));

    LinkedList<BiConsumer<ICatanGameHearth, IBuildConnectionRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IBuildConnectionRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));
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
        .setStateAllowed(GameState.NORMAL)
        .setSubstractResources(true)
        .setUpgradeHandler(false);
  }
}
