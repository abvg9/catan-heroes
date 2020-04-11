package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import java.util.function.BiFunction;

public class StructureRelatedRequestHandler<Req extends IStructureRelatedRequest>
    extends BuildElementRequestHandler<Req> {

  public StructureRelatedRequestHandler(StructureRelatedRequestHandlerBuilder<Req, ?> builder) {
    super(builder, getElementBuilder());
  }

  private static <Req extends IStructureRelatedRequest>
      BiFunction<ICatanGameHearth, Req, IOwnedElement> getElementBuilder() {
    return (ICatanGameHearth hearth, Req request) ->
        new BoardStructure(
            request.getPlayer(),
            hearth.getStructureCostProvider().getResourceManager(request.getStructureType()),
            request.getStructureType());
  }
}
