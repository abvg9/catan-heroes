package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class StandardRequestHandler<Req extends IRequest> extends RequestHandler<Req> {

  public StandardRequestHandler(StandardRequestHandlerBuilder<Req, ?> builder) {
    super(processBuilder(builder));
  }

  private static <Req extends IRequest> BiConsumer<ICatanGameHearth, Req> buildLogAction() {
    return (ICatanGameHearth hearth, Req request) -> {
      hearth.getGameLog().get(hearth.getPlayerManager().getTurnNumber()).add(request);
    };
  }

  private static <Req extends IRequest>
      BiFunction<ICatanGameHearth, Req, Boolean> buildOnlyActivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, Req request) -> {
      return hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <Req extends IRequest>
      BiFunction<ICatanGameHearth, Req, Boolean> buildOnlyAtPhaseAllowedPrecondition(
          final GameState state) {

    return (ICatanGameHearth hearth, Req request) -> {
      return hearth.getState() == state;
    };
  }

  private static <Req extends IRequest>
      BiFunction<ICatanGameHearth, Req, Boolean> buildOnlyIfTurnNotStartedPrecondition() {
    return (ICatanGameHearth hearth, Req request) -> {
      return !hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <Req extends IRequest>
      BiFunction<ICatanGameHearth, Req, Boolean> buildOnlyIfTurnStartedPrecondition() {
    return (ICatanGameHearth hearth, Req request) -> {
      return hearth.getPlayerManager().isTurnStarted();
    };
  }

  private static <Req extends IRequest>
      BiFunction<ICatanGameHearth, Req, Boolean> buildOnlyUnactivePlayerAllowedPrecondition() {
    return (ICatanGameHearth hearth, Req request) -> {
      return !hearth.getPlayerManager().getActivePlayer().equals(request.getPlayer());
    };
  }

  private static <Req extends IRequest> StandardRequestHandlerBuilder<Req, ?> processBuilder(
      StandardRequestHandlerBuilder<Req, ?> builder) {

    StandardRequestHandlerBuilder<Req, ?> processedBuilder = builder;

    processedBuilder = processBuilderPreconditions(processedBuilder);
    processedBuilder = processBuilderPreconditionRejectedAction(processedBuilder);
    processedBuilder = processBuilderAfterFailureActions(processedBuilder);
    processedBuilder = processBuilderAfterSuccessActions(processedBuilder);

    return processedBuilder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderAfterFailureActions(
          StandardRequestHandlerBuilder<Req, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, Req>> failureActions = builder.getAfterFailureActions();

    if (failureActions == null) {
      failureActions = new LinkedList<>();
    }

    builder.setAfterFailureActions(failureActions);

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderAfterSuccessActions(
          StandardRequestHandlerBuilder<Req, ?> builder) {

    LinkedList<BiConsumer<ICatanGameHearth, Req>> successActions = builder.getAfterSuccessActions();

    if (successActions == null) {
      successActions = new LinkedList<>();
    }

    if (builder.isLogRequestAfterAction()) {
      successActions.addFirst(buildLogAction());
    }

    builder.setAfterSuccessActions(successActions);

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderAllowedPhase(
          StandardRequestHandlerBuilder<Req, ?> builder) {

    if (builder.getStateAllowed() != null) {
      builder
          .getPreconditionsList()
          .addFirst(buildOnlyAtPhaseAllowedPrecondition(builder.getStateAllowed()));
    }

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderAllowedPlayers(
          StandardRequestHandlerBuilder<Req, ?> builder) {
    if (builder.isRejectActivePlayer()) {
      builder.getPreconditionsList().addFirst(buildOnlyUnactivePlayerAllowedPrecondition());
    }

    if (builder.isRejectUnactivePlayers()) {
      builder.getPreconditionsList().addFirst(buildOnlyActivePlayerAllowedPrecondition());
    }

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderAllowedTurnStarted(
          StandardRequestHandlerBuilder<Req, ?> builder) {
    if (builder.isRejectIfTurnNotStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnStartedPrecondition());
    }

    if (builder.isRejectIfTurnStarted()) {
      builder.getPreconditionsList().addFirst(buildOnlyIfTurnNotStartedPrecondition());
    }

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderPreconditionRejectedAction(
          StandardRequestHandlerBuilder<Req, ?> builder) {

    builder.setPreconditionRejectedAction(
        (ICatanGameHearth hearth, Req request) -> {
          hearth.getErrorHandler().accept(request);
        });

    return builder;
  }

  private static <Req extends IRequest>
      StandardRequestHandlerBuilder<Req, ?> processBuilderPreconditions(
          StandardRequestHandlerBuilder<Req, ?> builder) {

    StandardRequestHandlerBuilder<Req, ?> processedBuilder = builder;

    processedBuilder = processBuilderAllowedTurnStarted(processedBuilder);
    processedBuilder = processBuilderAllowedPlayers(processedBuilder);
    processedBuilder = processBuilderAllowedPhase(processedBuilder);

    return processedBuilder;
  }
}
