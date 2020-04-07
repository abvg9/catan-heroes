package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class MinimunBuildElementRequest extends BuildElementRequest {

  public MinimunBuildElementRequest(
      IPlayer player, RequestType requestType, BoardElementType type, int x, int y) {
    super(player, requestType, type, x, y);
  }
}
