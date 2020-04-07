package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class AgnosticBuildStructureRequest extends BuildElementRequest
    implements IBuildStructureRequest {

  private StructureType type;

  public AgnosticBuildStructureRequest(
      IPlayer player, RequestType requestType, StructureType type, int x, int y) {
    super(player, requestType, BoardElementType.STRUCTURE, x, y);

    this.type = type;
  }

  @Override
  public StructureType getStructureType() {
    return type;
  }
}
