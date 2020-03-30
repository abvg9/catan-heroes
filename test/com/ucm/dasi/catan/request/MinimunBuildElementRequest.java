package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;

public class MinimunBuildElementRequest extends BuildElementRequest {

  public MinimunBuildElementRequest(
      IPlayer player, RequestType requestType, BoardElementType type, int x, int y) {
    super(player, requestType, type, x, y);
  }
}
