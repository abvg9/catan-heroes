package com.catanheroes.core.board.connection;

import com.catanheroes.core.board.element.IOwnedElement;

public interface IBoardConnection extends IOwnedElement {
  ConnectionType getType();
}
