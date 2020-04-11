package io.github.notaphplover.catan.core.game.handler;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Request handler builder tests")
public class RequestHandlerBuilderTest {

  @Nested
  @DisplayName("RequestHandlerBuilder.constructor")
  class Constructor {

    @Test
    @DisplayName("It should store builder attributes")
    void itShouldStoreBuilderAttr() {

      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();
      LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();

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

      assertSame(afterFailureActions, builder.getAfterFailureActions());
      assertSame(afterSuccessActions, builder.getAfterSuccessActions());
      assertSame(preconditionFullfilledAction, builder.getPreconditionFullfilledAction());
      assertSame(preconditionRejectedAction, builder.getPreconditionRejectedAction());
      assertSame(preconditionList, builder.getPreconditionsList());
    }
  }
}
