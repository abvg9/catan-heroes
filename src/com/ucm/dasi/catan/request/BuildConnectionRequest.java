package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_CONNECTION, type, x, y);
  }
}
