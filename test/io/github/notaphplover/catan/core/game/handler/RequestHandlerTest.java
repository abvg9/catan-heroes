package io.github.notaphplover.catan.core.game.handler;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Request handler tests")
public class RequestHandlerTest {

  @Nested
  @DisplayName("Tests for the handle method")
  class Handle {

    @Test
    @DisplayName("It should call the after failure actions if any precondition is not fullfilled")
    public void itShouldCallTheAfterFailureActionI() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      afterFailureActions.add(
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);
          });

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> true;
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {};

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();
      preconditionList.add((ICatanGameHearth hearth, IRequest request) -> true);
      preconditionList.add((ICatanGameHearth hearth, IRequest request) -> false);

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }

    @Test
    @DisplayName("It should call the after failure actions if action fails")
    public void itShouldCallTheAfterFailureActionII() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      afterFailureActions.add(
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);
          });

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> false;
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {};

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }

    @Test
    @DisplayName("It should call the after sucess actions if action success")
    public void itShouldCallTheAfterSuccessActionI() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();
      afterSuccessActions.add(
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);
          });

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> true;
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {};

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }

    @Test
    @DisplayName("It should call the failure action if any precondition is not fullfilled")
    public void itShouldCallTheFailureActionI() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> true;
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);
          };

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();
      preconditionList.add((ICatanGameHearth hearth, IRequest request) -> true);
      preconditionList.add((ICatanGameHearth hearth, IRequest request) -> false);

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }

    @Test
    @DisplayName("It should call the success action if no precondition is provided")
    public void itShouldCallTheSuccessActionI() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);

            return true;
          };
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {};

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }

    @Test
    @DisplayName("It should call the success action if all the preconditions are fullfilled")
    public void itShouldCallTheSuccessActionII() {

      AtomicBoolean called = new AtomicBoolean(false);

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

      BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
          (ICatanGameHearth hearth, IRequest request) -> {
            called.set(true);

            return true;
          };
      BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
          (ICatanGameHearth hearth, IRequest request) -> {};

      LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
          new LinkedList<>();
      preconditionList.add((ICatanGameHearth hearth, IRequest request) -> true);

      MinimunRequestHandlerBuilder builder =
          new MinimunRequestHandlerBuilder()
              .setAfterFailureActions(afterFailureActions)
              .setAfterSuccessActions(afterSuccessActions)
              .setPreconditionFullfilledAction(preconditionFullfilledAction)
              .setPreconditionRejectedAction(preconditionRejectedAction)
              .setPreconditionsList(preconditionList);

      MinimunRequestHandler requestHandler = new MinimunRequestHandler(builder);

      requestHandler.handle(null, null);

      assertSame(true, called.get());
    }
  }
}
