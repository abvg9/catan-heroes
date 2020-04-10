package io.github.notaphplover.catan.core.game.handler.element.connection;

import io.github.notaphplover.catan.core.board.connection.BoardConnection;
import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import java.util.function.BiFunction;

public class BuildConnectionRequestHandler
    extends BuildElementRequestHandler<IBuildConnectionRequest> {

  public BuildConnectionRequestHandler(BuildConnectionRequestHandlerBuilder builder) {
    super(builder, getElementBuilder());
  }

  private static BiFunction<ICatanGameHearth, IBuildConnectionRequest, IOwnedElement>
      getElementBuilder() {
    return (ICatanGameHearth hearth, IBuildConnectionRequest request) ->
        new BoardConnection(
            request.getPlayer(),
            hearth.getConnectionCostProvider().getResourceManager(request.getConnectionType()),
            request.getConnectionType());
  }
}
