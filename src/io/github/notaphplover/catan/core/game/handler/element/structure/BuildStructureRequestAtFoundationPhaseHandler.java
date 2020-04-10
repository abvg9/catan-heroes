package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BuildStructureRequestAtFoundationPhaseHandler
    extends StructureRelatedRequestHandler<IBuildStructureRequest> {

  public BuildStructureRequestAtFoundationPhaseHandler() {
    super(getBuilder());
  }

  private static BuildStructureRequestHandlerBuilder getBuilder() {

    LinkedList<BiFunction<ICatanGameHearth, IBuildStructureRequest, Boolean>> preconditionsList =
        new LinkedList<>();

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildStructureRequest request) ->
            isTurnToBuildInitialStructure(hearth));

    preconditionsList.add(
        (ICatanGameHearth hearth, IBuildStructureRequest request) ->
            !hearth
                .getGameLog()
                .isRequestPerformedAt(
                    hearth.getPlayerManager().getTurnNumber(),
                    RequestType.BUILD_INITIAL_STRUCTURE));

    LinkedList<BiConsumer<ICatanGameHearth, IBuildStructureRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, IBuildStructureRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_FOUNDATION_REQUEST));
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
        .setStateAllowed(GameState.FOUNDATION)
        .setSubstractResources(false)
        .setUpgradeHandler(false);
  }

  private static boolean isTurnToBuildInitialStructure(ICatanGameHearth hearth) {
    return hearth.getState() == GameState.FOUNDATION
        && Math.floor(
                    hearth.getPlayerManager().getTurnNumber()
                        / hearth.getPlayerManager().getPlayers().length)
                % 2
            == 0;
  }
}
