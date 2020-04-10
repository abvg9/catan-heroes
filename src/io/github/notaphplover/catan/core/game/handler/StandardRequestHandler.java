package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class StandardRequestHandler<TRequest extends IRequest>
    extends RequestHandler<TRequest> {

  public StandardRequestHandler(StandardRequestHandlerBuilder<TRequest, ?> builder) {
    super(processBuilder(builder));
  }

  private static <TRequest extends IRequest>
      BiConsumer<ICatanGameHearth, TRequest> buildLogAction() {
    return (ICatanGameHearth hearth, TRequest request) -> {
      hearth.getGameLog().get(hearth.getPlayerManager().getTurnNumber()).add(request);
    };
  }

  private static <TRequest extends IRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> buildOnlyActivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, TRequest request) -> {
      return hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <TRequest extends IRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> buildOnlyAtPhaseAllowedPrecondition(
          final GameState state) {

    return (ICatanGameHearth hearth, TRequest request) -> {
      return hearth.getState() == state;
    };
  }

  private static <TRequest extends IRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> buildOnlyIfTurnNotStartedPrecondition() {
    return (ICatanGameHearth hearth, TRequest request) -> {
      return !hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <TRequest extends IRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> buildOnlyIfTurnStartedPrecondition() {
    return (ICatanGameHearth hearth, TRequest request) -> {
      return hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <TRequest extends IRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> buildOnlyUnactivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, TRequest request) -> {
      return !hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilder(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    builder = processBuilderPreconditions(builder);
    builder = processBuilderPreconditionRejectedAction(builder);
    builder = processBuilderAfterFailureActions(builder);
    builder = processBuilderAfterSuccessActions(builder);

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderAfterFailureActions(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, TRequest>> failureActions =
        builder.getAfterFailureActions();

    if (failureActions == null) {
      failureActions = new LinkedList<>();
    }

    builder.setAfterFailureActions(failureActions);

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderAfterSuccessActions(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, TRequest>> successActions =
        builder.getAfterSuccessActions();

    if (successActions == null) {
      successActions = new LinkedList<>();
    }

    if (builder.isLogRequestAfterAction()) {
      successActions.addFirst(buildLogAction());
    }

    builder.setAfterSuccessActions(successActions);

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderAllowedPhase(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    if (builder.getStateAllowed() != null) {
      builder
          .getPreconditionsList()
          .addFirst(buildOnlyAtPhaseAllowedPrecondition(builder.getStateAllowed()));
    }

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderAllowedPlayers(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {
    if (builder.isRejectActivePlayer()) {
      builder.getPreconditionsList().addFirst(buildOnlyUnactivePlayerAllowedPrecondition());
    }

    if (builder.isRejectUnactivePlayers()) {
      builder.getPreconditionsList().addFirst(buildOnlyActivePlayerAllowedPrecondition());
    }

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderAllowedTurnStarted(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {
    if (builder.isRejectIfTurnNotStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnStartedPrecondition());
    }

    if (builder.isRejectIfTurnStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnNotStartedPrecondition());
    }

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderPreconditionRejectedAction(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    builder.setPreconditionRejectedAction(
        (ICatanGameHearth hearth, TRequest request) -> {
          hearth.getErrorHandler().accept(request);
        });

    return builder;
  }

  private static <TRequest extends IRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilderPreconditions(
          StandardRequestHandlerBuilder<TRequest, ?> builder) {

    builder = processBuilderAllowedTurnStarted(builder);
    builder = processBuilderAllowedPlayers(builder);
    builder = processBuilderAllowedPhase(builder);

    return builder;
  }
}
