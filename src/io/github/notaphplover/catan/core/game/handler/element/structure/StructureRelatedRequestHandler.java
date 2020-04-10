package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import java.util.function.BiFunction;

public class StructureRelatedRequestHandler<TRequest extends IStructureRelatedRequest>
    extends BuildElementRequestHandler<TRequest> {

  public StructureRelatedRequestHandler(
      StructureRelatedRequestHandlerBuilder<TRequest, ?> builder) {
    super(builder, getElementBuilder());
  }

  private static <TRequest extends IStructureRelatedRequest>
      BiFunction<ICatanGameHearth, TRequest, IOwnedElement> getElementBuilder() {
    return (ICatanGameHearth hearth, TRequest request) ->
        new BoardStructure(
            request.getPlayer(),
            hearth.getStructureCostProvider().getResourceManager(request.getStructureType()),
            request.getStructureType());
  }
}
