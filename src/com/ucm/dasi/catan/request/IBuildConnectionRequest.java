package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.connection.ConnectionType;

public interface IBuildConnectionRequest extends IBuildElementRequest {
  ConnectionType getConnectionType();
}
