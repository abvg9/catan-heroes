package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.player.IPlayer;

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
