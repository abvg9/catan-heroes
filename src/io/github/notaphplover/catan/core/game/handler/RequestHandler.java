package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class RequestHandler<Req extends IRequest> implements IRequestHandler<Req> {

  private LinkedList<BiConsumer<ICatanGameHearth, Req>> afterFailureActions;

  private LinkedList<BiConsumer<ICatanGameHearth, Req>> afterSuccessActions;

  private BiFunction<ICatanGameHearth, Req, Boolean> preconditionFullfilledAction;

  private LinkedList<BiFunction<ICatanGameHearth, Req, Boolean>> preconditionsList;

  private BiConsumer<ICatanGameHearth, Req> preconditionRejectedAction;

  public RequestHandler(RequestHandlerBuilder<Req, ?> builder) {

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
  public void handle(ICatanGameHearth hearth, Req request) {

    boolean preconditionsFullfilled = true;

    for (BiFunction<ICatanGameHearth, Req, Boolean> precondition : preconditionsList) {
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

    List<BiConsumer<ICatanGameHearth, Req>> actionList =
        preconditionsFullfilled ? afterSuccessActions : afterFailureActions;

    for (BiConsumer<ICatanGameHearth, Req> action : actionList) {
      action.accept(hearth, request);
    }
  }
}
