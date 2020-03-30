package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildInitialStructureRequest extends AgnosticBuildStructureRequest
    implements IBuildStructureRequest {

  public BuildInitialStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.BUILD_INITIAL_STRUCTURE, type, x, y);
  }
}
