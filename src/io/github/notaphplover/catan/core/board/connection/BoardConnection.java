package io.github.notaphplover.catan.core.board.connection;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.element.OwnedElement;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;

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
