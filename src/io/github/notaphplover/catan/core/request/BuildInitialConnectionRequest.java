package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class BuildInitialConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildInitialConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_INITIAL_CONNECTION, type, x, y);
  }
}
