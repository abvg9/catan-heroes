package com.catanheroes.core.board.element;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceStorage;

public abstract class OwnedElement extends BoardElement implements IOwnedElement {

  private IResourceStorage cost;

  private IPlayer owner;

  public OwnedElement(BoardElementType type, IResourceStorage cost, IPlayer owner) {
    super(type);

    this.cost = cost;
    this.owner = owner;
  }

  @Override
  public IResourceStorage getCost() {
    return cost;
  }

  @Override
  public IPlayer getOwner() {
    return owner;
  }
}
