package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandlerBuilder<
    R extends IRequest, S extends RequestHandlerBuilder<R, S>> {

  private LinkedList<BiConsumer<ICatanGameHearth, R>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, R>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, R, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, R, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, R> preconditionRejectedAction;

  public LinkedList<BiConsumer<ICatanGameHearth, R>> getAfterFailureActions() {
    return afterFailureActions;
  }

  public LinkedList<BiConsumer<ICatanGameHearth, R>> getAfterSuccessActions() {
    return afterSuccessActions;
  }

  public BiFunction<ICatanGameHearth, R, Boolean> getPreconditionFullfilledAction() {
    return preconditionFullfilledAction;
  }

  public LinkedList<BiFunction<ICatanGameHearth, R, Boolean>> getPreconditionsList() {
    return preconditionsList;
  }

  public BiConsumer<ICatanGameHearth, R> getPreconditionRejectedAction() {
    return preconditionRejectedAction;
  }

  public S setAfterFailureActions(LinkedList<BiConsumer<ICatanGameHearth, R>> afterFailureActions) {
    this.afterFailureActions = afterFailureActions;

    return getSelf();
  }

  public S setAfterSuccessActions(LinkedList<BiConsumer<ICatanGameHearth, R>> afterSuccessActions) {
    this.afterSuccessActions = afterSuccessActions;

    return getSelf();
  }

  public S setPreconditionFullfilledAction(BiFunction<ICatanGameHearth, R, Boolean> action) {
    this.preconditionFullfilledAction = action;

    return getSelf();
  }

  public S setPreconditionsList(
      LinkedList<BiFunction<ICatanGameHearth, R, Boolean>> preconditionsList) {
    this.preconditionsList = preconditionsList;

    return getSelf();
  }

  public S setPreconditionRejectedAction(BiConsumer<ICatanGameHearth, R> action) {
    this.preconditionRejectedAction = action;

    return getSelf();
  }

  protected S getSelf() {
    @SuppressWarnings("unchecked")
    S self = (S) this;

    return self;
  }
}
