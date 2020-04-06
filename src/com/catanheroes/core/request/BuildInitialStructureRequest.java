package com.catanheroes.core.request;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;

public class BuildInitialStructureRequest extends AgnosticBuildStructureRequest {

  public BuildInitialStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.BUILD_INITIAL_STRUCTURE, type, x, y);
  }
}
