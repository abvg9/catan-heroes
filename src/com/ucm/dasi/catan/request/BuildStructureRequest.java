package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildStructureRequest extends AgnosticBuildStructureRequest {

  public BuildStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.BUILD_STRUCTURE, type, x, y);
  }
}
