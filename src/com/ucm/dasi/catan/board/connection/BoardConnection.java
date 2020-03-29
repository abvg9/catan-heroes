package com.ucm.dasi.catan.board.connection;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceStorage;

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
