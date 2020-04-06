package com.catanheroes.core.request;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.player.IPlayer;

public class MinimunBuildElementRequest extends BuildElementRequest {

  public MinimunBuildElementRequest(
      IPlayer player, RequestType requestType, BoardElementType type, int x, int y) {
    super(player, requestType, type, x, y);
  }
}
