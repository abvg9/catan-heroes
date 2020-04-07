package io.github.notaphplover.catan.core.board.element;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceManager;

public class MinimunOwnedElement extends OwnedElement {

  public MinimunOwnedElement(BoardElementType type, IResourceManager cost, IPlayer owner) {
    super(type, cost, owner);
  }
}
