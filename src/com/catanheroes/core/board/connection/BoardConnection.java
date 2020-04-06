package com.catanheroes.core.board.connection;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.element.OwnedElement;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceStorage;

public class BoardConnection extends OwnedElement implements IBoardConnection {

  protected ConnectionType type;

  public BoardConnection(IPlayer owner, IResourceStorage cost, ConnectionType type) {
    super(BoardElementType.CONNECTION, cost, owner);

    this.type = type;
  }

  @Override
  public ConnectionType getType() {
    return type;
  }
}
