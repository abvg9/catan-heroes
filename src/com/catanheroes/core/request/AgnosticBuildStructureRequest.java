package com.catanheroes.core.request;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;

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
