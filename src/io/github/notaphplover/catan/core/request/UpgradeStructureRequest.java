package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;

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
