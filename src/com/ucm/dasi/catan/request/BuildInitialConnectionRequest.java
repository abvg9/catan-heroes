package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.player.IPlayer;

public class BuildInitialConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildInitialConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_INITIAL_CONNECTION, type, x, y);
  }
}
