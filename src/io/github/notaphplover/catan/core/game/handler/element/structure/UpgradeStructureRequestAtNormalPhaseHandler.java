package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IUpgradeStructureRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class UpgradeStructureRequestAtNormalPhaseHandler
    extends StructureRelatedRequestHandler<IUpgradeStructureRequest> {

  public UpgradeStructureRequestAtNormalPhaseHandler() {
    super(getBuilder());
  }

  private static UpgradeStructureRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IUpgradeStructureRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IUpgradeStructureRequest request) ->
            hearth
                .getBoard()
                .isStructurePointConnected(request.getPlayer(), request.getX(), request.getY()));

    LinkedList<BiConsumer<ICatanGameHearth, IUpgradeStructureRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IUpgradeStructureRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));
        });

    return new UpgradeStructureRequestHandlerBuilder()
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
        .setUpgradeHandler(true);
  }
}
