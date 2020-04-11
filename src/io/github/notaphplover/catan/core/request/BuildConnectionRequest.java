package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class BuildConnectionRequest extends AgnosticBuildConnectionRequest {

  public BuildConnectionRequest(IPlayer player, ConnectionType type, int x, int y) {
    super(player, RequestType.BUILD_CONNECTION, type, x, y);
  }
}
