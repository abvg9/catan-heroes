package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

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
