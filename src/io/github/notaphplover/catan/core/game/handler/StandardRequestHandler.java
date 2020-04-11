package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class StandardRequestHandler<R extends IRequest> extends RequestHandler<R> {

  public StandardRequestHandler(StandardRequestHandlerBuilder<R, ?> builder) {
    super(processBuilder(builder));
  }

  private static <R extends IRequest> BiConsumer<ICatanGameHearth, R> buildLogAction() {
    return (ICatanGameHearth hearth, R request) -> {
      hearth.getGameLog().get(hearth.getPlayerManager().getTurnNumber()).add(request);
    };
  }

  private static <R extends IRequest>
      BiFunction<ICatanGameHearth, R, Boolean> buildOnlyActivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, R request) -> {
      return hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <R extends IRequest>
      BiFunction<ICatanGameHearth, R, Boolean> buildOnlyAtPhaseAllowedPrecondition(
          final GameState state) {

    return (ICatanGameHearth hearth, R request) -> {
      return hearth.getState() == state;
    };
  }

  private static <R extends IRequest>
      BiFunction<ICatanGameHearth, R, Boolean> buildOnlyIfTurnNotStartedPrecondition() {
    return (ICatanGameHearth hearth, R request) -> {
      return !hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <R extends IRequest>
      BiFunction<ICatanGameHearth, R, Boolean> buildOnlyIfTurnStartedPrecondition() {
    return (ICatanGameHearth hearth, R request) -> {
      return hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <R extends IRequest>
      BiFunction<ICatanGameHearth, R, Boolean> buildOnlyUnactivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, R request) -> {
      return !hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <R extends IRequest> StandardRequestHandlerBuilder<R, ?> processBuilder(
      StandardRequestHandlerBuilder<R, ?> builder) {

    StandardRequestHandlerBuilder<R, ?> processedBuilder = builder;

    processedBuilder = processBuilderPreconditions(processedBuilder);
    processedBuilder = processBuilderPreconditionRejectedAction(processedBuilder);
    processedBuilder = processBuilderAfterFailureActions(processedBuilder);
    processedBuilder = processBuilderAfterSuccessActions(processedBuilder);

    return processedBuilder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderAfterFailureActions(
          StandardRequestHandlerBuilder<R, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, R>> failureActions = builder.getAfterFailureActions();

    if (failureActions == null) {
      failureActions = new LinkedList<>();
    }

    builder.setAfterFailureActions(failureActions);

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderAfterSuccessActions(
          StandardRequestHandlerBuilder<R, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, R>> successActions = builder.getAfterSuccessActions();

    if (successActions == null) {
      successActions = new LinkedList<>();
    }

    if (builder.isLogRequestAfterAction()) {
      successActions.addFirst(buildLogAction());
    }

    builder.setAfterSuccessActions(successActions);

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderAllowedPhase(
          StandardRequestHandlerBuilder<R, ?> builder) {

    if (builder.getStateAllowed() != null) {
      builder
          .getPreconditionsList()
          .addFirst(buildOnlyAtPhaseAllowedPrecondition(builder.getStateAllowed()));
    }

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderAllowedPlayers(
          StandardRequestHandlerBuilder<R, ?> builder) {
    if (builder.isRejectActivePlayer()) {
      builder.getPreconditionsList().addFirst(buildOnlyUnactivePlayerAllowedPrecondition());
    }

    if (builder.isRejectUnactivePlayers()) {
      builder.getPreconditionsList().addFirst(buildOnlyActivePlayerAllowedPrecondition());
    }

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderAllowedTurnStarted(
          StandardRequestHandlerBuilder<R, ?> builder) {
    if (builder.isRejectIfTurnNotStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnStartedPrecondition());
    }

    if (builder.isRejectIfTurnStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnNotStartedPrecondition());
    }

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderPreconditionRejectedAction(
          StandardRequestHandlerBuilder<R, ?> builder) {

    builder.setPreconditionRejectedAction(
        (ICatanGameHearth hearth, R request) -> {
          hearth.getErrorHandler().accept(request);
        });

    return builder;
  }

  private static <R extends IRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilderPreconditions(
          StandardRequestHandlerBuilder<R, ?> builder) {

    StandardRequestHandlerBuilder<R, ?> processedBuilder = builder;

    processedBuilder = processBuilderAllowedTurnStarted(processedBuilder);
    processedBuilder = processBuilderAllowedPlayers(processedBuilder);
    processedBuilder = processBuilderAllowedPhase(processedBuilder);

    return processedBuilder;
  }
}
