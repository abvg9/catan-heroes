package com.catanheroes.core.request;

import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.player.IPlayer;

public class BuildConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_CONNECTION, type, x, y);
  }
}
