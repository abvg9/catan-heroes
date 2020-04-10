package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandlerBuilder<
    TRequest extends IRequest, TReturn extends RequestHandlerBuilder<TRequest, TReturn>> {

  private LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, TRequest, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, TRequest, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, TRequest> preconditionRejectedAction;

  public LinkedList<BiConsumer<ICatanGameHearth, TRequest>> getAfterFailureActions() {
    return afterFailureActions;
  }

  public LinkedList<BiConsumer<ICatanGameHearth, TRequest>> getAfterSuccessActions() {
    return afterSuccessActions;
  }

  public BiFunction<ICatanGameHearth, TRequest, Boolean> getPreconditionFullfilledAction() {
    return preconditionFullfilledAction;
  }

  public LinkedList<BiFunction<ICatanGameHearth, TRequest, Boolean>> getPreconditionsList() {
    return preconditionsList;
  }

  public BiConsumer<ICatanGameHearth, TRequest> getPreconditionRejectedAction() {
    return preconditionRejectedAction;
  }

  public TReturn setAfterFailureActions(
      LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterFailureActions) {
    this.afterFailureActions = afterFailureActions;

    return getSelf();
  }

  public TReturn setAfterSuccessActions(
      LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterSuccessActions) {
    this.afterSuccessActions = afterSuccessActions;

    return getSelf();
  }

  public TReturn setPreconditionFullfilledAction(
      BiFunction<ICatanGameHearth, TRequest, Boolean> action) {
    this.preconditionFullfilledAction = action;

    return getSelf();
  }

  public TReturn setPreconditionsList(
      LinkedList<BiFunction<ICatanGameHearth, TRequest, Boolean>> preconditionsList) {
    this.preconditionsList = preconditionsList;

    return getSelf();
  }

  public TReturn setPreconditionRejectedAction(BiConsumer<ICatanGameHearth, TRequest> action) {
    this.preconditionRejectedAction = action;

    return getSelf();
  }

  protected TReturn getSelf() {
    @SuppressWarnings("unchecked")
    TReturn self = (TReturn) this;

    return self;
  }
}
