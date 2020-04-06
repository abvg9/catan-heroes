package com.catanheroes.core.request;

import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.player.IPlayer;

public class BuildInitialConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildInitialConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_INITIAL_CONNECTION, type, x, y);
  }
}
