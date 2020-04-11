package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BuildStructureRequestAtNormalPhaseHandler
    extends StructureRelatedRequestHandler<IBuildStructureRequest> {

  public BuildStructureRequestAtNormalPhaseHandler() {
    super(getBuilder());
  }

  private static BuildStructureRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IBuildStructureRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildStructureRequest request) ->
            hearth
                .getBoard()
                .isStructurePointConnected(request.getPlayer(), request.getX(), request.getY()));

    LinkedList<BiConsumer<ICatanGameHearth, IBuildStructureRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IBuildStructureRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));
        });

    return new BuildStructureRequestHandlerBuilder()
        .setAfterFailureActions(new LinkedList<>())
        .setAfterSuccessActions(afterSuccessActions)
        .setLogRequestAfterAction(true)
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
