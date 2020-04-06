package com.catanheroes.core.request;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;

public class UpgradeStructureRequest extends BuildElementRequest
    implements IUpgradeStructureRequest {

  private StructureType type;

  public UpgradeStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.UPGRADE_STRUCTURE, BoardElementType.STRUCTURE, x, y);

    this.type = type;
  }

  @Override
  public StructureType getStructureType() {
    return type;
  }
}
