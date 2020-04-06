package com.catanheroes.core.request;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.player.IPlayer;

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
