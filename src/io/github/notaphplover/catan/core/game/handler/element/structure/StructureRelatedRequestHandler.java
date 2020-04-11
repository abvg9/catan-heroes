package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import java.util.function.BiFunction;

public class StructureRelatedRequestHandler<R extends IStructureRelatedRequest>
    extends BuildElementRequestHandler<R> {

  public StructureRelatedRequestHandler(StructureRelatedRequestHandlerBuilder<R, ?> builder) {
    super(builder, getElementBuilder());
  }

  private static <R extends IStructureRelatedRequest>
      BiFunction<ICatanGameHearth, R, IOwnedElement> getElementBuilder() {
    return (ICatanGameHearth hearth, R request) ->
        new BoardStructure(
            request.getPlayer(),
            hearth.getStructureCostProvider().getResourceManager(request.getStructureType()),
            request.getStructureType());
  }
}
