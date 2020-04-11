package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandlerBuilder<
    Req extends IRequest, Self extends RequestHandlerBuilder<Req, Self>> {

  private LinkedList<BiConsumer<ICatanGameHearth, Req>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, Req>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, Req, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, Req, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, Req> preconditionRejectedAction;

  public LinkedList<BiConsumer<ICatanGameHearth, Req>> getAfterFailureActions() {
    return afterFailureActions;
  }

  public LinkedList<BiConsumer<ICatanGameHearth, Req>> getAfterSuccessActions() {
    return afterSuccessActions;
  }

  public BiFunction<ICatanGameHearth, Req, Boolean> getPreconditionFullfilledAction() {
    return preconditionFullfilledAction;
  }

  public LinkedList<BiFunction<ICatanGameHearth, Req, Boolean>> getPreconditionsList() {
    return preconditionsList;
  }

  public BiConsumer<ICatanGameHearth, Req> getPreconditionRejectedAction() {
    return preconditionRejectedAction;
  }

  public Self setAfterFailureActions(
      LinkedList<BiConsumer<ICatanGameHearth, Req>> afterFailureActions) {
    this.afterFailureActions = afterFailureActions;

    return getSelf();
  }

  public Self setAfterSuccessActions(
      LinkedList<BiConsumer<ICatanGameHearth, Req>> afterSuccessActions) {
    this.afterSuccessActions = afterSuccessActions;

    return getSelf();
  }

  public Self setPreconditionFullfilledAction(BiFunction<ICatanGameHearth, Req, Boolean> action) {
    this.preconditionFullfilledAction = action;

    return getSelf();
  }

  public Self setPreconditionsList(
      LinkedList<BiFunction<ICatanGameHearth, Req, Boolean>> preconditionsList) {
    this.preconditionsList = preconditionsList;

    return getSelf();
  }

  public Self setPreconditionRejectedAction(BiConsumer<ICatanGameHearth, Req> action) {
    this.preconditionRejectedAction = action;

    return getSelf();
  }

  protected Self getSelf() {
    @SuppressWarnings("unchecked")
    Self self = (Self) this;

    return self;
  }
}
