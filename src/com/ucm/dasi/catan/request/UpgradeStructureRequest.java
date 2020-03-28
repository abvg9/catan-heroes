package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

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
