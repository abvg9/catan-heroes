package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandler<TRequest extends IRequest>
    implements IRequestHandler<TRequest> {

  private LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, TRequest>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, TRequest, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, TRequest, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, TRequest> preconditionRejectedAction;

  public RequestHandler(RequestHandlerBuilder<TRequest, ?> builder) {

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
  public void handle(ICatanGameHearth hearth, TRequest request) {

    boolean preconditionsFullfilled = true;

    for (BiFunction<ICatanGameHearth, TRequest, Boolean> precondition : preconditionsList) {
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

    List<BiConsumer<ICatanGameHearth, TRequest>> actionList =
        preconditionsFullfilled ? afterSuccessActions : afterFailureActions;

    for (BiConsumer<ICatanGameHearth, TRequest> action : actionList) {
      action.accept(hearth, request);
    }
  }
}
