package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;

public interface IBuildConnectionRequest extends IBuildElementRequest {
  ConnectionType getConnectionType();
}
