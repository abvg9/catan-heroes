package com.catanheroes.core.board.element;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceManager;

public class MinimunOwnedElement extends OwnedElement {

  public MinimunOwnedElement(BoardElementType type, IResourceManager cost, IPlayer owner) {
    super(type, cost, owner);
  }
}
