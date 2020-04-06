package com.catanheroes.core.request;

import com.catanheroes.core.board.connection.ConnectionType;

public interface IBuildConnectionRequest extends IBuildElementRequest {
  ConnectionType getConnectionType();
}
