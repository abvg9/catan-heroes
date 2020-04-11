package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandler<R extends IRequest> implements IRequestHandler<R> {

  private LinkedList<BiConsumer<ICatanGameHearth, R>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, R>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, R, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, R, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, R> preconditionRejectedAction;

  public RequestHandler(RequestHandlerBuilder<R, ?> builder) {

    if (builder.getAfterFailureActions() == null) {
      this.afterFailureActions = new LinkedList<>();
    } else {
      this.afterFailureActions = new LinkedList<>(builder.getAfterFailureActions());
    }

    if (builder.getAfterSuccessActions() == null) {
      this.afterSuccessActions = new LinkedList<>();
    } else {
      this.afterSuccessActions = new LinkedList<>(builder.getAfterSuccessActions());
    }

    this.preconditionFullfilledAction = builder.getPreconditionFullfilledAction();

    if (builder.getPreconditionsList() == null) {
      this.preconditionsList = new LinkedList<>();
    } else {
      this.preconditionsList = new LinkedList<>(builder.getPreconditionsList());
    }

    this.preconditionRejectedAction = builder.getPreconditionRejectedAction();
  }

  @Override
  public void handle(ICatanGameHearth hearth, R request) {

    boolean preconditionsFullfilled = true;

    for (BiFunction<ICatanGameHearth, R, Boolean> precondition : preconditionsList) {
      if (!precondition.apply(hearth, request)) {
        preconditionsFullfilled = false;
        break;
      }
    }

    if (preconditionsFullfilled) {
      preconditionsFullfilled = preconditionFullfilledAction.apply(hearth, request);
    } else {
      preconditionRejectedAction.accept(hearth, request);
    }

    List<BiConsumer<ICatanGameHearth, R>> actionList =
        preconditionsFullfilled ? afterSuccessActions : afterFailureActions;

    for (BiConsumer<ICatanGameHearth, R> action : actionList) {
      action.accept(hearth, request);
    }
  }
}
