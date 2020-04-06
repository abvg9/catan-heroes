package com.catanheroes.core.request;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;

public class BuildStructureRequest extends AgnosticBuildStructureRequest {

  public BuildStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.BUILD_STRUCTURE, type, x, y);
  }
}
