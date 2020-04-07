package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class BuildStructureRequest extends AgnosticBuildStructureRequest {

  public BuildStructureRequest(IPlayer player, StructureType type, int x, int y) {
    super(player, RequestType.BUILD_STRUCTURE, type, x, y);
  }
}
