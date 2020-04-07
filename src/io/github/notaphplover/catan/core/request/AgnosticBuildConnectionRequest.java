package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class AgnosticBuildConnectionRequest extends BuildElementRequest
    implements IBuildConnectionRequest {

  private ConnectionType type;

  public AgnosticBuildConnectionRequest(
      IPlayer player, RequestType requestType, ConnectionType type, int x, int y) {
    super(player, requestType, BoardElementType.CONNECTION, x, y);

    this.type = type;
  }

  @Override
  public ConnectionType getConnectionType() {
    return type;
  }
}
