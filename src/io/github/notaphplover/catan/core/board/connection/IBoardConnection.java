package io.github.notaphplover.catan.core.board.connection;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;

public interface IBoardConnection extends IOwnedElement {
  ConnectionType getType();
}
